;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.jvm.emit
  "JVM implementation of the emitter details"
  {:author "Herwig Hochleitner"}
  (:require (clojure.data.xml
             #_[name :refer [qname-uri qname-local separate-xmlns]]
             event)
            [clojure.string :as str])
  (:import (java.io OutputStreamWriter Writer StringWriter)
           (java.nio.charset Charset)
           (javax.xml.namespace NamespaceContext)
           (javax.xml.stream XMLStreamWriter XMLOutputFactory)
           (javax.xml.transform OutputKeys Transformer
                                TransformerFactory)
           (clojure.data.xml.event StartElementEvent EndElementEvent CharsEvent CDataEvent CommentEvent)))

(defprotocol EventEmit
  (emit-event [event ^XMLStreamWriter writer]))

(defn check-stream-encoding [^OutputStreamWriter stream xml-encoding]
  (when (not= (Charset/forName xml-encoding) (Charset/forName (.getEncoding stream)))
    (throw (Exception. (str "Output encoding of stream (" xml-encoding
                            ") doesn't match declaration ("
                            (.getEncoding stream) ")")))))

;; properly namespace aware version
#_(defn- emit-attrs [^XMLStreamWriter writer attrs]
    (doseq [[k v] attrs]
      (let [uri   (qname-uri k)
            local (qname-local k)]
        (if (str/blank? uri)
          (.writeAttribute writer uri local (str v))
          (.writeAttribute writer     local (str v))))))

;; The changes to the xmlns must be set before .writeStartElement
#_(defn- set-xmlns-attributes [^XMLStreamWriter writer ns-attrs]
    ;; leave a list of thunks to write the attributes
    (reduce (fn [left [k v]]
              (let [local (qname-local k)]
                (or (if (= "xmlns" local)
                      (when-not (= v (.. writer getNamespaceContext (getNamespaceURI "")))
                        (.setDefaultNamespace writer v)
                        (conj left #(.writeDefaultNamespace writer v)))
                      (when-let [prefix (and (str/blank? (.getPrefix writer v))
                                             (if (.. writer getNamespaceContext
                                                     (getNamespaceURI local))
                                               ;; rename clashing prefixes
                                               (str (gensym local))
                                               local))]
                        (.setPrefix writer prefix v)
                        (conj left #(.writeNamespace writer prefix v))))
                    left)))
            [] ns-attrs))

#_(defn- emit-start-tag [{:keys [attrs nss tag]} ^XMLStreamWriter writer]
    (let [ns-attrs (set-xmlns-attributes writer nss)
          uri   (qname-uri tag)
          local (qname-local tag)]
      (separate-xmlns
       attrs (fn [attrs xmlns]
               (set-xmlns-attributes writer xmlns)
               (.writeStartElement writer uri local)
               (emit-attrs writer attrs)))))

;; FIXME replace broken xmlns handling with code namespace aware version

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

(defn emit-start-tag [event ^javax.xml.stream.XMLStreamWriter writer]
  (let [[nspace qname] (qualified-name (:tag event))]
    (.writeStartElement writer "" qname (or nspace ""))
    (write-attributes (:attrs event) writer)))

;; /FIXME

(defn- emit-cdata [^String cdata-str ^XMLStreamWriter writer]
  (when-not (str/blank? cdata-str)
    (let [idx (.indexOf cdata-str "]]>")]
      (if (= idx -1)
        (.writeCData writer cdata-str )
        (do
          (.writeCData writer (subs cdata-str 0 (+ idx 2)))
          (recur (subs cdata-str (+ idx 2)) writer))))))

(extend-protocol EventEmit
  StartElementEvent
  (emit-event [ev writer] (emit-start-tag ev writer))
  EndElementEvent
  (emit-event [ev writer] (.writeEndElement writer))
  CharsEvent
  (emit-event [{:keys [str]} writer] (.writeCharacters writer str))
  CDataEvent
  (emit-event [{:keys [str]} writer] (emit-cdata str writer))
  CommentEvent
  (emit-event [{:keys [str]} writer] (.writeComment writer str)))

;; Writers

(defn write-document
  "Writes the given event seq as XML text to writer.
   Options:
    :encoding <str>          Character encoding to use"
  [^Writer swriter events opts]
  (let [^XMLStreamWriter writer (-> (XMLOutputFactory/newInstance)
                                    (.createXMLStreamWriter swriter))]

    (when (instance? OutputStreamWriter swriter)
      (check-stream-encoding swriter (or (:encoding opts) "UTF-8")))

    (.writeStartDocument writer (or (:encoding opts) "UTF-8") "1.0")
    (doseq [event events] (emit-event event writer))
    (.writeEndDocument writer)
    swriter))

(defn string-writer []
  (StringWriter.))
