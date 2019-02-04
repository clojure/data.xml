(ns clojure.data.xml.push-handler
  "A PushHandler can be translated to and from a Transducer, that would expect a push event.

  It provides optimization opportunities, by eliminating intermediate Event allocations.
  It does this, by unrolling push events into a protocol, in the fashion of SAX."
  (:require [clojure.data.xml.protocols :as p :refer [PushHandler push-events]]
            [clojure.data.xml.event :as event]
            [clojure.string :as str]
            [clojure.data.xml.core :as core :refer [code-gen unwrap-reduced]]))

(code-gen
 [_] [ph state overrides]
 (let [method-gensym (core/kv-from-coll (map first)
                                        core/split
                                        (core/map-vals (comp gensym str))
                                        p/push-methods)]
   `(defn ~'ph-wrapper
      "Wrap PushHandler, overriding only specified methods"
      [& {:as ~overrides}]
      (fn [~ph]
        (let [~@(eduction
                 (map first)
                 (mapcat (juxt
                          #(method-gensym %)
                          #(do `(get ~overrides ~(keyword %)
                                     ~(p/protocol-name %)))))
                 p/push-methods)]
          (reify PushHandler
            ~@(eduction
                  (map (fn [[method & args]]
                         `(~method [~_ ~state ~@args]
                           (~(method-gensym method)
                            ~ph ~state ~@args))))
                  p/push-methods)))))))

(defn event-xf-ph
  "Get PushHandler from event-expecting transducer"
  [xf]
  (code-gen
   [_ xf end-event] [state ph]
   `(reify PushHandler
      ~@(eduction
            (remove (comp #{end-event} first))
            (map (fn [[method & args]]
                   `(~method [~_ ~state ~@args]
                     (~xf ~state
                      (~(event/constructor-name method) ~@args)))))
            p/push-methods)
      (~end-event [~_ ~state]
       (~xf ~state)))))

(defn ph-event-xf
  "Get event-expecting transducer from PushHandler"
  [ph]
  (fn
    ([s] (p/end-event ph s))
    ([s event]
     (push-events event ph s))))
