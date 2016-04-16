;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.name
  #?@(:clj [(:require [clojure.string :as str]
                      [clojure.data.xml.jvm.name :as jvm]
                      (clojure.data.xml
                       [impl :refer [export-api]]
                       [protocols :as protocols :refer [AsQName]]))
            (:import (clojure.lang Namespace Keyword))]
      :cljs [(:require-macros
              [clojure.data.xml.impl :refer [export-api]])
             (:require [clojure.string :as str]
                       [clojure.data.xml.js.name :as jsn]
                       [clojure.data.xml.protocols :as protocols :refer [AsQName]])
             (:import (goog.string StringBuffer))]))

(export-api
 #?@(:clj  [jvm/parse-qname jvm/make-qname]
     :cljs [jsn/parse-qname jsn/make-qname])
 
 ;; protocol functions can be redefined by extend-*
 protocols/qname-uri protocols/qname-local)

(defn qname
  ([name] (make-qname "" name ""))
  ([uri name] (make-qname (or uri "") name ""))
  ([uri name prefix] (make-qname (or uri "") name (or prefix ""))))

;; The empty string shall be equal to nil for xml names
(defn namespaced? [qn]
  (not (str/blank? (qname-uri qn))))

(defn- clj-ns-name [ns]
  (cond (instance? Namespace ns) (ns-name ns)
        (keyword? ns) (name ns)
        :else (str ns)))

;; # Handling of xmlns - cljns bindings

(def ^:private nss (atom {:ns->xs {} :xs->ns {}}))

(defn ns-uri
  "Look up xmlns uri to keyword namespace"
  [ns]
  (-> @nss :ns->xs (get ns)))

(defn uri-ns
  "Look up keyword namespace to xmlns uri"
  [uri]
  (-> @nss :xs->ns (get uri)))

(extend-protocol AsQName
  Keyword
  (qname-local [kw] (name kw))
  (qname-uri [kw]
    (if-let [ns (namespace kw)]
      (or (ns-uri ns)
          (throw (ex-info (str "Unknown xmlns for clj ns: " (pr-str ns))
                          {:qname kw})))
      ""))
  #?(:clj String :cljs string)
  (qname-local [s] (qname-local (parse-qname s)))
  (qname-uri   [s] (qname-uri (parse-qname s))))

(defn canonical-name [uri local prefix]
  (if (str/blank? uri)
    (keyword local)
    (let [kw-prefix (uri-ns uri)]
      (if (str/blank? kw-prefix)
        (make-qname uri local prefix)
        (keyword kw-prefix local)))))

(defn to-qname [n]
  (make-qname (or (qname-uri n) "") (qname-local n) ""))

(defn- declare-ns* [{:keys [ns->xs xs->ns] :as acc} [ns xmlns & rst :as nss]]
  (if (seq nss)
    (do (assert (>= (count nss) 2))
        (let [n (clj-ns-name ns)]
          (if-let [x' (ns->xs n)]
            (if (= xmlns x')
              (recur acc rst)
              (throw (ex-info (str "Redefining " n) {:old x' :new xmlns})))
            (if-let [n' (xs->ns xmlns)]
              (throw (ex-info (str xmlns " already bound to " n')
                              {:old n' :new n}))
              (recur {:ns->xs (assoc ns->xs n xmlns)
                      :xs->ns (assoc xs->ns xmlns n)}
                     rst)))))
    acc))

(defn declare-ns
  "Define mappings in the global keyword-ns -> qname-uri mapping table.
   Arguments are pairs of ns-name - qname-uri
   ns-name must be a string, symbol, keyword or clojure namespace. The canonical form is string.
   ns-uri must be a string"
  {:arglists '([& {:as cljns-xmlnss}])}
  [& ns-xmlnss]
  (swap! nss declare-ns* ns-xmlnss))

(declare-ns
 :xml     "http://www.w3.org/XML/1998/namespace"
 :xmlns   "http://www.w3.org/2000/xmlns/")

(def ;;once ^:const
  empty-namespace
  {"xml"   (ns-uri "xml")
   "xmlns" (ns-uri "xmlns")})

(def ;;once ^:const
  xmlns-uri (ns-uri "xmlns"))

#?(:clj
   (defn alias-ns
     "Define a clojure namespace alias for shortened keyword and symbol namespaces.
   Similar to clojure.core/alias, but if namespace doesn't exist, it is created.

   ## Example
   (declare-ns :xml.dav \"DAV:\")
   (alias-ns :D :xml.dav)
  {:tag ::D/propfind :content []}"
     {:arglists '([& {:as alias-nss}])}
     [& ans]
     (loop [[a n & rst :as ans] ans]
       (when (seq ans)
         (assert (<= 2 (count ans)) (pr-str ans))
         (let [ns (symbol (clj-ns-name n))
               al (symbol (clj-ns-name a))]
           (create-ns ns)
           (alias al ns)
           (recur rst))))))

(defn merge-nss
  "Merge two attribute sets, deleting assignments of empty-string"
  [nss1 nss2]
  (persistent!
   (reduce-kv (fn [a k v]
                (if (str/blank? v)
                  (dissoc! a k)
                  (assoc! a k v)))
              (transient nss1)
              nss2)))

(defn xmlns-attr?
  "Is this qname an xmlns declaration?"
  [qn]
  (let [uri (qname-uri qn)
        local (qname-local qn)]
    (or (= xmlns-uri uri)
        (and (str/blank? uri)
             (= "xmlns" local)))))

(defn separate-xmlns
  "Call cont with two args: attributes and xmlns attributes"
  [attrs cont]
  (loop [attrs* (transient {})
         xmlns* (transient {})
         [qn :as attrs'] (keys attrs)]
    (if (seq attrs')
      (let [val (get attrs qn)]
        (if (xmlns-attr? qn)
          (recur attrs*
                 (assoc! xmlns* qn val)
                 (next attrs'))
          (recur (assoc! attrs* qn val)
                 xmlns*
                 (next attrs'))))
      (cont (persistent! attrs*) (persistent! xmlns*)))))

;(set! *warn-on-reflection* true)

#?(:clj (def ^:private ^"[C" prefix-alphabet
          (char-array
           (map char
                (range (int \a) (inc (int \z))))))
   :cljs (def ^:private prefix-alphabet
           (apply str (map js/String.fromCharCode
                           (range (.charCodeAt "a" 0)
                                  (inc (.charCodeAt "z" 0)))))))

(def ^{:dynamic true
       :doc "Thread local counter for a single document"}
  *gen-prefix-counter*)

(defn gen-prefix
  "Generates an xml prefix.
   Zero-arity can only be called, when *gen-prefix-counter* is bound and will increment it."
  ([] (let [c *gen-prefix-counter*]
        #?(:cljs (when (undefined? c)
                   (throw (ex-info "Not bound: *gen-prefix-counter*" {:v #'*gen-prefix-counter*}))))
        (set! *gen-prefix-counter* (inc c))
        (gen-prefix c)))
  ([n]
   (let [cnt (alength prefix-alphabet)
         sb #?(:clj (StringBuilder.) :cljs (StringBuffer.))]
     (loop [n* n]
       (let [ch (mod n* cnt)
             n** (quot n* cnt)]
         (.append sb (aget prefix-alphabet ch))
         (if (pos? n**)
           (recur n**)
           (str sb)))))))

