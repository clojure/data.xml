(ns clojure.data.xml.test-pu
  (:require [clojure.data.xml.pu-map :as pu]
            [clojure.data.xml.name :as name]
            [clojure.test :refer [deftest is are testing]]))

(deftest builtin-mappings
  (is (= name/xml-uri (pu/get pu/EMPTY "xml")))
  (is (= name/xmlns-uri (pu/get pu/EMPTY "xmlns")))
  (is (= ["xml"] (pu/get-prefixes pu/EMPTY name/xml-uri)))
  (is (= ["xmlns"] (pu/get-prefixes pu/EMPTY name/xmlns-uri)))
  (are [p u] (thrown? #?(:clj Exception :cljs js/Error) (pu/assoc pu/EMPTY p u))
    "xml" "_"
    "xmlns" "_"
    "_" name/xml-uri
    "_" name/xmlns-uri))

(deftest basic-operation
  (are [associated-groups expected-uris expected-prefixes]
      (let [pu (reduce (fn [pu* group] (apply pu/assoc pu* group))
                       pu/EMPTY associated-groups)]
        (every? true?
                (apply concat
                       (for [[prefix uri] (partition 2 expected-uris)]
                         (is (= uri (pu/get pu prefix))))
                       (for [[uri prefixes] (partition 2 expected-prefixes)]
                         [(is (= prefixes (pu/get-prefixes pu uri)))
                          (is (= (first prefixes) (pu/get-prefix pu uri)))]))))
      []
      ["wrong-prefix" nil
       "xml" name/xml-uri
       "xmlns" name/xmlns-uri]
      ["wrong-uri" nil
       name/xml-uri ["xml"]
       name/xmlns-uri ["xmlns"]]

      [[nil "FIN:"]]
      ["wrong-prefix" nil
       "xml" name/xml-uri
       "xmlns" name/xmlns-uri
       "" "FIN:"
       nil "FIN:"]
      ["wrong-uri" nil
       "FIN:" [""]
       name/xml-uri ["xml"]
       name/xmlns-uri ["xmlns"]]

      [["p" "U:"
        "q" "V:"]]
      ["wrong-prefix" nil
       "xml" name/xml-uri
       "xmlns" name/xmlns-uri
       "p" "U:"
       "q" "V:"]
      ["wrong-uri" nil
       name/xml-uri ["xml"]
       name/xmlns-uri ["xmlns"]
       "U:" ["p"]
       "V:" ["q"]]

      [["p" "U:"
        "q" "V:"]
       ["r" "U:"
        "s" "V:"]
       ["t" "U:"]
       ["p" ""
        "q" ""]]
      ["p" nil
       "q" nil
       "r" "U:"]
      ["U:" ["r" "t"]
       "V:" ["s"]]

      [["xml" name/xml-uri
        "xmlns" name/xmlns-uri]]
      ["xml" name/xml-uri
       "xmlns" name/xmlns-uri]
      [name/xml-uri ["xml"]
       name/xmlns-uri ["xmlns"]]))

(deftest assoc-nil
  (let [pu (pu/assoc nil nil "NIL")]
    (is (= "NIL" (pu/get pu nil) (pu/get pu "")))
    (is (= "" (pu/get-prefix pu "NIL")))))

(deftest direct-access
  (is (= {"" "NIL" "a" "A"
          "xml" name/xml-uri
          "xmlns" name/xmlns-uri}
         (pu/prefix-map
          (pu/assoc nil "a" "A" nil "NIL")))))

(deftest diffing
  (is (= {"c" "d"}
         (pu/reduce-diff
          assoc {}
          (pu/assoc pu/EMPTY "a" "b")
          (pu/assoc pu/EMPTY
                    "a" "b" "c" "d")))))
