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
 #?@(:clj  [jvm/parse-qname jvm/encode-uri jvm/decode-uri]
     :cljs [jsn/parse-qname jsn/encode-uri jsn/decode-uri]))

;; protocol functions can be redefined by extend-*, so we wrap
;; protocols/qname-uri protocols/qname-local within regular fns

(defn uri-symbol [uri]
  (symbol (encode-uri (str "xmlns." uri))))

(defn symbol-uri [ss]
  (let [du (decode-uri (str ss))]
    (if (.startsWith du "xmlns.")
      (subs du 6)
      (throw (ex-info "Uri symbol not valid" {:sym ss})))))

(defn qname-uri
  "Get the namespace uri for this qname"
  [v]
  (protocols/qname-uri v))

(defn qname-local
  "Get the name for this qname"
  [v]
  (protocols/qname-local v))

(defn qname
  ([local] (qname "" local))
  ([uri local] (keyword (when-not (str/blank? uri)
                          (encode-uri (str "xmlns." uri)))
                        local))
  ([uri local prefix] (qname uri local)))

;; The empty string shall be equal to nil for xml names
(defn namespaced? [qn]
  (not (str/blank? (qname-uri qn))))

(defn- clj-ns-name [ns]
  (cond (instance? Namespace ns) (ns-name ns)
        (keyword? ns) (name ns)
        :else (str ns)))

;; xmlns attributes get special treatment. they go into metadata, don't contribute to equality
(def xmlns-uri "http://www.w3.org/2000/xmlns/")
;; TODO find out if xml prefixed names need any special treatment too
(def xml-uri "http://www.w3.org/XML/1998/namespace")

(extend-protocol AsQName
  Keyword
  (qname-local [kw] (name kw))
  (qname-uri [kw]
    (if-let [ns (namespace kw)]
      (if (.startsWith ns "xmlns.")
        (decode-uri (subs ns 6))
        (if (= "xmlns" ns)
          xmlns-uri
          (throw (ex-info "Keyword ns is not an xmlns. Needs to be in the form :xmlns.<encoded-uri>/<local>"
                          {:kw kw}))))
      "")))

(defn as-qname [n]
  (qname (qname-uri n) (qname-local n)))

(defn uri-file
  "Dummy file name for :require'ing xmlns uri"
  [uri]
  (str (str/replace (name (uri-symbol uri))
                    "." "/")
       ".cljc"))

(defn print-uri-file-command!
  "Shell command to create a dummy file for xmlns. Execute from a source root."
  [uri]
  (println  "echo \"(ns" (str (uri-symbol uri) ")\" >") (uri-file uri)))

#?(:clj
   (defn alias-uri
     "Define a Clojure namespace aliases for xmlns uris.

  This sets up the current namespace for reading qnames denoted with
  Clojure's ::alias/keywords reader feature.
  

  ## Example
  (alias-uri :D \"DAV:\")
                           ; similar in effect to
  ;; (require '[xmlns.DAV%3A :as D])
                           ; but required namespace is auto-created
                           ; henceforth, shorthand keywords can be used
  {:tag ::D/propfind}
                           ; ::D/propfind will be expanded to :xmlns.DAV%3A/propfind
                           ; in the current namespace by the reader

  ## Clojurescript support
  Currently, namespaces can't be auto-created in Clojurescript.
  Dummy files for aliased uris have to exist. Have a look at `uri-file` and `print-uri-file-command!` to create those."
     {:arglists '([& {:as alias-nss}])}
     [& ans]
     (loop [[a n & rst :as ans] ans]
       (when (seq ans)
         (assert (<= (count ans)) (pr-str ans))
         (let [xn (uri-symbol n)
               al (symbol (clj-ns-name a))]
           (create-ns xn)
           (alias al xn)
           (recur rst))))))

(defn xmlns-attr?
  "Is this qname an xmlns declaration?"
  [qn]
  (let [uri (qname-uri qn)]
    (or (= xmlns-uri uri)
        (and (str/blank? uri)
             (= "xmlns" (qname-local qn))))))

(defn xmlns-attr-prefix [qn]
  (let [uri (qname-uri qn)]
    (if (str/blank? uri)
      (do (when-not (= "xmlns" (qname-local qn))
            (throw (ex-info "Not an xmlns-attr name" {:qname qn})))
          "")
      (do (when-not (= xmlns-uri uri)
            (throw (ex-info "Not an xmlns-attr name" {:qname qn})))
          (qname-local qn)))))

(defn legal-xmlns-binding! [prefix uri]
  (when (not= (= "xml" prefix)
              (= xml-uri uri))
    (throw (ex-info (str "The xmlns binding for prefix `xml` is fixed to `" xml-uri "`")
                    {:attempted-mapping {:prefix prefix :uri uri}})))
  (when (not= (= "xmlns" prefix)
              (= xmlns-uri uri))
    (throw (ex-info (str "The xmlns binding for prefix `xmlns` is fixed to `" xmlns-uri "`")
                    {:attempted-mapping {:prefix prefix :uri uri}}))))

(defn separate-xmlns
  "Call cont with two args: attributes and xmlns attributes"
  [attrs cont]
  (loop [attrs* (transient {})
         xmlns* (transient {})
         [qn :as attrs'] (keys attrs)]
    (if (seq attrs')
      (let [val (get attrs qn)]
        (if (xmlns-attr? qn)
          (let [prefix (xmlns-attr-prefix qn)]
            (legal-xmlns-binding! prefix val)
            (recur attrs*
                   (assoc! xmlns* prefix val)
                   (next attrs')))
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

