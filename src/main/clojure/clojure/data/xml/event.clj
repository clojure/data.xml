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
            #_[clojure.data.xml.name :refer [merge-nss separate-xmlns]]
            [clojure.data.xml.node :refer [element* cdata xml-comment]]
            [clojure.data.xml.impl :refer [extend-protocol-fns]])
  (:import (clojure.data.xml.node Element CData Comment)))

; Represents a parse event.
; type is one of :start-element, :end-element, or :characters
(defrecord StartElementEvent [tag attrs nss])
(defrecord EndElementEvent [tag])
(defrecord CharsEvent [str])
(defrecord CDataEvent [str])
(defrecord CommentEvent [str])

;; Event Generation for stuff to show up in generated xml

(let [second-arg #(do %2)]
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
    :next-events second-arg}))

(extend-protocol EventGeneration
  Element
  (gen-event   [element]
    #_(separate-xmlns #(->StartElementEvent
                        (:tag element)
                        %1
                        (merge-nss (get (meta element) :clojure.data.xml/nss {})
                                   %2)))
    ;; FIXME namespace awareness
    (->StartElementEvent
     (:tag element)
     (:attrs element)
     {})
    ;; /FIXME
    )
  (next-events [element next-items]
    (list* (:content element)
           (->EndElementEvent (:tag element))
           next-items))

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
              {:clojure.data.xml/nss (:nss event)})))

(defn event-node [event]
  (cond
    (instance? CharsEvent event) (:str event)
    (instance? CDataEvent event) (cdata (:str event))
    (instance? CommentEvent event) (xml-comment (:str event))
    :else (throw (ex-info "Illegal argument, not an event object" {:event event}))))

(defn event-exit? [event]
  (instance? EndElementEvent event))
