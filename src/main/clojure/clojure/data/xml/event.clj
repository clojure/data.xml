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
  (:require [clojure.data.xml.protocols :refer
             [EventGeneration gen-event next-events]]
            [clojure.data.xml.name :refer [merge-nss separate-xmlns]]
            [clojure.data.xml.node :refer [element* cdata xml-comment]]
            [clojure.data.xml.impl :refer [extend-protocol-fns]])
  (:import (clojure.data.xml.node Element CData Comment)))

(definline element-nss* [element]
  `(get (meta ~element) :clojure.data.xml/nss {}))

(defn element-nss
  "Get xmlns environment from element"
  [{:keys [attrs] :as element}]
  (separate-xmlns
   attrs #(merge-nss (element-nss* element) %2)))

; Represents a parse event.
; type is one of :start-element, :end-element, or :characters
(defrecord StartElementEvent [tag attrs nss location-info])
(defrecord EndElementEvent [tag])
(defrecord CharsEvent [str])
(defrecord CDataEvent [str])
(defrecord CommentEvent [str])

;; Event Generation for stuff to show up in generated xml

(let [second-arg #(do %2)
      elem-event-generation
      {:gen-event (fn elem-gen-event [{:keys [tag attrs] :as element}]
                    (separate-xmlns
                     attrs #(->StartElementEvent
                             tag %1 (merge-nss (element-nss* element) %2) nil)))
       :next-events (fn elem-next-events [{:keys [tag content]} next-items]
                      (list* content (->EndElementEvent tag) next-items))}]
  (extend-protocol-fns
   EventGeneration
   (StartElementEvent EndElementEvent CharsEvent CDataEvent CommentEvent)
   {:gen-event identity
    :next-events second-arg}
   (String Boolean Number nil)
   {:gen-event (comp ->CharsEvent str)
    :next-events second-arg}
   CData
   {:gen-event (comp ->CDataEvent :content)
    :next-events second-arg}
   Comment
   {:gen-event (comp ->CommentEvent :content)
    :next-events second-arg}
   (clojure.lang.IPersistentMap Element) elem-event-generation))

(extend-protocol EventGeneration
  
  clojure.lang.Sequential
  (gen-event   [coll]
    (gen-event (first coll)))
  (next-events [coll next-items]
    (if-let [r (seq (rest coll))]
      (cons (next-events (first coll) r) next-items)
      (next-events (first coll) next-items))))

;; Node Generation for events

(defn event-element [event contents]
  (when (instance? StartElementEvent event)
    (element* (:tag event) (:attrs event) contents
              (if-let [loc (:location-info event)]
                {:clojure.data.xml/location-info loc
                 :clojure.data.xml/nss (:nss event)}
                {:clojure.data.xml/nss (:nss event)}))))

(defn event-node [event]
  (cond
    (instance? CharsEvent event) (:str event)
    (instance? CDataEvent event) (cdata (:str event))
    (instance? CommentEvent event) (xml-comment (:str event))
    :else (throw (ex-info "Illegal argument, not an event object" {:event event}))))

(defn event-exit? [event]
  (instance? EndElementEvent event))
