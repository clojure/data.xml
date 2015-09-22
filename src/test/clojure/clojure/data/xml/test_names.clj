(ns clojure.data.xml.test-names
  (:require [clojure.data.xml :refer :all]
            [clojure.test :refer :all]))

(declare-ns
 :test.xmlns.u "uri-u:"
 "test.xmlns.v" "uri-v:"
 'test.xmlns.w "uri-w:"
 *ns* "uri-t:")

(alias-ns
 :U :test.xmlns.u
 'V 'test.xmlns.v
 "W" "test.xmlns.w"
 :T *ns*)

(deftest test-types
  (are [vals values] (every? true? (for [v values]
                                     (is (= vals [(qname-uri v) (qname-local v)])
                                         (str "Interpreted QName: " (pr-str v)))))
    ["" "name"]           ["name" :name (parse-qname "name")]
    ["uri-u:" "name"]     [:test.xmlns.u/name  ::U/name "{uri-u:}name" (parse-qname "{uri-u:}name")]
    ["uri-v:" "vname"]    [:test.xmlns.v/vname ::V/vname "{uri-v:}vname" (parse-qname "{uri-v:}vname")]
    ["uri-w:" "wname"]    [:test.xmlns.w/wname ::W/wname "{uri-w:}wname" (parse-qname "{uri-w:}wname")]
    ["http://www.w3.org/XML/1998/namespace" "name"] [:xml/name]
    ["http://www.w3.org/2000/xmlns/" "name"]        [:xmlns/name]))



