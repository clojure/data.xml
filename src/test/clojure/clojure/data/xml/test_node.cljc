(ns clojure.data.xml.test-node
  (:require [clojure.test :as t :refer [deftest testing is are]
             #?@(:cljs [:include-macros true])]
            [clojure.data.xml.node :as node :refer [element element*]]))

(deftest interfaces
  (let [el (element :foo)]
    (are [expr result] (= expr result)
      ;; TODO
      (seq el) [[:tag :foo] [:attrs {}] [:content []]])))

