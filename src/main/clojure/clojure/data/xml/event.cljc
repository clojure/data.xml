;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.event
  "Data type for xml pull events"
  {:author "Herwig Hochleitner"}
  (:require [clojure.data.xml.protocols :as p :refer
             [Event EventGeneration gen-event next-events xml-str]]
            [clojure.data.xml.name :refer [separate-xmlns]]
            [clojure.data.xml.node :as node :refer [element* cdata xml-comment]]
            [clojure.data.xml.pu-map :as pu]
            [clojure.data.xml.core :refer [code-gen unwrap-reduced]]
            [clojure.string :as str]
            [clojure.set :as set]
            [clojure.data.xml.core :as core])
  #?(:cljs (:require-macros clojure.data.xml.event)))


(defn element-nss* [element]
  (get (meta element) :clojure.data.xml/nss pu/EMPTY))

(defn element-nss
  "Get xmlns environment from element"
  [{:keys [attrs] :as element}]
  (separate-xmlns
   attrs #(pu/merge-prefix-map (element-nss* element) %2)))

(def push-methods
  ;; TODO move to protocols
  '((start-element-event tag attrs nss location-info)
    (end-element-event)
    (empty-element-event tag attrs nss location-info)
    (chars-event string)
    (c-data-event string)
    (comment-event string)
    (q-name-event qname)
    (error-event error)
    (end-event)))

(def type-name
  ;; TODO move to protocols
  (core/kv-from-coll
   (core/juxt-xf first
                 #(-> % first str (str/split #"-")
                      (->> (map str/capitalize))
                      str/join symbol))
   push-methods))

(defn constructor-name [method]
  ;; TODO move to protocols
  (symbol "clojure.data.xml.event"
          (str "->" (type-name method))))

(defn protocol-name [method]
  ;; TODO move to protocols
  (symbol "clojure.data.xml.protocols"
          (str method)))

(code-gen
 [_ push-events] [push-handler state]
 `(do
    ~@(eduction
       (map (fn [[method & args]]
              `(defrecord ~(type-name method) [~@args]
                 Event
                 (~push-events [~_ ~push-handler ~state]
                  (~(protocol-name method) ~push-handler ~state ~@args)))))
       push-methods)))

;; Node Generation for events

(defn event-element
  ([event contents]
   (when (or (instance? StartElementEvent event)
             (instance? EmptyElementEvent event))
     (event-element (:tag event)
                    (:attrs event)
                    (:nss event)
                    (:location-info event)
                    contents)))
  ([tag attrs nss location-info contents]
   (element* tag attrs contents
             (if location-info
               {:clojure.data.xml/location-info location-info
                :clojure.data.xml/nss nss}
               {:clojure.data.xml/nss nss}))))

(defn event-node [event]
  (cond
    (instance? CharsEvent event) (:string event)
    (instance? CDataEvent event) (cdata (:string event))
    (instance? CommentEvent event) (xml-comment (:string event))
    :else (throw (ex-info "Illegal argument, not an event object" {:event event}))))

(defn event-exit? [event]
  (instance? EndElementEvent event))
