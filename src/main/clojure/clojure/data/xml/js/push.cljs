(ns clojure.data.xml.js.push
  (:require
   [goog.object :as gob]
   [clojure.data.xml.sax-js :as sax]
   [clojure.data.xml.protocols :as p]
   [clojure.data.xml.core :as core]
   [clojure.data.xml.name :as name]
   [clojure.data.xml.tree :as tree]
   [clojure.data.xml.push-handler :as push-handler]))

(defn qname [qn]
  (name/qname (gob/get qn "uri")
              (gob/get qn "local")
              (gob/get qn "prefix")))

(defn attributes [ao cont]
  (name/separate-xmlns
   (core/kv-from-coll
    (map #(gob/get ao %))
    (core/juxt-xf qname #(gob/get % "value"))
    (js-keys ao))
   cont))

(defn parser [{:keys [strict trim normalize
                      lowercase xmlns position
                      strict-entities]
               :or {strict true
                    trim false
                    normalize false
                    lowercase true
                    position true
                    strict-entities false
                    xmlns true}}]
  (sax/parser strict #js {"trim" trim
                          "normalize" normalize
                          "lowercase" lowercase
                          "xmlns" xmlns
                          "position" position
                          "strictEntities" strict-entities}))

(defn parser-xf [opts]
  (fn [push-handler]
    (let [p (parser opts)
          actions (volatile! [])
          ph-drive (fn [state action & args]
                     (if (reduced? state)
                       (do (vswap! actions conj
                                   #(apply action push-handler % args))
                           state)
                       (let [res (apply action push-handler state args)]
                         (if (reduced? res)
                           (p/end-event push-handler @res)
                           res))))
          state (volatile! nil)]
      ;; OPEN TAG
      (set! (.-onopentag p)
            #(attributes
              (.-attributes %)
              (fn [attrs nss]
                (vswap! state ph-drive p/start-element-event (qname %) attrs nss nil))))

      ;; CLOSE TAG
      (set! (.-onclosetag p)
            #(vswap! state ph-drive p/end-element-event))

      ;; GET TEXT
      (set! (.-ontext p)
            #(vswap! state ph-drive p/chars-event %))

      ;; CDATA HANDLING
      (set! (.-oncdata p)
            #(vswap! state ph-drive p/c-data-event %))

      ;; COMMENTS
      (set! (.-oncomment p)
            #(vswap! state ph-drive p/comment-event %))

      ;; END PARSING
      (set! (.-onend p)
            #(vswap! state ph-drive p/end-event))

      ;; ERROR
      (set! (.-onerror p)
            #(vswap! state ph-drive p/error-event %))
      (fn
        ([s]
         (vreset! state s)
         (.close p)
         @state)
        ([s string]
         (vreset! state s)
         (.write p string)
         @state)))))

(comment
  (first
   (transduce
    (parser-xf {})
    tree/push-handler
    (list (transient []))
    ["<roo"
     "t xmlns:a=\"GOO:\" foo=\"bar\" "
     "a:roo=\"ra\">lalala<![CDATA[foo GAARR "
     "bar]]><a:gaga/><!--  la  la --></root"
     ">"]))

  (-> (tree/event-tree
       (sequence
        (comp (parser-xf {})
              push-handler/event-xf-ph)
        ["<roo"
         "t xmlns:a=\"GOO:\" foo=\"bar\" "
         "a:roo=\"ra\">lalala<![CDATA[foo GAARR "
         "bar]]><a:gaga/><!--  la  la --></root"
         ">"])))

  )

