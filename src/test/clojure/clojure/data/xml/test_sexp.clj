;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for reading [:tag {:attr 'value} body*] as XML."
      :author "Alan Malloy"}
  clojure.data.xml.test-sexp
  (:require
   [clojure.test :refer :all]
   [clojure.data.xml :refer :all]
   [clojure.data.xml.test-utils :refer (test-stream lazy-parse*)]))

(deftest as-element
  (let [xml-input "<tag attr=\"value\"><body /></tag>"
        sexp-input [:tag {:attr "value"} :body]]
    (is (= (lazy-parse* xml-input)
           (sexp-as-element sexp-input)))))

(deftest as-fragment
  (let [input (list [:tag1 "stuff"]
                    [:tag2 "other"])]
    (is (= (sexps-as-fragment input)
           (map sexp-as-element input)))
    (is (thrown? Exception (sexp-as-element input)))))

(deftest with-cdata
  (let [xml-input (element :tag {:attr "value"}
                           (element :body {} (cdata "not parsed <stuff")))
        sexp-input [:tag {:attr "value"} [:body {} [:-cdata "not parsed <stuff"]]]]
    (is (= xml-input
           (sexp-as-element sexp-input)))))

(deftest with-multiple-cdata
  (testing "separate cdata"
    (let [xml-input (element :tag {:attr "value"}
                             (element :body {}
                                      (cdata "not parsed <stuff")
                                      (cdata "more not parsed <stuff")))
          sexp-input [:tag {:attr "value"} [:body {}
                                            (list [:-cdata "not parsed <stuff"]
                                                  [:-cdata "more not parsed <stuff"])]]]
      (is (= xml-input
             (sexp-as-element sexp-input)))))
  (testing "cdata with embedded ]]>"
    (let [xml-input (element :tag {:attr "value"}
                             (element :body {}
                                      (cdata "not parsed <stuff]]")
                                      (cdata ">more not parsed <stuff")))
          sexp-input [:tag {:attr "value"}
                      [:body {}
                       [:-cdata "not parsed <stuff]]>more not parsed <stuff"]]]]
      (is (= (emit-str xml-input)
             (emit-str (sexp-as-element sexp-input)))))))

(deftest with-comment
  (let [xml-input (element :tag {:attr "value"}
                           (element :body {} (xml-comment "comment <stuff<here<")))
        sexp-input [:tag {:attr "value"} [:body {} [:-comment "comment <stuff<here<"]]]]
    (is (= xml-input
           (sexp-as-element sexp-input)))))
