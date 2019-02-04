;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.protocols
  (:require [clojure.data.xml.core :as core]
            [clojure.string :as str]))

;; XML names can be any data type that has at least a namespace uri and a name slot

(defprotocol AsQName
  (qname-local [qname] "Get the name for this qname")
  (qname-uri   [qname] "Get the namespace uri for this qname"))

(defprotocol EventGeneration
  "Protocol for generating new events based on element type"
  (gen-event [item]
    "Function to generate an event for e.")
  (next-events [item next-items]
    "Returns the next set of events that should occur after e.  next-events are the
     events that should be generated after this one is complete."))

(defprotocol AsElements
  (as-elements [expr] "Return a seq of elements represented by an expression."))

(defprotocol AsXmlString
  (xml-str [node] "Serialize atribute value or content node"))

(def push-methods
  '((start-element-event tag attrs nss location-info)
    (end-element-event)
    (empty-element-event tag attrs nss location-info)
    (chars-event string)
    (c-data-event string)
    (comment-event string)
    (q-name-event qname)
    (error-event error)
    (end-event)))

(core/code-gen
 [push-handler state] []
 `(defprotocol PushHandler
    ~@(map (fn [[name & args]]
             (list name (into [push-handler state] args)))
        push-methods)))

(def push-type-name
  (core/kv-from-coll
   (core/juxt-xf first
                 #(-> % first str (str/split #"-")
                      (->> (map str/capitalize))
                      str/join symbol))
   push-methods))

(defn protocol-name [method]
  (symbol "clojure.data.xml.protocols"
          (str method)))

(defprotocol Event
  (push-events [event push-handler state]))
