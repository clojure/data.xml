(ns clojure.data.xml.js.push
  (:require
   [clojure.data.xml.sax-js :as sax]))

(defn parser [rfn init-state {:keys [strict trim normalize
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
        s (volatile! init-state)]
    ;; OPEN TAG
    (set! (.-onopentag p)
          #(vswap! s rfn {:type :start
                          :name (.-name %)
                          :attributes (.-attributes %)}))

    ;; CLOSE TAG
    (set! (.-onclosetag p)
          #(vswap! s rfn {:type :end
                          :name %}))

    ;; GET TEXT
    (set! (.-ontext p)
          #(vswap! s rfn {:type :chars
                          :str %}))

    ;; CDATA HANDLING
    (set! (.-oncdata p)
          #(vswap! s rfn {:type :cdata
                          :str %}))

    ;; COMMENTS
    (set! (.-oncomment p)
          #(vswap! s rfn {:type :comment
                          :str %}))

    ;; END PARSING
    (set! (.-onend p)
          #(vswap! s rfn))

    ;; ERROR
    (set! (.-onerror p)
          #(do
             (vswap! s rfn {:type :error
                            :error %})
             (vswap! s rfn)))

    (fn
      ([]
       (.close p)
       @s)
      ([source-part]
       (.write p source-part)))))

(comment

  (let [p (parser conj [] {})]
    (p "<roo")
    (p "t xmlns:a=\"GOO:\" foo=\"bar\" ")
    (p "a:roo=\"ra\"><![CDATA[foo")
    (p "bar]]><!--  la  la --></root")
    (p ">")
    (p))

  )

