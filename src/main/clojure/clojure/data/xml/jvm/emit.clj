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
             [name :refer [qname-uri qname-local separate-xmlns gen-prefix *gen-prefix-counter*]]
             event)
            [clojure.string :as str])
  (:import (java.io OutputStreamWriter Writer StringWriter)
           (java.nio.charset Charset)
           (java.util.logging Logger Level)
           (javax.xml.namespace NamespaceContext)
           (javax.xml.stream XMLStreamWriter XMLOutputFactory)
           (javax.xml.transform OutputKeys Transformer
                                TransformerFactory)
           (clojure.data.xml.event StartElementEvent EndElementEvent CharsEvent CDataEvent CommentEvent)))

(def logger (Logger/getLogger "clojure.data.xml"))

(defprotocol EventEmit
  (emit-event [event ^XMLStreamWriter writer]))

(defn check-stream-encoding [^OutputStreamWriter stream xml-encoding]
  (when (not= (Charset/forName xml-encoding) (Charset/forName (.getEncoding stream)))
    (throw (Exception. (str "Output encoding of stream (" xml-encoding
                            ") doesn't match declaration ("
                            (.getEncoding stream) ")")))))

;; properly namespace aware version
(defn- emit-attrs [^XMLStreamWriter writer attrs]
  (doseq [[k v] attrs]
    (let [uri   (qname-uri k)
          local (qname-local k)]
      (if (str/blank? uri)
        (.writeAttribute writer     local (str v))
        (.writeAttribute writer uri local (str v))))))

(defn- make-prefix [^NamespaceContext nc]
  (let [pf (gen-prefix)]
    (if (str/blank? (.getNamespaceURI nc pf))
      pf (recur nc))))

(defn- write-xmlns-attribute [^XMLStreamWriter writer k v]
  (if (str/blank? k)
    (do (.setDefaultNamespace writer v)
        (.writeDefaultNamespace writer v))
    (do (.setPrefix writer v k)
        (.writeNamespace writer v k)))
  writer)

(defn- get-prefix [^XMLStreamWriter writer temp-xmlns uri]
  (or (get temp-xmlns uri)
      (.getPrefix writer uri)))

(defn- xmlns-attribute-set [^XMLStreamWriter writer ns-attrs used-uris]
  (let [tleft (transient {})
        tleft (reduce-kv (fn [tleft k v]
                           (let [local (qname-local k)]
                             (or (if (= "xmlns" local)
                                   (when-not (= (str v)
                                                (str (.. writer getNamespaceContext (getNamespaceURI ""))))
                                     (assoc! tleft v ""))
                                   (when-let [prefix (and (str/blank? (get-prefix writer tleft v))
                                                          (if (.. writer getNamespaceContext
                                                                  (getNamespaceURI local))
                                                            ;; rename clashing prefixes
                                                            (make-prefix (.getNamespaceContext writer))
                                                            local))]
                                     (assoc! tleft v prefix)))
                                 tleft)))
                         tleft ns-attrs)]
    (persistent!
     (reduce (fn [tleft uri]
               (if (and (not (str/blank? uri))
                        (nil? (get-prefix writer tleft uri)))
                 (assoc! tleft uri (make-prefix (.getNamespaceContext writer)))
                 tleft))
             tleft used-uris))))

(defn- emit-start-tag [{:keys [attrs nss tag]} ^XMLStreamWriter writer]
  (let [uri   (qname-uri tag)
        local (qname-local tag)
        global (str/blank? uri)
        xmlns-attrs (xmlns-attribute-set
                     writer
                     (if global
                       (let [default (get nss :xmlns)]
                         (when (and
                                (not (str/blank? default))
                                (.isLoggable logger Level/FINE))
                           (.log logger Level/FINE
                                 (format "Default `xmlns=\"%s\"` had to be replaced with a `xmlns=\"\"` because of global element `%s`" default local)))
                         (assoc nss :xmlns ""))
                       nss)
                     (cons uri (map qname-uri (keys attrs))))]
    (if global
      (.writeStartElement writer local)
      (.writeStartElement writer (get-prefix writer xmlns-attrs uri)
                          local uri))
    (reduce-kv write-xmlns-attribute writer xmlns-attrs)
    (emit-attrs writer attrs)))

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
    :encoding <str>          Character encoding to use
    :doctype  <str>          Document type (DOCTYPE) declaration to use"
  [^Writer swriter events opts]
  (binding [*gen-prefix-counter* 0]
    (let [^XMLStreamWriter writer (-> (XMLOutputFactory/newInstance)
                                      (.createXMLStreamWriter swriter))]

      (when (instance? OutputStreamWriter swriter)
        (check-stream-encoding swriter (or (:encoding opts) "UTF-8")))

      (.writeStartDocument writer (or (:encoding opts) "UTF-8") "1.0")
      (when-let [doctype (:doctype opts)]
        (.writeDTD writer doctype))
      (doseq [event events] (emit-event event writer))
      (.writeEndDocument writer)
      swriter)))

(defn string-writer []
  (StringWriter.))
