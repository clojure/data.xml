;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.jvm.parse
  (:require [clojure.string :as str]
            [clojure.data.xml.protocols :as p]
            [clojure.data.xml.event :refer
             [->StartElementEvent ->EmptyElementEvent ->EndElementEvent
              ->CharsEvent ->CDataEvent ->CommentEvent]]
            [clojure.data.xml.impl :refer
             [static-case]]
            [clojure.data.xml.name :refer
             [qname]]
            [clojure.data.xml.pu-map :as pu]
            [clojure.data.xml.core :as core]
            [clojure.data.xml.push-handler :as push-handler]
            [clojure.data.xml.tree :as tree]
            [clojure.java.io :as io])
  (:import
   (java.io InputStream Reader)
   (javax.xml.stream
    XMLInputFactory XMLStreamReader XMLStreamConstants)
   (clojure.data.xml.event EndElementEvent)))

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
             (assoc! tr (qname (.getAttributeNamespace sreader i)
                               (.getAttributeLocalName sreader i)
                               (.getAttributePrefix sreader i))
                     (.getAttributeValue sreader i)))
           (transient {})
           (range (.getAttributeCount sreader)))))

(defn- nss-hash [^XMLStreamReader sreader parent-hash]
  (pu/persistent!
   (reduce (fn [tr ^long i]
             (pu/assoc! tr
                        (.getNamespacePrefix sreader i)
                        (.getNamespaceURI ^XMLStreamReader sreader i)))
           (pu/transient parent-hash)
           (range (.getNamespaceCount sreader)))))

(defn- location-hash
  [^XMLStreamReader sreader]
  (when-let [location (.getLocation sreader)]
    {:character-offset (.getCharacterOffset location)
     :column-number (.getColumnNumber location)
     :line-number (.getLineNumber location)}))

(defn push
  "Read events off an XMLStreamReader and push them into a handler stack.
  In a tight loop.

  Fundamentally, this drives a (push-handler - faced) transducer
  stack, but it is reconciled with lazy sequences, by supporting a
  continuation protocol:

  If a push returns a (reduced state), the push loop is terminated, as
  per reducers protocol. However, the returned state is wrapped into

      {:state transformed-init
       :continue (fn continue [replacement-state] ...)}

  continue can be called with an updated state that has its reduced?
  condition cleared."
  [push-handler init ^XMLStreamReader sreader {:keys [include-node? location-info skip-whitespace] :as opts} ns-envs]
  (loop [state init
         ns-envs ns-envs]
    (if (reduced? state)
      {:state (p/end-event push-handler @state)
       :continue #(push push-handler % sreader opts ns-envs)}
      (let [location (when location-info
                       (location-hash sreader))]
        (static-case
         (.next sreader)
         XMLStreamConstants/START_ELEMENT
         (if (include-node? :element)
           (let [ns-env (nss-hash sreader (or (first ns-envs) pu/EMPTY))
                 tag (qname (.getNamespaceURI sreader)
                            (.getLocalName sreader)
                            (.getPrefix sreader))
                 attrs (attr-hash sreader)]
             (recur
              (p/start-element-event push-handler state tag attrs ns-env location)
              (cons ns-env ns-envs)))
           (recur state ns-envs))
         XMLStreamConstants/END_ELEMENT
         (if (include-node? :element)
           (do (assert (seq ns-envs) "Balanced end")
               (recur (p/end-element-event push-handler state)
                      (next ns-envs)))
           (recur state ns-envs))
         (list XMLStreamConstants/CHARACTERS
               XMLStreamConstants/SPACE)
         (let [text (and (include-node? :characters)
                         (not (and skip-whitespace
                                   (.isWhiteSpace sreader)))
                         (.getText sreader))]
           (recur
            (if (and text (pos? (.length ^CharSequence text)))
              (p/chars-event push-handler state text)
              state)
            ns-envs))
         XMLStreamConstants/COMMENT
         (let [text (and (include-node? :comment)
                         (.getText sreader))]
           (recur
            (if (and text (pos? (.length ^CharSequence text)))
              (p/chars-event push-handler state text)
              state)
            ns-envs))
         XMLStreamConstants/CDATA
         (let [text (and (include-node? :cdata)
                         (.getText sreader))]
           (recur
            (if (and text (pos? (.length ^CharSequence text)))
              (p/c-data-event push-handler state text)
              state)
            ns-envs))
         XMLStreamConstants/END_DOCUMENT
         {:state (p/end-event push-handler state)}
         ;; Consume and ignore comments, spaces, processing instructions etc
         (recur state ns-envs))))))

(defn run-push*
  [ph sreader opts ns-envs]
  (first
   (:state
    (push ph (list (transient []))
          sreader opts ns-envs))))

(defn run-push [sreader opts ns-envs]
  (run-push* tree/push-handler opts ns-envs))

(defn chunk-filler
  ([]
   (chunk-buffer 32))
  ([b]
   (chunk b))
  ([b e]
   (chunk-append b e)
   (if (>= 32 (count b))
     b
     (reduced b))))

(defn pull-seq* [{:keys [state continue]}]
  (chunk-cons
   state
   (when continue
     (lazy-seq
      (pull-seq* (continue (chunk-filler)))))))

(defn pull-seq
  "Creates a seq of events."
  ([sreader opts ns-envs] (pull-seq identity sreader opts ns-envs))
  ([xform sreader opts ns-envs]
   (lazy-seq
    (pull-seq*
     (push (push-handler/event-xf-ph
            (xform
             chunk-filler))
           (chunk-filler)
           sreader opts ns-envs)))))

(defn- make-input-factory ^XMLInputFactory [props]
  (let [fac (XMLInputFactory/newInstance)]
    (doseq [[k v] props
            :when (contains? input-factory-props k)
            :let [prop (input-factory-props k)]]
      (.setProperty fac prop v))
    fac))

(defn make-stream-reader [props source]
  (let [fac (make-input-factory props)]
    (cond
      (instance? Reader source) (.createXMLStreamReader fac ^Reader source)
      (instance? InputStream source) (.createXMLStreamReader fac ^InputStream source)
      :else (throw (IllegalArgumentException.
                     "source should be java.io.Reader or java.io.InputStream")))))

(defn string-source [s]
  (java.io.StringReader. s))
