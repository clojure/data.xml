;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.node
  "Data types for xml nodes: Element, CData and Comment"
  {:author "Herwig Hochleitner"}
  (:require [clojure.data.xml.name :refer [separate-xmlns as-qname]]
            [clojure.data.xml.protocols :as p]
            [clojure.data.xml.pu-map :as pu]
            [clojure.data.xml.core :as core])
  #?(:clj (:import (clojure.lang IHashEq IObj ILookup IKeywordLookup Counted
                                 Associative Seqable IPersistentMap
                                 APersistentMap RT MapEquivalence MapEntry)
                   (java.io Serializable Writer)
                   (java.util Map Iterator))))

;; Parsed data format
;; Represents a node of an XML tree

;; We implement a custom deftype for elements
;; it is similar to (defrecord Element [tag attrs content])
;; but we override its hash and equality to be compatible with
;; clojure's hash-maps
;; see http://dev.clojure.org/jira/browse/CLJ-2084
;; also, elements don't have an extmap and degrade to hash-maps also
;; when assoc'ing unknown keys

;; FIXME hash caching cannot be used: http://dev.clojure.org/jira/browse/CLJ-2092

#?
(:clj
 (deftype ElementIterator [el ^:volatile-mutable fields]
   Iterator
   (hasNext [_] (boolean (seq fields)))
   (next [_]
     (let [f (first fields)]
       (set! fields (next fields))
       (MapEntry. f (get el f))))))

'{nil {ICloneable {nil -clone}}
  Iterable {IIterable {iterator -iterator}}
  Object {Object {hashCode nil}
          IEquiv {equals -equiv}}
  IHashEq {IHash {hasheq -hash}}
  IObj {IMeta {meta -meta}
        IWithMeta {withMeta -with-meta}}
  IPersistentMap {nil {equiv nil}
                  ILookup {valAt -lookup}
                  ICounted {count -count}
                  ICollection {cons -conj}
                  IAssociative {assoc -assoc}
                  IMap {without -dissoc}
                  ISeqable {seq -seq}
                  IEmptyableCollection {empty -empty}}}

(defn element-nss* [element]
  (get (meta element) :clojure.data.xml/nss pu/EMPTY))

(defn element-nss
  "Get xmlns environment from element"
  [{:keys [attrs] :as element}]
  (separate-xmlns
   attrs #(pu/merge-prefix-map (element-nss* element) %2)))

(defn push-content [content push-handler state]
  (if (and (seq content)
           (not (reduced? state)))
    (reduce (fn [s n]
              (core/wrap-reduced (p/push-events n push-handler s)))
            state content)
    state))

(defn push-element [{:keys [tag attrs content] :as element} push-handler state]
  (if (not (reduced? state))
    (->> (separate-xmlns
          attrs #((if (seq content)
                    p/start-element-event p/empty-element-event)
                  push-handler state
                  tag %1 (pu/merge-prefix-map (element-nss* element) %2)
                  (:clojure.data.xml/location-info (meta element))))
         (push-content content push-handler))))

(deftype Element [tag attrs content meta]

  p/Event
  (push-events [this ph s] (push-element this ph s))
  
  ;; serializing/cloning, hashing, equality, iteration

  #?@
  (:clj
   [Serializable
    MapEquivalence
    IHashEq
    (hasheq [this] (APersistentMap/mapHasheq this))
    Iterable
    (iterator [this] (ElementIterator. this '(:tag :attrs :content)))]
   :cljs
   [ICloneable
    (-clone [_] (Element. tag attrs content meta))
    IHash
    (-hash [this] (hash-unordered-coll this))
    IEquiv
    (-equiv [this other] (or (identical? this other)
                             ^boolean (js/cljs.core.equiv_map this other)))
    IIterable
    (-iterator [this] (RecordIter. 0 this 3 [:tag :attrs :content] (nil-iter)))])
  Object
  (toString [_]
    (let [qname (as-qname tag)]
      (apply str (concat ["<" qname]
                         (mapcat (fn [[n a]]
                                   [" " (as-qname n) "=" (pr-str a)])
                                 attrs)
                         (if (seq content)
                           (concat [">"] content ["</" qname ">"])
                           ["/>"])))))
  #?@(:clj
      [(hashCode [this] (APersistentMap/mapHash this))
       (equals [this other] (APersistentMap/mapEquals this other))
       IPersistentMap
       (equiv [this other] (APersistentMap/mapEquals this other))])

  ;; Main collection interfaces, that are included in IPersistentMap,
  ;; but are separate protocols in cljs

  #?(:cljs ILookup)
  (#?(:clj valAt :cljs -lookup) [this k]
    (#?(:clj .valAt :cljs -lookup)
     this k nil))
  (#?(:clj valAt :cljs -lookup) [this k nf]
    (case k
      :tag tag
      :attrs attrs
      :content content
      nf))
  #?(:cljs ICounted)
  (#?(:clj count :cljs -count) [this] 3)
  #?(:cljs ICollection)
  (#?(:clj cons :cljs -conj) [this entry]
    (conj (with-meta {:tag tag :attrs attrs :content content} meta)
          entry))
  #?(:cljs IAssociative)
  (#?(:clj assoc :cljs -assoc) [this k v]
    (case k
      :tag (Element. v attrs content meta)
      :attrs (Element. tag v content meta)
      :content (Element. tag attrs v meta)
      (with-meta {:tag tag :attrs attrs :content content k v} meta)))
  #?(:cljs IMap)
  (#?(:clj without :cljs -dissoc) [this k]
    (with-meta
      (case k
        :tag {:attrs attrs :content content}
        :attrs {:tag tag :content content}
        :content {:tag tag :attrs attrs}
        this)
      meta))
  #?(:clj (empty [_] (Element. tag {} [] {})))
  #?@(:cljs
      [ISeqable
       (-seq [this]
             (seq [[:tag tag] [:attrs attrs] [:content content]]))]
      :clj
      [clojure.lang.Seqable
       (seq [this] (iterator-seq (.iterator this)))])


  #?@(:cljs
      [IEmptyableCollection
       (-empty [_] (Element. tag {} [] {}))])

  ;; j.u.Map and included interfaces
  #?@(:clj
      [Map
       (entrySet [this] (set this))
       (values [this] (vals this))
       (keySet [this] (set (keys this)))
       (get [this k] (.valAt this k))
       (containsKey [this k] (case k (:tag :attrs :content) true false))
       (containsValue [this v] (boolean (some #{v} (vals this))))
       (isEmpty [this] false)
       (size [this] 3)])

  ;; Metadata interface

  #?(:clj IObj :cljs IMeta)
  (#?(:clj meta :cljs -meta) [this] meta)
  #?(:cljs IWithMeta)
  (#?(:clj withMeta :cljs -with-meta) [this next-meta]
    (Element. tag attrs content next-meta))

  ;; cljs printing is protocol-based

  #?@
  (:cljs
   [IPrintWithWriter
    (-pr-writer [this writer opts]
                (-write writer "#xml/element{:tag ")
                (pr-writer tag writer opts)
                (when-not (empty? attrs)
                  (-write writer ", :attrs ")
                  (pr-writer attrs writer opts))
                (when-not (empty? content)
                  (-write writer ", :content ")
                  (pr-sequential-writer writer pr-writer "[" " " "]" opts content))
                (-write writer "}"))]))

;; clj printing is a multimethod

#?
(:clj
 (defmethod print-method Element [{:keys [tag attrs content]} ^Writer writer]
   (.write writer "#xml/element{:tag ")
   (print-method tag writer)
   (when-not (empty? attrs)
     (.write writer ", :attrs ")
     (print-method attrs writer))
   (when-not (empty? content)
     (.write writer ", :content [")
     (print-method (first content) writer)
     (doseq [c (next content)]
       (.write writer " ")
       (print-method c writer))
     (.write writer "]"))
   (.write writer "}")))

(defrecord CData [content])
(defrecord Comment [content])

(defn element*
  "Create an xml element from a content collection and optional metadata"
  ([tag attrs content meta]
   (Element. tag (or attrs {})
             (into [] (remove nil?)
                   content)
             meta))
  ([tag attrs content]
   (Element. tag (or attrs {})
             (into [] (remove nil?)
                   content)
             nil)))

#?(:clj
   ;; Compiler macro for inlining the two constructors
   (alter-meta! #'element* assoc :inline
                (fn
                  ([tag attrs content meta]
                   `(Element. ~tag (or ~attrs {}) (into [] (remove nil?) ~content) ~meta))
                  ([tag attrs content]
                   `(Element. ~tag (or ~attrs {}) (into [] (remove nil?) ~content) nil)))))

(defn element
  "Create an xml Element from content varargs"
  ([tag] (element* tag nil nil))
  ([tag attrs] (element* tag attrs nil))
  ([tag attrs & content] (element* tag attrs content)))

(defn cdata
  "Create a CData node"
  [content]
  (CData. content))

(defn xml-comment
  "Create a Comment node"
  [content]
  (Comment. content))

(defn map->Element [{:keys [tag attrs content] :as el}]
  (element* tag attrs content (meta el)))

(defn tagged-element [el]
  (cond (map? el)
        #?(:clj (if (when-let [v (resolve 'cljs.env/*compiler*)]
                      (and (bound? v)
                           @v))
                  `(element* ~(:tag el) ~(:attrs el) ~(:content el) ~(meta el))
                  (map->Element el))
           :cljs (map->Element el))
        ;; TODO support hiccup syntax
        :else (throw (ex-info "Unsupported element representation"
                              {:element el}))))

(defn element? [el]
  (and (map? el) (some? (:tag el))))
