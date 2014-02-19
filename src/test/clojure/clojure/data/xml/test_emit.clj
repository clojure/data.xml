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
  (:use clojure.test
        clojure.data.xml
        [clojure.data.xml.test-utils :only (test-stream lazy-parse*)]))

(def deep-tree
  (lazy-parse* (str "<a h=\"1\" i='2' j=\"3\">"
                    "  t1<b k=\"4\">t2</b>"
                    "  t3<c>t4</c>"
                    "  t5<d>t6</d>"
                    "  t7<e l=\"5\" m=\"6\">"
                    "    t8<f>t10</f>t11</e>"
                    "  t12<g>t13</g>t14"
                    "</a>")))

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
    (is (= expect (emit-str deep-tree)))))

(deftest defaults
  ;;XML below should be updated when namespace support is in
  (let [expect (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bar item=\"1\"><baz item=\"2\">done</baz></bar>")]
    (is (= expect (emit-str (element "foo/bar" {"foo/item" 1} [(element "foo/baz" {"foo/item" 2} "done")]))))))


(deftest mixed-quotes
  (is (= (lazy-parse*
          (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
               "<mixed double=\"&quot;double&quot;quotes&quot;here&quot;\""
               " single=\"'single'quotes'here\"></mixed>"))
         (lazy-parse*
          (emit-str (element :mixed
                             {:single "'single'quotes'here"
                              :double "\"double\"quotes\"here\""}))))))

;; TODO add an indentation test once we figure out how to indent portably across JREs


(defn emit-char-seq [xml-tree encoding]
  (with-open [bos (java.io.ByteArrayOutputStream.)
        stream (java.io.OutputStreamWriter. bos encoding)]
    (emit xml-tree stream :encoding encoding)
    (.flush stream)
    (map #(if (pos? %) (char %) %) (.toByteArray bos))))

(deftest encoding
  (let [input-tree
         (lazy-parse* "<how-cool>Übercool</how-cool>")]
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
            (emit (element :foo) *out* :encoding "ISO-8859-1"))))))

(deftest emitting-cdata
  (testing "basic cdata"
    (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                "<cdata-stuff><![CDATA[<goes><here>]]></cdata-stuff>")
           (emit-str (element :cdata-stuff {}
                              (cdata "<goes><here>"))))))
  (testing "cdata with ]]> chars"
    (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                "<cdata-stuff><![CDATA[<goes><here>]]]]><![CDATA[><and><here>]]></cdata-stuff>")
           (emit-str (element :cdata-stuff {}
                              (cdata "<goes><here>]]><and><here>"))))))
  (testing "cdata with ]]> chars and newlines"
    (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                "<cdata-stuff><![CDATA[<goes><here>\n\n\n]]]]><![CDATA[><and><here>]]></cdata-stuff>")
           (emit-str (element :cdata-stuff {}
                              (cdata "<goes><here>\n\n\n]]><and><here>")))))))

(deftest emitting-cdata-with-embedded-end
  (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
              "<cdata-stuff><![CDATA[<goes><here>]]]]><![CDATA[><and><here>]]></cdata-stuff>")
         (emit-str (element :cdata-stuff {}
                                (cdata "<goes><here>]]><and><here>")))))  )

(deftest emitting-comment
  (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
              "<comment-stuff>comment <!-- goes here --> not here</comment-stuff>")
         (emit-str (element :comment-stuff {}
                                "comment "
                                (xml-comment " goes here ")
                                " not here"))))  )

(deftest test-indent
  (let [nested-xml (lazy-parse* (str "<a><b><c><d>foo</d></c></b></a>"))
        expect (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a>\n  "
                    "<b>\n    <c>\n      <d>foo</d>\n    </c>\n  </b>\n</a>\n")
        sw (java.io.StringWriter.)]
     (indent nested-xml sw)
    (is (= expect (.toString sw)))))

(deftest test-indent-str
  (let [nested-xml (lazy-parse* (str "<a><b><c><d>foo</d></c></b></a>"))
        expect (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a>\n  "
                    "<b>\n    <c>\n      <d>foo</d>\n    </c>\n  </b>\n</a>\n")]
    (is (= expect (indent-str nested-xml)))))

(deftest test-indent
  (let [nested-xml (lazy-parse* (str "<a><b><c><d>foo</d></c></b></a>"))
        expect (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a>\n  "
                    "<b>\n    <c>\n      <d>foo</d>\n    </c>\n  </b>\n</a>\n")
        sw (java.io.StringWriter.)]
    (indent nested-xml sw :encoding "UTF-8")
    (is (= expect (.toString sw)))))

(deftest test-boolean
  (is (= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo>true</foo>"
         (emit-str (element :foo {} true)))))

(deftest test-number
  (is (= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo>1</foo>"
         (emit-str (element :foo {} 1))))
  (is (= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo>1.2</foo>"
         (emit-str (element :foo {} 1.2))))
  (is (= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo>0</foo>"
         (emit-str (element :foo {} (int 0)))))
  (is (= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo>1.2</foo>"
         (emit-str (element :foo {} (float 1.2))))))


(deftest test-hiccup
   (is (= [:foo true]
        (emit-sexp (element :foo {} true)))))