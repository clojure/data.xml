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
  (:require
   [clojure.test :refer :all]
   [clojure.data.xml :refer :all]
   [clojure.data.xml.test-utils :refer [test-stream lazy-parse*]]))

(def deep-tree
  (lazy-parse* (str "<a h=\"1\" i='2' j=\"3\">"
                    "  t1<b k=\"4\">t2</b>"
                    "  t3<c>t4</c>"
                    "  t5<d>t6</d>"
                    "  t7<e l=\"5\" m=\"6\">"
                    "    t8<f>t10</f>t11</e>"
                    "  t12<g>t13</g>t14"
                    "</a>")))

(deftest test-defaults
  (testing "basic parsing"
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

  (testing "namespaced defaults"
    (let [expect (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?><D:bar xmlns:D=\"DAV:\" D:item=\"1\"><D:baz D:item=\"2\">done</D:baz></D:bar>")]
      (is (= expect (emit-str
                     (element "{DAV:}bar" {"{DAV:}item" 1 :xmlns/D "DAV:"}
                              [(element "{DAV:}baz" {:xml.dav/item 2} "done")]))))
      (is (= expect (emit-str
                     {:tag "{DAV:}bar" :attrs {"{DAV:}item" 1 :xmlns/D "DAV:"}
                      :content [{:tag "{DAV:}baz" :attrs {:xml.dav/item 2} :content "done"}]}))))))

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

(deftest doctype
  (let [input-tree
          (lazy-parse* "<how-cool>cool</how-cool>")
        doctype-html "<!DOCTYPE html>"
        doctype-html-401-transitional
          "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
        doctype-xhtml-10-strict
          "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"]
    (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                doctype-html
                "<how-cool>cool</how-cool>")
           (emit-str input-tree :doctype doctype-html)))
    (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                doctype-html-401-transitional
                "<how-cool>cool</how-cool>")
           (emit-str input-tree :doctype doctype-html-401-transitional)))
    (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                doctype-xhtml-10-strict
                "<how-cool>cool</how-cool>")
           (emit-str input-tree :doctype doctype-xhtml-10-strict)))))

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
                                " not here")))))

(def xml-decl-newline?
  (-> (System/getProperty "java.version")
      (.startsWith "1.8")
      not))

(deftest test-indent
  (let [nested-xml (lazy-parse* (str "<a><b><c><d>foo</d></c></b></a>"))
        expect (str "<a>\n  <b>\n    <c>\n      <d>foo</d>\n    </c>\n  </b>\n</a>\n")
        sw (java.io.StringWriter.)
        _ (indent nested-xml sw)
        result (.toString sw)]
     (is (= expect
            (subs result (.indexOf result "<a>"))))))

(deftest test-indent-str
  (let [nested-xml (lazy-parse* (str "<a><b><c><d>foo</d></c></b></a>"))
        expect (str "<a>\n  <b>\n    <c>\n      <d>foo</d>\n    </c>\n  </b>\n</a>\n")
        result (indent-str nested-xml)]
    (is (= expect (subs result (.indexOf result "<a>"))))))

(deftest test-indent-str-with-doctype
  (let [nested-xml (lazy-parse* (str "<a><b><c><d>foo</d></c></b></a>"))
        doctype "<!DOCTYPE html>"
        expect (str doctype "\n<a>\n  <b>\n    <c>\n      <d>foo</d>\n    </c>\n  </b>\n</a>\n")
        result (indent-str nested-xml :doctype doctype)]
    (is (= expect (subs result (.indexOf result doctype))))))

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

(deftest test-event-seq-emit
  (is (= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a>123</a>"
         (emit-str (event-seq (java.io.StringReader. "<a>123</a>") {})))))

(deftest test-sibling-xmlns
  (let [el (element (to-qname "{NS1}top") {}
                    (element (to-qname "{NS2}foo"))
                    (element (to-qname "{NS2}bar")))]
    (is (= (parse-str (emit-str el)) el))))

(deftest test-default-xmlns
  (let [nss-meta (comp :clojure.data.xml/nss meta)]
    (is (= {:xmlns "NS"}
           (nss-meta (parse-str "<foo xmlns=\"NS\"/>"))
           (nss-meta (parse-str (emit-str (parse-str "<foo xmlns=\"NS\"/>"))))))))
