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
             [pu-map :as pu]
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
  (emit-event [event ^XMLStreamWriter writer prefix-uri-stack]))

(defn check-stream-encoding [^OutputStreamWriter stream xml-encoding]
  (when (not= (Charset/forName xml-encoding) (Charset/forName (.getEncoding stream)))
    (throw (ex-info (str "Output encoding of writer (" (.getEncoding stream)
                         ") doesn't match declaration ("
                         xml-encoding ")")
                    {:stream-encoding (.getEncoding stream)
                     :declared-encoding xml-encoding}))))

(defn- emit-attrs [^XMLStreamWriter writer pu attrs]
  (reduce-kv
   (fn [_ attr value]
     (let [uri (qname-uri attr)
           local (qname-local attr)]
       (if (str/blank? uri)
         (.writeAttribute writer                         local value)
         (.writeAttribute writer (pu/get-prefix pu uri) uri local value)))
     _)
   nil attrs))

(defn- emit-ns-attrs [^XMLStreamWriter writer parent-pu pu]
  (pu/reduce-diff
   (fn [_ pf uri]
     (if (str/blank? pf)
       (.writeDefaultNamespace writer uri)
       (.writeNamespace writer pf uri))
     _)
   nil parent-pu pu))

(defn- compute-prefix [tpu uri suggested]
  (or (pu/get-prefix tpu uri)
      (loop [prefix (or suggested (gen-prefix))]
        (if (pu/get tpu prefix)
          (recur (gen-prefix))
          prefix))))

(defn- compute-pu [pu ns-attrs attr-uris tag-uri tag-local]
  (let [tpu (pu/transient pu)
        ;; add namespaces from current environment
        tpu (reduce-kv (fn [tpu ns-attr uri]
                         (pu/assoc! tpu
                                    (if (str/blank? (qname-uri ns-attr))
                                      (do (assert (= "xmlns" (qname-local ns-attr))
                                                  "non-prefixed attribute, that's not xmlns= is not a namespace attr")
                                          "")
                                      (compute-prefix tpu uri (qname-local ns-attr)))
                                    uri))
                       tpu ns-attrs)
        ;; add implicit namespaces used by tag, attrs
        tpu (reduce (fn [tpu uri]
                      (pu/assoc! tpu (compute-prefix tpu uri nil) uri))
                    tpu (if (str/blank? tag-uri)
                          attr-uris
                          (cons tag-uri attr-uris)))
        ;; rename default namespace, if tag is global (not in a namespace)
        tpu (if-let [uri (and (str/blank? tag-uri)
                              (pu/get tpu ""))]
              (do
                (when (.isLoggable logger Level/FINE)
                  (.log logger Level/FINE
                        (format "Default `xmlns=\"%s\"` had to be replaced with a `xmlns=\"\"` because of global element `%s`"
                                uri tag-local)))
                (-> tpu
                    (pu/assoc! "" "")
                    (as-> tpu (pu/assoc! tpu (compute-prefix tpu uri nil) uri))))
              tpu)]
    (pu/persistent! tpu)))

(defn- emit-start-tag [{:keys [attrs nss tag]} ^XMLStreamWriter writer prefix-uri-stack]
  (let [uri   (qname-uri tag)
        local (qname-local tag)
        parent-pu (first prefix-uri-stack)
        pu (compute-pu parent-pu nss (map qname-uri (keys attrs)) uri local)]
    (if (str/blank? uri)
      (.writeStartElement writer local)
      (.writeStartElement writer (pu/get-prefix pu uri) local uri))
    (emit-ns-attrs writer parent-pu pu)
    (emit-attrs writer pu attrs)
    (cons pu prefix-uri-stack)))

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
  (emit-event [ev writer pu-stack] (emit-start-tag ev writer pu-stack))
  EndElementEvent
  (emit-event [ev writer pu-stack]
    (assert (next pu-stack) "balanced tags")
    (.writeEndElement writer)
    (next pu-stack))
  CharsEvent
  (emit-event [{:keys [str]} writer s] (.writeCharacters writer str) s)
  CDataEvent
  (emit-event [{:keys [str]} writer s] (emit-cdata str writer) s)
  CommentEvent
  (emit-event [{:keys [str]} writer s] (.writeComment writer str) s))

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
      (reduce #(emit-event %2 writer %1) [pu/EMPTY] events)
      (.writeEndDocument writer)
      swriter)))

(defn string-writer []
  (StringWriter.))
