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
  (:use [clojure.test :only [deftest is are]]
        [clojure.data.xml :as xml :only [sexp-as-element
                                         sexps-as-fragment element]]))

(deftest as-element
  (let [xml-input "<tag attr=\"value\"><body /></tag>"
        sexp-input [:tag {:attr "value"} :body]]
    (is (= (with-in-str xml-input (xml/parse *in*))
           (sexp-as-element sexp-input)))))

(deftest as-fragment
  (let [input (list [:tag1 "stuff"]
                    [:tag2 "other"])]
    (is (= (sexps-as-fragment input)
           (map sexp-as-element input)))
    (is (thrown? Exception (sexp-as-element input)))))
