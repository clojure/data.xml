(ns clojure.data.xml.js.name
  (:require [clojure.data.xml.protocols :refer [AsQName qname-uri qname-local]]
            [clojure.string :as str]))

(def parse-qname
  (memoize (partial re-matches #"(?:\{([^}]+)\})?([^{]*)")))

(defn decode-uri [ns]
  (js/decodeURIComponent ns))

(defn encode-uri [uri]
  (js/encodeURIComponent uri))

(extend-protocol AsQName
  string
  (qname-local [s]
    (let [[_ _ local] (parse-qname s)]
      local))
  (qname-uri [s]
    (let [[_ uri _] (parse-qname s)]
      uri)))
