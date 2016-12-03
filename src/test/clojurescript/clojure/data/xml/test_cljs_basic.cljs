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
    (xml/element :xmlns.DAV%3A/foo) "<foo xmlns=\"DAV:\"/>"))
