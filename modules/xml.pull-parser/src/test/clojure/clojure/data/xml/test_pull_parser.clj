;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for XML pull-parsing functions."
      :author "Chris Houser"}
  clojure.data.xml.test-pull-parser
  (:use [clojure.test :only [deftest is are]]
        [clojure.data.xml :as xml :only [element]]
        [clojure.data.xml.pull-parser :as pp :only []]))

(defn test-stream [x]
  (java.io.ByteArrayInputStream. (.getBytes x)))

(def lazy-parse* (comp pp/lazy-parse test-stream))

(deftest simple
  (let [input "<html><body bg=\"red\">This is <b>bold</b> test</body></html>"
        expected (element :html {} (element :body {:bg "red"}
                   "This is " (element :b {} "bold") " test"))]
    (is (= expected (lazy-parse* input)))))


(def deep-tree (str "<a h='1' i=\"2\" j='3'>"
                   "  t1<b k=\"4\">t2</b>"
                   "  t3<c>t4</c>"
                   "  t5<d>t6</d>"
                   "  t7<e l='5' m='6'>"
                   "    t8<f>t10</f>t11</e>"
                   "  t12<g>t13</g>t14"
                   "</a>"))
(deftest deep
  (let [expected (element :a {:h "1", :i "2", :j "3"}
                   "  t1" (element :b {:k "4"} "t2")
                   "  t3" (element :c {} "t4")
                   "  t5" (element :d {} "t6")
                   "  t7" (element :e {:l "5" :m "6"}
                   "    t8" (element :f {} "t10") "t11")
                   "  t12" (element :g {} "t13") "t14")]
    (is (= expected (lazy-parse* deep-tree)))))


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
    (is (= expect (with-out-str (pp/emit (lazy-parse* deep-tree)))))))

(deftest mixed-quotes
  (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
              "<mixed double=\"&quot;double&quot;quotes&quot;here&quot;\""
              " single=\"'single'quotes'here\"/>")
         (with-out-str
           (xml/emit (element :mixed
                       {:single "'single'quotes'here"
                        :double "\"double\"quotes\"here\""}))))))

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
