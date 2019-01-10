(ns clojure.data.xml.js.push
  (:require
   [clojure.data.xml.sax-js :as sax]))

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

(defn parser [sh init-state
              {:keys [strict trim normalize
                      lowercase xmlns position
                      strict-entities]
               :or {strict true
                    trim false
                    normalize false
                    lowercase true
                    position true
                    strict-entities false
                    xmlns true}}]
  (let [p (sax/parser strict #js {"trim" trim
                                  "normalize" normalize
                                  "lowercase" lowercase
                                  "xmlns" xmlns
                                  "position" position
                                  "strictEntities" strict-entities})
        sh-drive (fn [state action sax-handler & args]
                   (if (reduced? state)
                     state
                     (let [res (apply action sax-handler state args)]
                       (if (reduced? res)
                         (do (.close p)
                             (ensure-reduced (-end sax-handler @res)))
                         res))))
        s (volatile! init-state)]
    ;; OPEN TAG
    (set! (.-onopentag p)
          #(vswap! s sh-drive -open-tag sh (.-name %) (.-attributes %)))

    ;; CLOSE TAG
    (set! (.-onclosetag p)
          #(vswap! s sh-drive -close-tag sh %))

    ;; GET TEXT
    (set! (.-ontext p)
          #(vswap! s sh-drive -text sh %))

    ;; CDATA HANDLING
    (set! (.-oncdata p)
          #(vswap! s sh-drive -cdata sh %))

    ;; COMMENTS
    (set! (.-oncomment p)
          #(vswap! s sh-drive -comment sh %))

    ;; END PARSING
    (set! (.-onend p)
          #(vswap! s sh-drive -end sh))

    ;; ERROR
    (set! (.-onerror p)
          #(vswap! s sh-drive -error sh %))

    (fn
      ([]
       (.close p)
       @s)
      ([source-part]
       (.write p source-part)))))

(comment

  (let [p (parser ((comp (sh-wrapper
                          :text (fn [sh state string]
                                  (-text sh state (.toUpperCase string))))
                         (sh-wrapper
                          :cdata (fn [sh state string]
                                   (-cdata sh state (.toLowerCase string)))))
                   (event-xf-sh
                    conj)) [] {})]
    (p "<roo")
    (p "t xmlns:a=\"GOO:\" foo=\"bar\" ")
    (p "a:roo=\"ra\">lalala<![CDATA[foo GAARR ")
    (p "bar]]><!--  la  la --></root")
    (p ">")
    (p))

  )

