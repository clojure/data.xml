(ns clojure.data.xml.test-names
  (:require [clojure.data.xml.name :refer :all]
            [clojure.test :refer :all]))

#_(defns xmlns.uri "uri:"
  "P" "uri2:")

#_(deftest test-types
    (are [vals values] (every? #{vals}
                               (map (juxt qname-uri qname-local)
                                    values))
      ["" "name"]           ["name" :name]
      ["uri:" "name"]       ["{uri:}name" :xmlns.uri/name]
      ["uri2:" "name2"]     ["{uri2:}name2" :xmlns.uri/P:name2]
      [xml-ns-uri "name"]   [:xml/name]
      [xmlns-ns-uri "name"] [:xmlns/name]))



