(ns clojure.data.xml.js.push
  (:require
   [goog.object :as gob]
   [clojure.data.xml.sax-js :as sax]
   [clojure.data.xml.protocols :as p]
   [clojure.data.xml.core :as core]
   [clojure.data.xml.name :as name]
   [clojure.data.xml.tree :as tree]
   [clojure.data.xml.push-handler :as push-handler]))

(defprotocol SaxHandler
  (-open-tag [sh state name attributes])
  (-close-tag [sh state name])
  (-text [sh state string])
  (-cdata [sh state string])
  (-comment [sh state string])
  (-end [sh state])
  (-error [sh state error]))

(defn unwrap-reduced [o]
  (if (reduced? o)
    @o o))

(defn sh-wrapper [& {:keys [open-tag close-tag text cdata comment end error]}]
  (fn [sh]
    (reify SaxHandler
      (-open-tag [_ state name attributes]
        ((or open-tag -open-tag)
         sh state name attributes))
      (-close-tag [_ state name]
        ((or close-tag -close-tag)
         sh state name))
      (-text [_ state string]
        ((or text -text)
         sh state string))
      (-cdata [_ state string]
        ((or cdata -cdata)
         sh state string))
      (-comment [_ state string]
        ((or comment -comment)
         sh state string))
      (-end [_ state]
        ((or end -end)
         sh state))
      (-error [_ state error]
        ((or error -error)
         sh state error)))))

(defn event-xf-sh [xf]
  (reify SaxHandler
    (-open-tag [sh state name attributes]
      (xf state {:type :start
                 :name name
                 :attributes attributes}))
    (-close-tag [sh state name]
      (xf state {:type :end
                 :name name}))
    (-text [sh state string]
      (xf state {:type :chars
                 :str string}))
    (-cdata [sh state string]
      (xf state {:type :cdata
                 :str string}))
    (-comment [sh state string]
      (xf state {:type :comment
                 :str string}))
    (-end [sh state]
      (xf state))
    (-error [sh state error]
      (let [s (xf state {:type :error
                         :error error})]
        (xf (unwrap-reduced s))))))

(defn sh-event-xf [sh]
  (fn
    ([s] (-end sh (unwrap-reduced s)))
    ([s {:keys [type name attributes str error]}]
     (case type
       :start (-open-tag sh s name attributes)
       :end (-close-tag sh s name)
       :chars (-text sh s str)
       :cdata (-cdata sh s str)
       :comment (-comment sh s str)
       :error (-error sh s error)))))

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

  (let [p (parser
           ((push-handler/ph-wrapper
             :chars-event (fn [ph state string]
                            (p/chars-event ph state (.toUpperCase string))))
            tree/push-handler)
           (list (transient []))
           {})]
    (p "<roo")
    (p "t xmlns:a=\"GOO:\" foo=\"bar\" ")
    (p "a:roo=\"ra\">lalala<![CDATA[foo GAARR ")
    (p "bar]]><a:gaga/><!--  la  la --></root")
    (p ">")
    (p))

  )

