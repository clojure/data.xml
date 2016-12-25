(ns clojure.data.xml.test-equiv
  (:require [clojure.data.xml :refer [element qname]]
            [clojure.test :refer [deftest is are testing]]))

(deftest test-node-equivalence
  (are [repr1 repr2] (and (is (= repr1 repr2))
                          (is (= (hash repr1) (hash repr2))))
    (element :foo) {:tag :foo :attrs {} :content []}
    (element (qname "DAV:" "foo")) {:tag (qname "DAV:" "foo") :attrs {} :content []}
    (element :foo {:a "b"}) {:tag :foo :attrs {:a "b"} :content []}
    (element :foo {:a "b"} "a" "b") {:tag :foo :attrs {:a "b"} :content ["a" "b"]}
    #?@(:clj ;; wait for https://github.com/clojure/clojurescript/commit/9484a134bdf039c10ec3c26c8aaa3acd0dcd9875
        [#xml/element {:tag :foo :content ["C"]} {:tag :foo :attrs {} :content ["C"]}])))
