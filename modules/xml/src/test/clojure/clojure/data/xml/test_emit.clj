;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for emit to print XML text."
      :author "Chris Houser"}
  clojure.data.xml.test-emit
  (:use [clojure.test :only [deftest is are]]
        [clojure.data.xml :as xml :only [element]]))

(def deep-tree
  (with-in-str (str "<a h=\"1\" i='2' j=\"3\">"
                    "  t1<b k=\"4\">t2</b>"
                    "  t3<c>t4</c>"
                    "  t5<d>t6</d>"
                    "  t7<e l=\"5\" m=\"6\">"
                    "    t8<f>t10</f>t11</e>"
                    "  t12<g>t13</g>t14"
                    "</a>")
    (xml/parse *in*)))

(deftest defaults
  (let [expect (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    "<a h=\"1\" i=\"2\" j=\"3\">"
                    "  t1<b k=\"4\">t2</b>"
                    "  t3<c>t4</c>"
                    "  t5<d>t6</d>"
                    "  t7<e l=\"5\" m=\"6\">"
                    "    t8<f>t10</f>t11</e>"
                    "  t12<g>t13</g>t14"
                    "</a>")]
    (is (= expect (with-out-str (xml/emit deep-tree))))))

(deftest namespaced
  (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
              "<body xmlns:api=\"http://some.uri.com/location\">"
              "<api:method args=\"\" ret=\"int\" />"
              "</body>")
         (with-out-str
           (xml/emit (element :body
                       {:xmlns/api "http://some.uri.com/location"}
                       (element :api/method
                         {:args "" :ret "int"})))))))

(deftest mixed-quotes
  (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
              "<mixed double=\"&quot;double&quot;quotes&quot;here&quot;\""
              " single=\"'single'quotes'here\"/>")
         (with-out-str
           (xml/emit (element :mixed
                       {:single "'single'quotes'here"
                        :double "\"double\"quotes\"here\""}))))))

(deftest without-decl
  (let [expect (str "<a h=\"1\" i=\"2\" j=\"3\">"
                    "  t1<b k=\"4\">t2</b>"
                    "  t3<c>t4</c>"
                    "  t5<d>t6</d>"
                    "  t7<e l=\"5\" m=\"6\">"
                    "    t8<f>t10</f>t11</e>"
                    "  t12<g>t13</g>t14"
                    "</a>")]
    (is (= expect (with-out-str (xml/emit deep-tree
                                          :xml-declaration false))))))

(deftest indent
  (let [input-tree
         (with-in-str (str "<a h=\"1\" i=\"2\" j=\"3\">"
                           "<b k=\"4\">t2</b>"
                           "<c>t4</c>t5<d>t6</d>"
                           "<e l=\"5\" m=\"6\">"
                           "          <f>t10</f>t11</e>"
                           "<g>t13</g>t14"
                           "</a>")
           (xml/parse *in*))
        expect (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    "<a h=\"1\" i=\"2\" j=\"3\">\n"
                    "   <b k=\"4\">t2</b>\n"
                    "   <c>t4</c>t5<d>t6</d>\n"
                    "   <e l=\"5\" m=\"6\">\n"
                    "      <f>t10</f>t11</e>\n"
                    "   <g>t13</g>t14</a>\n")]
    (is (= expect (with-out-str
                    (xml/emit input-tree :indent 3))))))

(defn emit-char-seq [xml-tree encoding]
  (let [stream (java.io.ByteArrayOutputStream.)]
    (binding [*out* (java.io.OutputStreamWriter. stream encoding)]
      (xml/emit xml-tree :encoding encoding)
      (map #(if (pos? %) (char %) %) (.toByteArray stream)))))

(deftest encoding
  (let [input-tree
         (with-in-str "<how-cool>Übercool</how-cool>" (xml/parse *in*))]
    (is (= (concat "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                   "<how-cool>" [-61 -100] "bercool</how-cool>")
           (emit-char-seq input-tree "UTF-8")))
    (is (= (concat "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
                   "<how-cool>" [-36] "bercool</how-cool>")
           (emit-char-seq input-tree "ISO-8859-1")))))

(deftest encoding-assertion
  (is (thrown? Exception
        (let [stream (java.io.ByteArrayOutputStream.)]
          (binding [*out* (java.io.OutputStreamWriter. stream "UTF-8")]
            (xml/emit (element :foo) :encoding "ISO-8859-1"))))))
