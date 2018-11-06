(ns clojure.data.xml.test-cljs-basic
  (:require
   [cljs.test :as test :refer [deftest is are]]
   [clojure.data.xml :as xml :refer [parse-str emit-str element element-data element-node]]
   [clojure.data.xml.node :as node]
   [clojure.data.xml.js.dom :as dom]))

(comment

  (= (xml/element :foo)
     (xml/parse-str "<foo/>"))

  (xml/element-data (xml/element-node (xml/element :foo)))

  )

(deftest roundtrips
  (are [dxml xml] (do (is (= dxml (xml/parse-str xml)))
                      (is (= dxml (xml/parse-str (xml/emit-str dxml)))))
    (xml/element :foo) "<foo/>"
    (xml/element :xmlns.DAV%3A/foo) "<foo xmlns=\"DAV:\"/>"
    (xml/element :foo {} (xml/cdata "<foo")) "<foo><![CDATA[<foo]]></foo>"
    (xml/element :foo {} (xml/xml-comment " bar> ")) "<foo><!-- bar> --></foo>"))

(deftest printing
  (are [node ps] (is (= ps (pr-str node)))
    (xml/element :foo) "#xml/element{:tag :foo}"
    (xml/element :foo {:a "2"}) "#xml/element{:tag :foo, :attrs {:a \"2\"}}"
    (xml/element :foo {} (xml/element :bar)) "#xml/element{:tag :foo, :content [#xml/element{:tag :bar}]}"
    (xml/element :foo {} "bar") "#xml/element{:tag :foo, :content [\"bar\"]}"))
