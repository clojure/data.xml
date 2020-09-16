;   Copyright (c) Rich Hickey and contributors. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

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
