(ns clojure.data.xml.push-handler
  (:require [clojure.data.xml.protocols :as p :refer [PushHandler]]
            [clojure.data.xml.event]
            [clojure.string :as str])
  #?(:cljs (:require-macros clojure.data.xml.push-handler)))

(defn unwrap-reduced [o]
  (if (reduced? o)
    @o o))

#?
(:clj
 (defmacro code-gen [plainsyms gensyms expr]
   (eval `(let ~(-> []
                    (into
                     (mapcat (juxt identity #(list 'quote %)))
                     plainsyms)
                    (into
                     (mapcat (juxt identity #(list 'quote (gensym (str %)))))
                     gensyms))
            ~expr))))

(def methods
  '((start-element-event tag attrs nss location-info)
    (end-element-event tag)
    (empty-element-event tag attrs nss location-info)
    (chars-event string)
    (c-data-event string)
    (comment-event string)
    (q-name-event qname)
    (end-event)
    (error-event error)))

(defn sym [& parts]
  (symbol (apply str parts)))

(code-gen
 [_] [sh state overrides]
 (let [method-rs (into {}
                       (map (juxt first (comp gensym str first)))
                       methods)]
   `(defn ~'sh-wrapper [~overrides]
      (fn [~sh]
        (let [~@(eduction
                 (map first)
                 (mapcat (juxt #(method-rs %)
                               #(do `(get ~overrides ~(keyword %)
                                          ~(symbol "clojure.data.xml.protocols" (name %))))))
                 methods)]
          (reify PushHandler
            ~@(eduction
                  (map (fn [[method & args]]
                         `(~method [~_ ~state ~@args]
                           (~(method-rs method)
                            ~sh ~state ~@args))))
                  methods)))))))

(defn event-p-method [method]
  (-> method str
      (str/split #"-")
      (->> (map str/capitalize))
      str/join
      (->> (symbol "clojure.data.xml.protocols" ))))

(defn event-constructor [method]
  (-> method str
      (str/split #"-")
      (->> (map str/capitalize))
      str/join
      (->> (str "->")
           (symbol "clojure.data.xml.event"))))

(defn event-xf-sh [xf]
  (code-gen
   [_ xf end-event] [state sh]
   `(reify PushHandler
      ~@(eduction
            (remove (comp #{end-event} first))
            (map (fn [[method & args]]
                   `(~method [~_ ~state ~@args]
                     (~xf ~state
                      (~(event-constructor method) ~@args)))))
            methods)
      (~end-event [~_ ~state]
       (~xf ~state)))))

(defn sh-event-xf [sh]
  (fn
    ([s] (p/end-event sh (unwrap-reduced s)))
    ([s event #_{:keys [type name attributes str error]}]
     
     #_(code-gen
        [sh s event] []
        `(case type
           ~@(eduction
              (mapcat (fn [[method & args]]
                        [(keyword method)
                         `(~(symbol "clojure.data.xml.protocols" (name method))
                           ~sh ~s
                           ~@(eduction
                              (map (fn [arg]
                                     `(get ~event ~(keyword arg))))
                              args))]))
              methods)))
     )))

(comment

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
        :error (-error sh s error))))))
