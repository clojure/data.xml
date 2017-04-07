(ns clojure.data.xml.test-process
  (:require [clojure.data.xml :refer :all]
            [clojure.test :refer :all]
            [clojure.walk :as w]
            [clojure.string :as str]))

(def test-data
  (element
   :foo nil
   (with-meta (element :bar {:xmlns "MOO:"} "some" "content")
     {:clojure.data.xml/nss {:xmlns/p "PAR:"}})
   "more content"
   (element (qname "GOO:" "ho") {(qname "GEE:" "hi") "ma"} "ii")
   "end"))

(deftest process
  (is (= (find-xmlns test-data) #{"" "GEE:" "GOO:"}))
  (is (= (set (vals (element-nss (aggregate-xmlns test-data)))) #{"GEE:" "GOO:"})))

(deftest walk-test
  (is (= {:tag :FOO, :attrs {}, :content ()}
         (w/postwalk (fn [e]
                       (println e)
                       (if (element? e)
                         (update e :tag (comp keyword str/upper-case name))
                         e))
                     (element :foo)))))
