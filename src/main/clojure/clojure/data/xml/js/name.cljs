(ns clojure.data.xml.js.name
  (:require [clojure.data.xml.protocols :refer [AsQName qname-uri qname-local]]
            [clojure.string :as str]))

(defrecord QName [uri local prefix]
  AsQName
  (qname-local [_] local)
  (qname-uri [_] uri))

(specify! (.-prototype QName)
  IEquiv
  (-equiv [this other]
    (and (instance? QName other)
         (= (.-local this) (.-local other))
         (= (.-uri this) (.-uri other)))))

(defn make-qname [uri name prefix]
  (->QName uri name prefix))

(def parse-qname
  (memoize
   (fn [s]
     (let [[_ ns-uri local] (re-matches #"(?:\{([^}]+)\})?([^{]*)" s)]
       (make-qname (or ns-uri "") local "")))))
