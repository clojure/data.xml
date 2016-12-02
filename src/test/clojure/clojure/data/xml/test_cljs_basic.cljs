(ns clojure.data.xml.test-cljs-basic
  (:require
   [cljs.test :as test :refer [deftest is are]]
   [clojure.data.xml :as xml :refer [parse-str emit-str element element-data]]
   [clojure.data.xml.node :as node]))


(comment

  (-> "<foo/>"
      parse-str
      emit-str)

  (-> :foo xml/element
      emit-str parse-str)

  

  )


(deftest roundtrips
  (are [dxml xml] (do (is (= (element-data dxml) (xml/parse-str xml)))
                      (is (= (element-data dxml) (xml/parse-str (xml/emit-str dxml)))))
    (xml/element :foo) "<foo/>"))
