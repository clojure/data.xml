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
  (:import (org.xml.sax Attributes InputSource)
           (org.xml.sax.helpers DefaultHandler)
           (javax.xml.parsers SAXParserFactory)
           (java.util.concurrent LinkedBlockingQueue TimeUnit)
           (java.lang.ref WeakReference)
           (java.nio.charset Charset)
           (java.io Reader)))

; Represents a parse event.
; type is one of :start-element, :end-element, or :characters
(defrecord Event [type name attrs str])

(defn event [type name & [attrs str]]
  (Event. type name attrs str))

; Represents a node of an XML tree
(defrecord Element [tag attrs content])

(defn element [tag & [attrs & content]]
  (Element. tag (or attrs {}) content))

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



; based on work related to Rich Hickey's seque.
; blame Chouser for anything broken or ugly.
(defn- fill-queue
  "filler-func will be called in another thread with a single arg
  'fill'.  filler-func may call fill repeatedly with one arg each
  time which will be pushed onto a queue, blocking if needed until
  this is possible.  fill-queue will return a lazy seq of the values
  filler-func has pushed onto the queue, blocking if needed until each
  next element becomes available.  filler-func's return value is ignored."
  ([filler-func & optseq]
    (let [opts (apply array-map optseq)
          apoll (:alive-poll opts 1)
          q (LinkedBlockingQueue. (:queue-size opts 1))
          NIL (Object.) ;nil sentinel since LBQ doesn't support nils
          weak-target (Object.)
          alive? (WeakReference. weak-target)
          fill (fn fill [x]
                 (if (.get alive?)
                   (if (.offer q (if (nil? x) NIL x) apoll TimeUnit/SECONDS)
                     x
                     (recur x))
                   (throw (Exception. "abandoned"))))
          f (future
              (try
                (filler-func fill)
                (finally
                  (.put q q))) ;q itself is eos sentinel
              nil)] ; set future's value to nil
      ((fn drain []
         weak-target ; force closing over this object
         (lazy-seq
           (let [x (.take q)]
             (if (identical? x q)
               @f  ;will be nil, touch just to propagate errors
               (cons (if (identical? x NIL) nil x)
                     (drain))))))))))

(defn startparse-sax [s ch]
  (.. SAXParserFactory newInstance newSAXParser (parse s ch)))

(defn- fill-from-sax
  "Uses startparse to create a SAX parser, feeds it the contents of
  InputSource source, and creates an Event object for each interesting
  SAX event. As each Event is created, it is passed in a call to
  fill."
  [source startparse fill]
  (startparse source (proxy [DefaultHandler] []
    (startElement [uri local-name q-name ^Attributes atts]
      ;(prn :start-element q-name)(flush)
      (let [attrs (into {} (for [i (range (.getLength atts))]
                                [(keyword (.getQName atts i))
                                 (.getValue atts i)]))]
        (fill (Event. :start-element (keyword q-name) attrs nil))))
    (endElement [uri local-name q-name]
      ;(prn :end-element q-name)(flush)
      (fill (Event. :end-element (keyword q-name) nil nil)))
    (characters [ch start length]
      ;(prn :characters)(flush)
      (let [st (String. ch start length)]
        (when (seq (.trim st))
          (fill (Event. :characters nil nil st))))))))

(defn source-seq
  "Parses the XML InputSource source (eagerly, not lazily). Returns
  a seq of Event records. Other SAX-compatible parsers can be supplied
  by passing startparse, a fn taking a source and a ContentHandler and
  returning a parser. See also lazy-source-seq"
  ([source] (source-seq source startparse-sax))
  ([source startparse]
   (let [ll (java.util.LinkedList.)]
     (fill-from-sax source startparse #(.add ll %))
     ((fn consume []
        (lazy-seq
          (when-not (empty? ll)
            (cons (.removeFirst ll) (consume)))))))))

(defn lazy-source-seq
  "Parses the XML InputSource source. Returns a lazy sequence of Event
  records. Other SAX-compatible parsers can be supplied by passing
  startparse, a fn taking a source and a ContentHandler and returning
  a parser. The parser is run in a separate thread and may get ahead
  by your consumer by queue-size items, which defaults to maxint. See
  also source-seq for a non-lazy, single-threaded solution."
  ([source]            (lazy-source-seq source startparse-sax))
  ([source startparse] (lazy-source-seq source startparse Integer/MAX_VALUE))
  ([source startparse queue-size]
   (fill-queue (partial fill-from-sax source startparse)
               :queue-size queue-size)))

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

(defn lazy-parse
  "Convenience function. Parses the source, which can be a File,
  InputStream or String naming a URI, and returns a lazy tree of
  Element records. See lazy-source-seq for finer-grained control."
  [source]
  (event-tree (lazy-source-seq
                (if (instance? Reader source)
                  (InputSource. source)
                  source))))

(defn parse
  "Convenience function. Parses the source, which can be a File,
  InputStream or String naming a URI, and returns a tree of
  Element records. See source-seq for finer-grained control."
  [source]
  (event-tree (source-seq
                (if (instance? Reader source)
                  (InputSource. source)
                  source))))

;=== Emit-related functions ===

(defn- attributes
  "Returns a sax.Attributes instance for the given Event record"
  [e]
  (let [v (vec (:attrs e))]
    (reify org.xml.sax.Attributes
      (getLength [_] (count v))
      (getURI [_ i] (namespace (key (v i))))
      (getLocalName [this i] (.getQName this i))
      (getQName [_ i] (let [k (key (v i))]
                        (str (namespace k)
                             (and (namespace k) \:)
                             (name k))))
      (getValue [_ uri name] (get (:attrs e) name))
      (^String getValue [_ ^int i] (val (v i)))
      (^String getType [_ ^int i] "CDATA"))))

(defn- emit-element
  "Recursively prints the Element e as XML text."
  [e ^org.xml.sax.ContentHandler ch]
  (if (instance? String e)
    (.characters ch (.toCharArray ^String e) 0 (count e))
    (let [nspace (namespace (:tag e))
          qname (name (:tag e))]
      (.startElement ch (or nspace "") qname qname (attributes e))
      (doseq [c (:content e)]
        (emit-element c ch))
      (.endElement ch (or nspace "") qname qname))))
  
(defn emit
  "Prints the given Element tree as XML text to *out*. See element-tree.
  Options:
    :indent <num>            Amount to increase indent depth each time
    :xml-declaration <bool>  True to print <?xml...?>
    :encoding <str>          Character encoding to use"
  [e & {:as opts}]
  (let [content-handler (atom nil)
        trans (-> (javax.xml.transform.TransformerFactory/newInstance)
                .newTransformer)]

    (when (:indent opts)
      (.setOutputProperty trans "indent" "yes")
      (.setOutputProperty trans "{http://xml.apache.org/xslt}indent-amount"
                          (str (:indent opts))))

    (when (contains? opts :xml-declaration)
      (.setOutputProperty trans "omit-xml-declaration" 
                          (if (:xml-declaration opts) "no" "yes")))

    (when (:encoding opts)
      (.setOutputProperty trans "encoding" (:encoding opts)))

    (when (instance? java.io.OutputStreamWriter *out*)
      (let [decl-enc (.getOutputProperty trans "encoding") 
            stream-enc (.getEncoding *out*)]
      (if (not= (Charset/forName decl-enc) (Charset/forName stream-enc))
        (throw (Exception. (str "Output encoding of stream (" stream-enc
                                ") doesn't match declaration ("
                                decl-enc ")"))))))

    (.transform
      trans
      (javax.xml.transform.sax.SAXSource.
        (reify org.xml.sax.XMLReader
          (getContentHandler [_] @content-handler)
          (setDTDHandler [_ handler])
          (setFeature [_ name value])
          (setProperty [_ name value])
          (setContentHandler [_ ch] (reset! content-handler ch))
          (^void parse [_ ^org.xml.sax.InputSource _]
            (when @content-handler
              (.startDocument @content-handler)
              (emit-element e @content-handler)
              (.endDocument @content-handler))))
        (org.xml.sax.InputSource.))
      (javax.xml.transform.stream.StreamResult. *out*))))
