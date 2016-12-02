(ns clojure.data.xml.test-cljs-extended
  (:require
   [cljs.test :as test :refer [deftest is are]]
   [clojure.data.xml :as xml :refer [parse-str emit-str element element-data]]
   [clojure.data.xml.node :as node]))

(deftest roundtrips
  (are [dxml xml] (do (is (= dxml (xml/parse-str xml :raw true)))
                      (is (= dxml (xml/parse-str (xml/emit-str dxml) :raw true))))
    (xml/element :foo) "<foo/>"))
