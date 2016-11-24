;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.jvm.parse
  (:require [clojure.string :as str]
            [clojure.data.xml.event :refer
             [->StartElementEvent ->EndElementEvent
              ->CharsEvent ->CDataEvent ->CommentEvent]]
            [clojure.data.xml.impl :refer
             [static-case]]
            [clojure.data.xml.name :refer
             [canonical-name]])
  (:import
   (javax.xml.stream
    XMLInputFactory XMLStreamReader XMLStreamConstants)))

(def ^{:private true} input-factory-props
  {:allocator XMLInputFactory/ALLOCATOR
   :coalescing XMLInputFactory/IS_COALESCING
   :namespace-aware XMLInputFactory/IS_NAMESPACE_AWARE
   :replacing-entity-references XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES
   :supporting-external-entities XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES
   :validating XMLInputFactory/IS_VALIDATING
   :reporter XMLInputFactory/REPORTER
   :resolver XMLInputFactory/RESOLVER
   :support-dtd XMLInputFactory/SUPPORT_DTD})

(defn- attr-prefix [^XMLStreamReader sreader index]
  (let [p (.getAttributePrefix sreader index)]
    (when-not (str/blank? p)
      p)))

(defn- attr-hash [^XMLStreamReader sreader]
  (persistent!
   (reduce (fn [tr i]
             (assoc! tr (canonical-name (.getAttributeNamespace sreader i)
                                        (.getAttributeLocalName sreader i)
                                        (.getAttributePrefix sreader i))
                     (.getAttributeValue sreader i)))
           (transient {})
           (range (.getAttributeCount sreader)))))

(defn- nss-hash [^XMLStreamReader sreader parent-hash]
  (persistent!
   (reduce (fn [tr i]
             (let [ns-pf (.getNamespacePrefix sreader i)]
               (assoc! tr (if (str/blank? ns-pf)
                            :xmlns ns-pf)
                       (.getNamespaceURI sreader i))))
           (transient parent-hash)
           (range (.getNamespaceCount sreader)))))

(defn- location-hash
  [^XMLStreamReader sreader]
  (when-let [location (.getLocation sreader)]
    {:character-offset (.getCharacterOffset location)
     :column-number (.getColumnNumber location)
     :line-number (.getLineNumber location)}))

; Note, sreader is mutable and mutated here in pull-seq, but it's
; protected by a lazy-seq so it's thread-safe.
(defn pull-seq
  "Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to
   be triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete."
  [^XMLStreamReader sreader {:keys [include-node? location-info] :as opts} ns-envs]
  (lazy-seq
   (loop []
     (let [location (when location-info
                      (location-hash sreader))]
       (static-case
         (.next sreader)
         ; condp == (.next sreader)
         XMLStreamConstants/START_ELEMENT
         (if (include-node? :element)
           (let [ns-env (nss-hash sreader (or (first ns-envs) {}))]
             (cons (->StartElementEvent (canonical-name (.getNamespaceURI sreader)
                                                        (.getLocalName sreader)
                                                        (.getPrefix sreader))
                                        (attr-hash sreader)
                                        ns-env
                                        location)
                   (pull-seq sreader opts (cons ns-env ns-envs))))
           (recur))
         XMLStreamConstants/END_ELEMENT
         (if (include-node? :element)
           (do (assert (seq ns-envs) "Balanced end")
               (cons (->EndElementEvent (keyword (.getLocalName sreader)))
                     (pull-seq sreader opts (rest ns-envs))))
           (recur))
         XMLStreamConstants/CHARACTERS
         (if-let [text (and (include-node? :characters)
                            (not (.isWhiteSpace sreader))
                            (.getText sreader))]
           (cons (->CharsEvent text)
                 (pull-seq sreader opts ns-envs))
           (recur))
         XMLStreamConstants/COMMENT
         (if (include-node? :comment)
           (cons (->CommentEvent (.getText sreader))
                 (pull-seq sreader opts ns-envs))
           (recur))
         XMLStreamConstants/END_DOCUMENT
         nil
         ;; Consume and ignore comments, spaces, processing instructions etc
         (recur))))))

(defn- make-input-factory [props]
  (let [fac (XMLInputFactory/newInstance)]
    (doseq [[k v] props
            :when (contains? input-factory-props k)
            :let [prop (input-factory-props k)]]
      (.setProperty fac prop v))
    fac))

(defn make-stream-reader [props source]
  (let [fac (make-input-factory props)]
    ;; Reflection on following line cannot be eliminated via a
    ;; type hint, because s is advertised by fn parse to be an
    ;; InputStream or Reader, and there are different
    ;; createXMLStreamReader signatures for each of those types.
    (.createXMLStreamReader fac source)))

(defn string-source [s]
  (java.io.StringReader. s))
