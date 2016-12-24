(ns clojure.data.xml.spec
  (:require [clojure.spec :as s]
            [#?(:cljs cljs.spec.impl.gen :clj clojure.spec.gen) :as gen]
            clojure.test.check.generators
            [clojure.data.xml :as xml]
            [clojure.data.xml.name :as name]
            [clojure.data.xml.name :as node]
            #?@(:cljs [[clojure.data.xml.js.dom :as dom]])
            [clojure.string :as str]))

(s/def ::qname-conformer
  (s/and (s/conformer
          (fn [qn]
            (try {:uri (name/qname-uri qn)
                  :local (name/qname-local qn)}
                 (catch :default e
                   (.error js/console e "Could not conform to qname:" qn)
                   ::s/invalid)))
          (fn [{:keys [uri local] :as arg}]
            (.log js/console arg)
            (name/qname uri local)))
         #(not (str/blank? (:local %)))))

(s/def ::name/qname
  (->
   (s/or
    :global (s/or :kw (s/and simple-keyword? ::qname-conformer)
                  :str (s/and string? ::qname-conformer #(str/blank? (:uri %))))
    :qualified (s/or :kw (s/and qualified-keyword? ::qname-conformer)
                     :str (s/and string? ::qname-conformer)))
   (s/with-gen
     #(gen/fmap (fn [[uri local]]
                  (name/qname uri local))
                (gen/tuple (gen/fmap (fn [s] (when-not (str/blank? s)
                                               (str "urn:" s)))
                                     (gen/string-alphanumeric))
                           (gen/fmap (partial str "G") (gen/string-alphanumeric)))))))


(s/def ::node/Element
  (s/keys :req-un [::node/tag]
          :opt-un [::node/attrs ::node/content]))

#?(:cljs
   (do (s/def ::dom/Element (s/with-gen (partial instance? dom/Element)
                              #(gen/fmap dom/element-node (s/gen ::node/Element))))
       (s/def ::dom/Text (s/with-gen (partial instance? dom/Text)
                           #(gen/fmap dom/text-node (gen/string-ascii))))))

(s/def ::xml/Element
  #?(:clj ::node/Element
     :cljs (s/or :dom ::dom/Element
                 :rec ::node/Element)))

(s/def ::xml/Text
  (s/or :blank (s/with-gen str/blank? #(s/gen #{"" nil}))
        :str string?
        #?@(:cljs [:text ::dom/Text])))

(s/def ::xml/Node
  (s/or :text ::xml/Text
        :elem ::xml/Element))

(s/def ::node/tag ::name/qname)
(s/def ::node/attrs (s/map-of ::name/qname string?
                              :conform-keys true))
(s/def ::node/content
  (s/coll-of ::xml/Node))


(comment

  (s/conform
   (s/coll-of ::name/qname)
   [:foo :xmlns/foo])

  (require '[clojure.spec.gen :as gen])

  (s/exercise ::name/qname)

  )
