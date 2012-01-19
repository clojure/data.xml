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
                             XMLStreamConstants)
           (java.nio.charset Charset)
           (java.io Reader)))

; Represents a parse event.
; type is one of :start-element, :end-element, or :characters
(defrecord Event [type name attrs str])

(defn event [type name & [attrs str]]
  (Event. type name attrs str))

(defprotocol Emit
  (emit-element [element writer]))

(defn write-attributes [{:keys (attrs)} ^javax.xml.stream.XMLStreamWriter writer]
  (doseq [[k v] attrs]
    (if (namespace k)
      (.writeAttribute writer (str (namespace k)) (name k) (str v))
      (.writeAttribute writer (name k) (str v)))))

; Represents a node of an XML tree
(defrecord Element [tag attrs content]
  Emit
  (emit-element [e writer]
    (let [nspace (namespace (:tag e))
          qname (name (:tag e))
          ^javax.xml.stream.XMLStreamWriter writer writer]
      (.writeStartElement writer ""  qname (or nspace ""))
      (write-attributes e writer)
      (doseq [c (:content e)]
        (emit-element c writer))
      (.writeEndElement writer))))

(defrecord CData [content]
  Emit
  (emit-element [e writer]
    (.writeCData ^javax.xml.stream.XMLStreamWriter writer (:content e))))

(defrecord Comment [content]
  Emit
  (emit-element [e writer]
    (.writeComment ^javax.xml.stream.XMLStreamWriter writer (:content e))))

(extend-protocol Emit
  String
  (emit-element [e writer]
    (.writeCharacters ^javax.xml.stream.XMLStreamWriter writer e)))

(defn element [tag & [attrs & content]]
  (Element. tag (or attrs {}) (remove nil? content)))

(defn cdata [content]
  (CData. content))

(defn xml-comment [content]
  (Comment. content))

;=== Parse-related functions ===
(defn- seq-tree
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

(defn event-tree
  "Returns a lazy tree of Element objects for the given seq of Event
  objects. See source-seq and parse."
  [events]
  (ffirst
   (seq-tree
    (fn [^Event event contents]
      (when (= :start-element (.type event))
        (Element. (.name event) (.attrs event) contents)))
    (fn [^Event event] (= :end-element (.type event)))
    (fn [^Event event] (.str event))
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

; Note, sreader is mutable and mutated here in pull-seq, but it's
; protected by a lazy-seq so it's thread-safe.
(defn- pull-seq
  "Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to 
   be triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete."
  [^XMLStreamReader sreader]
  (lazy-seq
   (loop []
     (condp == (.next sreader)
       XMLStreamConstants/START_ELEMENT
       (cons (event :start-element
                    (keyword (.getLocalName sreader))
                    (attr-hash sreader) nil)
             (pull-seq sreader)) 
       XMLStreamConstants/END_ELEMENT
       (cons (event :end-element
                    (keyword (.getLocalName sreader)) nil nil)
             (pull-seq sreader))
       XMLStreamConstants/CHARACTERS
       (if-let [text (and (not (.isWhiteSpace sreader))
                          (.getText sreader))]
         (cons (event :characters nil nil text)
               (pull-seq sreader))
         (recur))
       XMLStreamConstants/COMMENT
       (recur)
       XMLStreamConstants/SPACE
       (recur)
       XMLStreamConstants/END_DOCUMENT
       nil))))

(defn source-seq
  "Parses the XML InputSource source using a pull-parser. Returns
  a lazy sequence of Event records.  See clojure.data.xml/lazy-source-seq
  for similar results but without requiring an external pull parser."
  [^java.io.InputStream s]
  (let [fac (doto (javax.xml.stream.XMLInputFactory/newInstance)
              (.setProperty javax.xml.stream.XMLInputFactory/IS_COALESCING true))
        sreader (.createXMLStreamReader fac s)]
    (doall (pull-seq sreader))))

(defn parse
  "Convenience function. Parses the source, which can be a File,
  InputStream or String naming a URI, and returns a lazy tree of
  Element records. See lazy-source-seq for finer-grained control."
  [source]
  (event-tree (source-seq source)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; XML Emitting
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn check-stream-encoding [^java.io.OutputStreamWriter stream xml-encoding]
  (when (not= (Charset/forName xml-encoding) (Charset/forName (.getEncoding stream)))
    (throw (Exception. (str "Output encoding of stream (" xml-encoding
                            ") doesn't match declaration ("
                            (.getEncoding stream) ")")))))

(defn emit-stream
  "Prints the given Element tree as XML text to *out*. See element-tree.
  Options:
    :indent <num>            Amount to increase indent depth each time
    :encoding <str>          Character encoding to use"
  [e ^java.io.Writer stream & {:as opts}]
  (let [^javax.xml.stream.XMLStreamWriter writer (-> (javax.xml.stream.XMLOutputFactory/newInstance)
                                                     (.createXMLStreamWriter stream))]

    (when (instance? java.io.OutputStreamWriter stream)
      (check-stream-encoding stream (or (:encoding opts) "UTF-8")))
    
    (.writeStartDocument writer (or (:encoding opts) "UTF-8") "1.0")
    (emit-element e writer)
    (.writeEndDocument writer)))

(defn emit [e & {:as opts}]
  (apply emit-stream e *out* opts))
