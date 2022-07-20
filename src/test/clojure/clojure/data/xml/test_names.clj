(ns clojure.data.xml.test-names
  (:require [clojure.data.xml :refer :all]
            [clojure.test :refer :all]))

(alias-uri
 :U "uri-u:"
 :D "DAV:"
 'V "uri-v:"
 "W" "uri-w:")

(deftest test-types
  (are [vals values] (every? true? (for [v values]
                                     (is (= vals [(qname-uri v) (qname-local v)])
                                         (str "Interpreted QName: " (pr-str v)))))
    ["" "name"]           ["name" :name (parse-qname "name")]
    ["uri-u:" "name"]     [::U/name "{uri-u:}name" (parse-qname "{uri-u:}name") (as-qname "{uri-u:}name")]
    ["uri-v:" "vname"]    [::V/vname "{uri-v:}vname" (parse-qname "{uri-v:}vname")]
    ["uri-w:" "wname"]    [::W/wname "{uri-w:}wname" (parse-qname "{uri-w:}wname")]
    ["http://www.w3.org/XML/1998/namespace" "name"] [:xml/name]
    ["http://www.w3.org/2000/xmlns/" "name"]        [:xmlns/name]))


(deftest test-emit-raw
  (are [node result] (= (emit-str node) result)
    {:tag ::D/limit :attrs {:xmlns/D "DAV:"}
     :content [{:tag ::D/nresults :content ["100"]}]}
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><D:limit xmlns:D=\"DAV:\"><D:nresults>100</D:nresults></D:limit>"))

(deftest test-parse-raw
  (testing "includes namespace in tags when namespace-aware is true"
    (is (= (element ::D/limit {}
                    (element ::D/nresults nil "100"))
           (parse-str "<D:limit xmlns:D=\"DAV:\"><D:nresults>100</D:nresults></D:limit>"))))
  (testing "leaves namespace off tags when namespace-aware is false"
    (is (= (element :limit {:xmlns.http%3A%2F%2Fwww.w3.org%2F2000%2Fxmlns%2F/D "DAV:"}
                    (element :nresults nil "100"))
           (parse-str "<limit xmlns:D=\"DAV:\"><nresults>100</nresults></limit>"
                      :namespace-aware false)))))

(deftest qnames
  (is (= (qname "foo") (as-qname :foo))))

(deftest test-gen-prefix
  (are [node] (= (parse-str (emit-str node)) node)
    (element ::D/limit {::V/moo "gee"}
             (element ::D/nresults nil "100"))))

(deftest test-reassign-prefix
  (are [node reparsed] (= (parse-str (emit-str node)) reparsed)
    (element ::D/limit {:xmlns/D "DAV:"}
             ;; because of outer binding, "uri-v:" will be bound to
             ;; generated xmlns:a instead of xmlns:D
             (element ::V/other {:xmlns/D "uri-v:"}))
    (element ::D/limit {} (element ::V/other))))

(deftest test-preserve-empty-ns
  (are [el] (= el (parse-str (emit-str (assoc-in el [:attrs :xmlns] "DAV:"))))
    (element :top-level)
    (element ::D/local-root {}
             (element :top-level))))
