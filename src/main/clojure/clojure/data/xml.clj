;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Functions to parse XML into lazy sequences and lazy trees and
  emit these as text."
  :author "Chris Houser"}
  clojure.data.xml
  (:require [clojure.string :as str])
  (:import (javax.xml.stream XMLInputFactory
                             XMLStreamReader
                             XMLStreamWriter
                             XMLStreamConstants)
           (java.nio.charset Charset)
           (java.io Reader)))

; Represents a parse event.
; type is one of :start-element, :end-element, :characters or :comment
(defrecord Event [type name attrs str])

(defn event [type name & [attrs str]]
  (Event. type name attrs str))

(defn qualified-name [event-name]
  (if (instance? clojure.lang.Named event-name)
   [(namespace event-name) (name event-name)]
   (let [name-parts (str/split event-name #"/" 2)]
     (if (= 2 (count name-parts))
       name-parts
       [nil (first name-parts)]))))

(defn write-attributes [attrs ^javax.xml.stream.XMLStreamWriter writer]
  (doseq [[k v] attrs]
    (let [[attr-ns attr-name] (qualified-name k)]
      (if attr-ns
        (.writeAttribute writer attr-ns attr-name (str v))
        (.writeAttribute writer attr-name (str v))))))

; Represents a node of an XML tree
(defrecord Element [tag attrs content])
(defrecord CData [content])
(defrecord Comment [content])

(defn emit-start-tag [event ^javax.xml.stream.XMLStreamWriter writer]
  (let [[nspace qname] (qualified-name (:name event))]
    (.writeStartElement writer "" qname (or nspace ""))
    (write-attributes (:attrs event) writer)))

(defn emit-event [event ^javax.xml.stream.XMLStreamWriter writer]
  (case (:type event)
    :start-element (emit-start-tag event writer)
    :end-element (.writeEndElement writer)
    :chars (.writeCharacters writer (:str event))
    :cdata (.writeCData writer (:str event))
    :comment (.writeComment writer (:str event))))

(defprotocol EventGeneration
  "Protocol for generating new events based on element type"
  (gen-event [item]
    "Function to generate an event for e.")
  (next-events [item next-items]
    "Returns the next set of events that should occur after e.  next-events are the
     events that should be generated after this one is complete."))

(extend-protocol EventGeneration
  Element
  (gen-event [element]
    (Event. :start-element (:tag element) (:attrs element) nil))
  (next-events [element next-items]
    (cons (:content element)
          (cons (Event. :end-element (:tag element) nil nil) next-items)))
  Event
  (gen-event [event] event)
  (next-events [_ next-items]
    next-items)

  clojure.lang.Sequential
  (gen-event [coll]
    (gen-event (first coll)))
  (next-events [coll next-items]
    (if-let [r (seq (rest coll))]
      (cons (next-events (first coll) r) next-items)
      (next-events (first coll) next-items)))
  
  String
  (gen-event [s]
    (Event. :chars nil nil s))
  (next-events [_ next-items]
    next-items)
  
  CData
  (gen-event [cdata]
    (Event. :cdata nil nil (:content cdata)))
  (next-events [_ next-items]
    next-items)
  
  Comment
  (gen-event [comment]
    (Event. :comment nil nil (:content comment)))
  (next-events [_ next-items]
    next-items)
  
  nil
  (gen-event [_]
    (Event. :chars nil nil ""))
  (next-events [_ next-items]
    next-items))

(defn flatten-elements [elements]
  (when (seq elements)
    (lazy-seq
     (let [e (first elements)]
       (cons (gen-event e)
             (flatten-elements (next-events e (rest elements))))))))

(defn element [tag & [attrs & content]]
  (Element. tag (or attrs {}) (remove nil? content)))

(defn cdata [content]
  (CData. content))

(defn xml-comment [content]
  (Comment. content))

;=== Parse-related functions ===
(defn seq-tree
  "Takes a seq of events that logically represents
  a tree by each event being one of: enter-sub-tree event,
  exit-sub-tree event, or node event.

  Returns a lazy sequence whose first element is a sequence of
  sub-trees and whose remaining elements are events that are not
  siblings or descendants of the initial event.

  The given exit? function must return true for any exit-sub-tree
  event.  parent must be a function of two arguments: the first is an
  event, the second a sequence of nodes or subtrees that are children
  of the event.  parent must return nil or false if the event is not
  an enter-sub-tree event.  Any other return value will become
  a sub-tree of the output tree and should normally contain in some
  way the children passed as the second arg.  The node function is
  called with a single event arg on every event that is neither parent
  nor exit, and its return value will become a node of the output tree.

  (seq-tree #(when (= %1 :<) (vector %2)) #{:>} str
            [1 2 :< 3 :< 4 :> :> 5 :> 6])
  ;=> ((\"1\" \"2\" [(\"3\" [(\"4\")])] \"5\") 6)"
 [parent exit? node coll]
  (lazy-seq
    (when-let [[event] (seq coll)]
      (let [more (rest coll)]
        (if (exit? event)
          (cons nil more)
          (let [tree (seq-tree parent exit? node more)]
            (if-let [p (parent event (lazy-seq (first tree)))]
              (let [subtree (seq-tree parent exit? node (lazy-seq (rest tree)))]
                (cons (cons p (lazy-seq (first subtree)))
                      (lazy-seq (rest subtree))))
              (cons (cons (node event) (lazy-seq (first tree)))
                    (lazy-seq (rest tree))))))))))

(defn copy-location-meta
  [src dst]
  (if-let [loc (:location (meta src))]
    (with-meta dst {:location loc})
    dst))

(defn event-tree
  "Returns a lazy tree of Element objects for the given seq of Event
  objects. See source-seq and parse."
  [events]
  (ffirst
    (seq-tree
      (fn [^Event event contents]
        (when (= :start-element (.type event))
          (copy-location-meta event (Element. (.name event) (.attrs event) contents))))
      (fn [^Event event] (= :end-element (.type event)))
      (fn [^Event event]
        (case (.type event)
          :comment (copy-location-meta event (Comment. (.str event)))
          :characters (.str event)))
    events)))

(defprotocol AsElements
  (as-elements [expr] "Return a seq of elements represented by an expression."))

(extend-protocol AsElements
  clojure.lang.IPersistentVector
  (as-elements [v]
    (let [[tag & [attrs & after-attrs :as content]] v
          [attrs content] (if (map? attrs)
                            [(into {} (for [[k v] attrs]
                                        [k (str v)]))
                             after-attrs]
                            [{} content])]
      [(Element. tag attrs (mapcat as-elements content))]))

  clojure.lang.ISeq
  (as-elements [s]
    (mapcat as-elements s))

  clojure.lang.Keyword
  (as-elements [k]
    [(Element. k {} ())])

  java.lang.String
  (as-elements [s]
    [s])

  nil
  (as-elements [_] nil)

  java.lang.Object
  (as-elements [o]
    [(str o)]))

(defn sexps-as-fragment
  "Convert a compact prxml/hiccup-style data structure into the more formal
   tag/attrs/content format. A seq of elements will be returned, which may
   not be suitable for immediate use as there is no root element. See also
   sexp-as-element.

   The format is [:tag-name attr-map? content*]. Each vector opens a new tag;
   seqs do not open new tags, and are just used for inserting groups of elements
   into the parent tag. A bare keyword not in a vector creates an empty element.

   To provide XML conversion for your own data types, extend the AsElements
   protocol to them."
  ([] nil)
  ([sexp] (as-elements sexp))
  ([sexp & sexps] (mapcat as-elements (cons sexp sexps))))

(defn sexp-as-element
  "Convert a single sexp into an Element"
  [sexp]
  (let [[root & more] (sexps-as-fragment sexp)]
    (when more
      (throw
       (IllegalArgumentException.
        "Cannot have multiple root elements; try creating a fragment instead")))
    root))


(defn- attr-prefix [^XMLStreamReader sreader index]
  (let [p (.getAttributePrefix sreader index)]
    (when-not (str/blank? p)
      p)))

(defn- attr-hash [^XMLStreamReader sreader] (into {}
    (for [i (range (.getAttributeCount sreader))]
      [(keyword (attr-prefix sreader i) (.getAttributeLocalName sreader i))
       (.getAttributeValue sreader i)])))

(defn get-location
  [^XMLStreamReader sreader]
  (let [^javax.xml.stream.Location loc (.getLocation sreader)]
    {:line (.getLineNumber loc)
     :column (.getColumnNumber loc)
     :character-offset (.getCharacterOffset loc)
     :public-id (.getPublicId loc)
     :system-id (.getSystemId loc)}))

(defn with-location
  [store-location sreader value]
  (if store-location
    (with-meta value {:location (get-location sreader)})
    value))

; Note, sreader is mutable and mutated here in pull-seq, but it's
; protected by a lazy-seq so it's thread-safe.
(defn- pull-seq
  "Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to 
   be triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete.
   Options:
     :comments <boolean>        whether to load comments
     :store-location <boolean>  whether to store starting elements file location in metadata"
  [^XMLStreamReader sreader {:keys [comments store-location] :as props}]
  (lazy-seq
   (loop []
     (condp == (.next sreader)
       XMLStreamConstants/START_ELEMENT
       (cons
         (with-location store-location sreader
           (event :start-element (keyword (.getLocalName sreader)) (attr-hash sreader) nil))
         (pull-seq sreader props))
       XMLStreamConstants/END_ELEMENT
       (cons
         (event :end-element (keyword (.getLocalName sreader)) nil nil)
         (pull-seq sreader props))
       XMLStreamConstants/CHARACTERS
       (if-let [text (and (not (.isWhiteSpace sreader))
                          (.getText sreader))]
         (cons
           (event :characters nil nil text)
           (pull-seq sreader props))
         (recur))
       XMLStreamConstants/COMMENT
       (if comments
         (if-let [text (and (not (.isWhiteSpace sreader)) (.getText sreader))]
           (cons
             (with-location store-location sreader (event :comment nil nil text))
             (pull-seq sreader props))
           (recur))
         (recur))
       XMLStreamConstants/END_DOCUMENT
       nil
       (recur);; Consume and ignore spaces, processing instructions etc
       ))))

(def ^{:private true} xml-input-factory-props
  {:allocator javax.xml.stream.XMLInputFactory/ALLOCATOR
   :coalescing javax.xml.stream.XMLInputFactory/IS_COALESCING
   :namespace-aware javax.xml.stream.XMLInputFactory/IS_NAMESPACE_AWARE
   :replacing-entity-references javax.xml.stream.XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES
   :supporting-external-entities javax.xml.stream.XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES
   :validating javax.xml.stream.XMLInputFactory/IS_VALIDATING
   :reporter javax.xml.stream.XMLInputFactory/REPORTER
   :resolver javax.xml.stream.XMLInputFactory/RESOLVER
   :support-dtd javax.xml.stream.XMLInputFactory/SUPPORT_DTD})

(defn- new-xml-input-factory [props]
  (let [fac (javax.xml.stream.XMLInputFactory/newInstance)]
    (doseq [[k v] props
            :let [prop (xml-input-factory-props k)]]
      (.setProperty fac prop v))
    fac))

(defn source-seq
  "Parses the XML InputSource source using a pull-parser. Returns
   a lazy sequence of Event records.  Accepts key pairs
   with XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
   and xml-input-factory-props for more information. Defaults coalescing true.
   Options:
    :comments <boolean>        whether to load comments
    :store-location <boolean>  whether to store starting elements file location in metadata"
  [s & {:keys [comments store-location] :as props}]
  (let [fac (new-xml-input-factory (merge {:coalescing true} (dissoc props :comments :store-location)))
        ;; Reflection on following line cannot be eliminated via a
        ;; type hint, because s is advertised by fn parse to be an
        ;; InputStream or Reader, and there are different
        ;; createXMLStreamReader signatures for each of those types.
        sreader (.createXMLStreamReader fac s)]
    (pull-seq sreader
      {:comments comments
       :store-location store-location})))

(defn parse
  "Parses the source, which can be an
   InputStream or Reader, and returns a lazy tree of Element records. Accepts key pairs
   with XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
   and xml-input-factory-props for more information. Defaults coalescing true.
   Options:
    :comments <boolean>        whether to load comments
    :store-location <boolean>  whether to store starting elements file location in metadata"
  [source & props]
  (event-tree (apply source-seq source props)))

(defn parse-str
  "Parses the passed in string to Clojure data structures.  Accepts key pairs
   with XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
   and xml-input-factory-props for more information. Defaults coalescing true.
   Options:
    :comments <boolean>        whether to load comments
    :store-location <boolean>  whether to store starting elements file location in metadata"
  [s & props]
  (let [sr (java.io.StringReader. s)]
    (apply parse sr props)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; XML Emitting
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn check-stream-encoding [^java.io.OutputStreamWriter stream xml-encoding]
  (when (not= (Charset/forName xml-encoding) (Charset/forName (.getEncoding stream)))
    (throw (Exception. (str "Output encoding of stream (" xml-encoding
                            ") doesn't match declaration ("
                            (.getEncoding stream) ")")))))

(defn emit
  "Prints the given Element tree as XML text to stream.
   Options:
    :encoding <str>          Character encoding to use"
  [e ^java.io.Writer stream & {:as opts}]
  (let [^javax.xml.stream.XMLStreamWriter writer (-> (javax.xml.stream.XMLOutputFactory/newInstance)
                                                     (.createXMLStreamWriter stream))]

    (when (instance? java.io.OutputStreamWriter stream)
      (check-stream-encoding stream (or (:encoding opts) "UTF-8")))
    
    (.writeStartDocument writer (or (:encoding opts) "UTF-8") "1.0")
    (doseq [event (flatten-elements [e])]
      (emit-event event writer))
    (.writeEndDocument writer)
    stream))

(defn emit-str
  "Emits the Element to String and returns it"
  [e & opts]
  (let [^java.io.StringWriter sw (java.io.StringWriter.)]
    (apply emit e sw opts)
    (.toString sw)))

;; ==============================================================
;; Indenting XMLStreamWriter implementation
;; This implementation of indenting XMLStreamWriter
;; is heavily inspired by stax-utils IndentingXMLStreamWriter:
;; http://java.net/projects/stax-utils/
;; ==============================================================

;; A stack over integer array

; An interface required for the following deftype
(definterface IntStack
  (^int peek [])
  (^void replace [^clojure.lang.IFn f arg])
  (^void push [^int value])
  (^int pop [])
  (^int depth []))

; Actual implementation of the stack
; Completely not thread-safe, for performance reasons
(deftype IntStackImpl
  [^{:tag ints :unsynchronized-mutable true} data
   ^{:tag int :unsynchronized-mutable true} depth]
  IntStack
  (peek [this]
    (if (>= depth 0)
      (aget data depth)
      (throw (IllegalStateException. "Stack is empty!"))))
  (replace [this f arg]
    (if (>= depth 0)
      (aset data depth (int (f (aget data depth) arg)))
      (throw (IllegalStateException. "Stack is empty!"))))
  (push [this value]
    (when (>= (inc depth) (alength data))
      (let [data-length (alength data)
            new-data (int-array (* data-length 2))]
        (System/arraycopy data 0 new-data 0 data-length)
        (set! data new-data)))
    (set! depth (int (inc depth)))
    (aset data depth value))
  (pop [this]
    (if (>= depth 0)
      (let [value (aget data depth)]
        (set! depth (int (dec depth)))
        value)
      (throw (IllegalStateException. "Stack is empty!"))))
  (depth [this]
    (inc depth)))

(defn new-int-stack
  "Creates new stack for integers."
  ([] (new-int-stack -1))
  ([start] (IntStackImpl. (int-array 4) start)))

; Additional supporting macros and functions

(defmacro wrapped-with
  "Surrounds the body with (before-<what> this) and (after-<what> this) calls. 'this' symbol is captured
  from the environment."
  [what & body]
  `(do
     (~(symbol (str "before-" what)) ~'this)
     ~@body
     (~(symbol (str "after-" what)) ~'this)))

(defmacro deftype-with-delegation
  "Adds delegation feature to deftype. Equivalent to (deftype), but it is also
  possible to use (delegate-to target method*) form inside it, which will
  expand to method definitions which will call themselves on target with all
  corresponding arguments. Example:

  (deftype-with-delegation NewType
    [val a b]
    SomeInterface
    (delegate-to val
      method1 [x y]
      method2 [z])
    (method3 [this u] (println u)))

  expands to

  (deftype NewType
    [val a b]
    SomeInterface
    (method1 [_ x y] (. val method1 x y))
    (method2 [_ z] (. val method2 z))
    (method3 [this u] (println u)))

  With this macro it is possible to delegate multiple methods of some
  interface/protocol to another object. Useful for creating wrappers, e.g. for
  java.io.OutputStream or java.xml.stream.XMLStreamWriter.

  Side-effect of this macro is that it is not possible to extend protocols which contain 'delegate-to' method
  with it in other way than delegating."
  [& args]
  `(deftype
     ~@(mapcat
         (fn [arg]
           (or (when (coll? arg)
                 (when-let [[name & more] arg]
                   (when (= name 'delegate-to)
                     (let [[target & pairs] more]
                       (for [[name args] (partition 2 pairs)]
                         `(~name [~'_ ~@args] (. ~target ~name ~@args)))))))
             [arg]))
         args)))


(defmacro inc!
  "Applies (inc) function to mutable field of the class."
  [field]
  `(set! ~field (inc ~field)))

(defmacro dec!
  "Applies (dec) function to mutable field of the class."
  [field]
  `(set! ~field (dec ~field)))

; Bit masks which will be held in the stack
(def +wrote-markup+ 1)
(def +wrote-text+ 2)

(defn const [a b] b)

; Convenience functions to query and update the stack

(defn wrote-text?
  [^IntStack stack]
  (> (bit-and (long (.peek stack)) +wrote-text+) 0))

(defn wrote-markup?
  [^IntStack stack]
  (> (bit-and (long (.peek stack)) +wrote-markup+) 0))

(defn wrote-text!
  [^IntStack stack]
  (.replace stack const +wrote-text+))

(defn wrote-markup!
  [^IntStack stack]
  (.replace stack const +wrote-markup+))

(defn reset-state!
  [^IntStack stack]
  (.replace stack (constantly 0) 0))

; A protocol with additional methods used for indenting, implemented by IndentingXMLStreamWriter
(defprotocol Indenter
  (before-markup [this])
  (after-markup [this])
  (before-text [this])
  (after-text [this])
  (before-start-element [this])
  (after-start-element [this])
  (before-end-element [this])
  (after-end-element [this])
  (write-newline [this level]))

; Actual implementation of indenting XMLStreamWriter
; The algorithm here is very similar to the one in IndentingXMLStreamWriter from stax-utils; however, there are
; some changes
; Requires thorough testing; it is highly possible that there are bugs here
(deftype-with-delegation IndentingXMLStreamWriter
  [^{:tag XMLStreamWriter} writer
   ^{:tag String} line-separator
   ^{:tag String} indent-string
   ^{:tag long :unsynchronized-mutable true} indent-level
   ^{:tag IntStack :unsynchronized-mutable true} stack]
  XMLStreamWriter
  (delegate-to writer
    close []
    flush []
    getPrefix [uri]
    setPrefix [prefix uri]
    setDefaultNamespace [uri]
    setNamespaceContext [namespace-context]
    getNamespaceContext []
    getProperty [name]
    writeEntityRef [name]
    writeNamespace [prefix namespace-uri]
    writeDefaultNamespace [namespace-uri]
    writeAttribute [local-name value]
    writeAttribute [namespace-uri local-name value]
    writeAttribute [prefix namespace-uri local-name value])
  (writeStartElement [this local-name]
    (wrapped-with start-element
      (.writeStartElement writer local-name)))
  (writeStartElement [this namespace-uri local-name]
    (wrapped-with start-element
      (.writeStartElement writer namespace-uri)))
  (writeStartElement [this prefix namespace-uri local-name]
    (wrapped-with start-element
      (.writeStartElement writer prefix namespace-uri local-name)))
  (writeEmptyElement [this local-name]
    (wrapped-with markup
      (.writeEmptyElement writer local-name)))
  (writeEmptyElement [this namespace-uri local-name]
    (wrapped-with markup
      (.writeEmptyElement writer namespace-uri local-name)))
  (writeEmptyElement [this prefix namespace-uri local-name]
    (wrapped-with markup
      (.writeEmptyElement writer prefix namespace-uri local-name)))
  (writeEndElement [this]
    (wrapped-with end-element
      (.writeEndElement writer)))
  (writeComment [this data]
    (wrapped-with markup
      (.writeComment writer data)))
  (writeProcessingInstruction [this target]
    (wrapped-with markup
      (.writeProcessingInstruction writer target)))
  (writeProcessingInstruction [this target data]
    (wrapped-with markup
      (.writeProcessingInstruction writer target data)))
  (writeCData [this data]
    (wrapped-with text
      (.writeCData writer data)))
  (writeCharacters [this text start length]
    (wrapped-with text
      (.writeCharacters writer text start length)))
  (writeCharacters [this text]
    (wrapped-with text
      (.writeCharacters writer text)))
  (writeDTD [this dtd]
    (wrapped-with markup
      (.writeDTD writer dtd)))
  (writeStartDocument [this]
    (wrapped-with markup
      (.writeStartDocument writer)))
  (writeStartDocument [this version]
    (wrapped-with markup
      (.writeStartDocument writer version)))
  (writeStartDocument [this encoding version]
    (wrapped-with markup
      (.writeStartDocument writer encoding version)))
  (writeEndDocument [this]
    (while (> indent-level 0)
      (.writeEndElement this)
      (.pop stack))
    (.writeEndDocument writer)
    (set! indent-level 0)
    (when (and (wrote-markup? stack) (not (wrote-text? stack)))
      (write-newline this 0))
    (reset-state! stack))

  Indenter
  (before-markup [this]
    (when (and (not (wrote-text? stack)) (or (> indent-level 0) (wrote-markup? stack)))
      (write-newline this indent-level)
      (when (and (> indent-level 0) (> (.length indent-string) 0))
        (after-markup this))))
  (after-markup [this]
    (wrote-markup! stack))
  (before-start-element [this]
    (before-markup this)
    (.push stack 0))
  (after-start-element [this]
    (after-markup this)
    (inc! indent-level))
  (before-end-element [this]
    (when (and (> indent-level 0) (wrote-markup? stack) (not (wrote-text? stack)))
      (write-newline this (dec indent-level))))
  (after-end-element [this]
    (when (> indent-level 0)
      (dec! indent-level)
      (.pop stack))
    (wrote-markup! stack))
  (before-text [this])
  (after-text [this]
    (wrote-text! stack))
  (write-newline [this level]
    (let [indentation (apply str line-separator (repeat level indent-string))]
      (.writeCharacters writer indentation))))

(defn indent
  "Prints the given Element tree as an indented XML text to stream.
   Options:
    :encoding <str>          Character encoding to use, defaults to \"UTF-8\"
    :line-separator <str>    Line separator to use for indenting, defaults to \"\\n\"
    :indent-string <str>     A string used for single level of indentation, defaults to \"  \" (two spaces)"
  [e ^java.io.Writer stream & {:keys [encoding line-separator indent-string]
                               :or {encoding "UTF-8" line-separator "\n" indent-string "  "}}]
  (let [^javax.xml.stream.XMLStreamWriter writer
        (-> (javax.xml.stream.XMLOutputFactory/newInstance)
          (.createXMLStreamWriter stream)
          (IndentingXMLStreamWriter. line-separator indent-string 0 (new-int-stack 0)))]

    (when (instance? java.io.OutputStreamWriter stream)
      (check-stream-encoding stream encoding))

    (.writeStartDocument writer encoding "1.0")
    (doseq [event (flatten-elements [e])]
      (emit-event event writer))
    (.writeEndDocument writer)

    stream))

(defn indent-str
  "Prints the given Element tree as an indented XML text to a string and returns it. Options are the same as
  for (indent)."
  [e & opts]
  (let [^java.io.StringWriter sw (java.io.StringWriter.)]
    (apply indent e sw opts)
    (.toString sw)))

