(ns clojure.data.xml.jvm.event
  (:import (clojure.data.xml.node Element CData Comment)
           (clojure.lang Sequential IPersistentMap Keyword)
           (java.net URI URL)
           (java.util Date)
           (javax.xml.namespace QName))
  (:require [clojure.data.xml.impl :refer [extend-protocol-fns compile-if]]
            [clojure.data.xml.node :as node :refer [element* cdata xml-comment]]
            [clojure.data.xml.name :refer [separate-xmlns]]
            [clojure.data.xml.protocols :as p :refer
             [Event EventGeneration gen-event next-events xml-str]]))

(let [push-string (fn [string push-handler state]
                    (p/chars-event push-handler state (xml-str string)))
      push-qname (fn [qname push-handler state]
                   (p/q-name-event push-handler state qname))]
  (extend-protocol-fns
   Event
   (String Boolean Number (Class/forName "[B") Date URI URL nil)
   {:push-events push-string}
   (Keyword QName)
   {:push-events push-qname}
   IPersistentMap
   {:push-events node/push-element}
   Sequential
   {:push-events node/push-content})
  (compile-if
   (Class/forName "java.time.Instant")
   (extend java.time.Instant
     Event
     {:push-events push-string})
   nil))

(let [second-arg #(do %2)
      elem-event-generation
      {:gen-event (fn elem-gen-event [{:keys [tag attrs content] :as element}]
                    (separate-xmlns
                     attrs #((if (seq content)
                               ->StartElementEvent ->EmptyElementEvent)
                             tag %1 (pu/merge-prefix-map (element-nss* element) %2) nil)))
       :next-events (fn elem-next-events [{:keys [tag content]} next-items]
                      (if (seq content)
                        (list* content end-element-event next-items)
                        next-items))}
      string-event-generation {:gen-event (comp ->CharsEvent #'xml-str)
                               :next-events second-arg}
      qname-event-generation {:gen-event ->QNameEvent
                              :next-events second-arg}]
  (extend-protocol-fns
   EventGeneration
   (StartElementEvent EmptyElementEvent EndElementEvent CharsEvent CDataEvent CommentEvent)
   {:gen-event identity
    :next-events second-arg}
   (String Boolean Number (Class/forName "[B") Date URI URL nil)
   string-event-generation
   (Keyword QName) qname-event-generation
   CData
   {:gen-event (comp ->CDataEvent :content)
    :next-events second-arg}
   Comment
   {:gen-event (comp ->CommentEvent :content)
    :next-events second-arg}
   (IPersistentMap Element) elem-event-generation)
  (compile-if
   (Class/forName "java.time.Instant")
   (extend java.time.Instant
     EventGeneration
     string-event-generation)
   nil))

(extend-protocol EventGeneration
  Sequential
  (gen-event   [coll]
    (gen-event (first coll)))
  (next-events [coll next-items]
    (if-let [r (seq (rest coll))]
      (cons (next-events (first coll) r) next-items)
      (next-events (first coll) next-items))))
