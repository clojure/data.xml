;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.impl
  "Shared private code for data.xml namespaces"
  {:author "Herwig Hochleitner"}
  (:require [clojure.data.codec.base64 :as b64]))

(defn- var-form? [form]
  (and (seq? form) (= 'var (first form))))

(defn- export-form [var-name]
  (let [is-var (var-form? var-name)
        vsym (symbol (name (if is-var (second var-name) var-name)))]
    `[(def ~vsym ~var-name)
      (alter-meta! (var ~vsym)
                   (constantly (assoc (meta ~(if is-var
                                               var-name
                                               `(var ~var-name)))
                                      :wrapped-by (var ~vsym))))]))

(defmacro export-api
  "This creates vars, that take their (local) name, value and metadata from another var"
  [& names]
  (cons 'do (mapcat export-form names)))

(defmacro static-case
  "Variant of case where keys are evaluated at compile-time"
  [val & cases]
  `(case ~val
     ~@(mapcat (fn [[field thunk]]
                 [(eval field) thunk])
               (partition 2 cases))
     ~@(when (odd? (count cases))
         [(last cases)])))

(defmacro extend-protocol-fns
  "Helper to many types to a protocol with a method map, similar to extend"
  [proto & types+mmaps]
  (assert (zero? (mod (count types+mmaps) 2)))
  (let [gen-extend (fn [type mmap] (list `extend type proto mmap))]
    `(do ~@(for [[type mmap] (partition 2 types+mmaps)]
             (if (coll? type)
               (let [mm (gensym "mm-")]
                 `(let [~mm ~mmap]
                    ~@(map gen-extend type (repeat mm))))
               (gen-extend type mmap))))))

(defmacro compile-if
  "Evaluate `exp` and if it returns logical true and doesn't error, expand to
  `then`.  Else expand to `else`.

  see clojure.core.reducers"
  [exp then else]
  (if (try (eval exp)
           (catch Throwable _ false))
    `(do ~then)
    `(do ~else)))

(defn b64-encode [ba]
  (String. ^bytes (b64/encode ba)))
