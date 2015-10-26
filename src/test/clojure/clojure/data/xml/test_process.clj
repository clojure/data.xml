(ns clojure.data.xml.test-process
  (:require [clojure.data.xml :refer :all]
            [clojure.test :refer :all]))

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
  (is (= (element-nss (aggregate-xmlns test-data)) {:xmlns/a "GEE:" :xmlns/b "GOO:"})))
