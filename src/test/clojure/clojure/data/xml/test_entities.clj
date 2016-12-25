;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Test that external entities are not resolved by default, see https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Processing"
      :author "Carlo Sciolla"}
  clojure.data.xml.test-entities
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clojure.data.xml :refer :all]))

(defn vulnerable-input
  "Creates an XML with an external entity referring to the given URL"
  [file-url]
  (str "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
       "<!DOCTYPE foo ["
       "  <!ELEMENT foo ANY >"
       "  <!ENTITY xxe SYSTEM \"" file-url  "\" >]>"
       "<foo>&xxe;</foo>"))

(defn secret-file
  "Returns the URL to the secret file containing the server root password"
  []
  (io/resource "secret.txt"))

(defn parse-vulnerable-file
  "Parses the vulnerable file, optionally passing the given options to the parser"
  ([]        (parse-str (vulnerable-input (secret-file))))
  ([& options] (apply parse-str (vulnerable-input (secret-file)) options)))

(deftest prevent-xxe-by-default
  (testing "To prevent XXE attacks, exernal entities by default resolve to nil"
    (let [parsed (parse-vulnerable-file)
          expected {:tag :foo
                    :attrs {}
                    :content ()}]
      (is (= expected parsed)))))

(deftest allow-external-entities-if-required
  (testing "If explicitly enabled, external entities are property resolved"
    (let [parsed (parse-vulnerable-file :supporting-external-entities true)
          expected {:tag :foo
                    :attrs {}
                    :content ["root_password\n"]}]
      (is (= expected parsed)))))
