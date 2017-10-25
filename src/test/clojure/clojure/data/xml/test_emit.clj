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
   [clojure.data.xml.test-utils :refer [test-stream lazy-parse*]]
   [clojure.data.xml.impl :refer [compile-if]]
   [clojure.data.xml.name :as name]
   [clojure.data.xml.pu-map :as pu])
  (:import (javax.xml.namespace QName)))

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
                              [(element "{DAV:}baz" {:xmlns.DAV%3A/item 2} "done")]))))
      (is (= expect (emit-str
                     {:tag "{DAV:}bar" :attrs {"{DAV:}item" 1 :xmlns/D "DAV:"}
                      :content [{:tag "{DAV:}baz" :attrs {:xmlns.DAV%3A/item 2} :content "done"}]}))))))

(deftest mixed-quotes
  (is (= (lazy-parse*
          (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
               "<mixed double=\"&quot;double&quot;quotes&quot;here&quot;\""
               " single=\"'single'quotes'here\"></mixed>"))
         (lazy-parse*
          (emit-str (element :mixed
                             {:single "'single'quotes'here"
                              :double "\"double\"quotes\"here\""}))))))

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
        expect "\n<a>\n  <b>\n    <c>\n      <d>foo</d>\n    </c>\n  </b>\n</a>\n"
        result (indent-str nested-xml :doctype doctype)
        offset-dt (.indexOf result "<!DOCTYPE")
        offset-res (inc (.indexOf result ">" offset-dt))]
    (is (= expect (subs result offset-res)))))

(defmacro are-serializable [group-description extra-attrs & {:as data-strings}]
  `(testing ~group-description
     (testing "in content"
       ~@(for [[data string] data-strings]
           `(is (= (parse-str (emit-str (element :e ~extra-attrs ~string)))
                   (parse-str (emit-str (element :e ~extra-attrs ~data)))))))
     (testing "in attrs"
       ~@(for [[data string] data-strings]
           `(is (= (emit-str (element :e ~(assoc extra-attrs :a string)))
                   (emit-str (element :e ~(assoc extra-attrs :a data)))))))))

(deftest test-datatypes
  ;; https://www.w3.org/TR/xmlschema-2/#built-in-datatypes
  (testing "serializing"
    (are-serializable
     "booleans" {}
     true "true"
     false "false")
    (are-serializable
     "numbers" {}
     1 "1"
     1.2 "1.2"
     3/4 "0.75"
     (int 0) "0"
     (float 1.4) "1.4"
     1.25M "1.25"
     (BigInteger. "42424242424242424242424242424242") "42424242424242424242424242424242"
     42424242424242424242424242424242 "42424242424242424242424242424242")
    (are-serializable
     "byte-arrays" {}
     (byte-array [0 1 2 3 4]) "AAECAwQ=")
    (are-serializable
     "uris" {}
     (java.net.URI. "S:l") "S:l"
     (java.net.URL. "http://foo") "http://foo")
    (are-serializable
     "dates" {}
     (java.util.Date. 0) "1970-01-01T00:00:00.000-00:00")
    (compile-if
     (Class/forName "java.time.Instant")
     (are-serializable
      "instants" {}
      (java.time.Instant/ofEpochMilli 0) "1970-01-01T00:00:00.000-00:00")
     nil)
    (are-serializable
     "qnames" {:xmlns/p "U:"}
     :xmlns.U%3A/qn     "p:qn"
     (QName. "U:" "qn") "p:qn")
    (testing "qnames generated"
      (is (thrown? Exception (emit-str (element :e {} :xmlns.U%3A/qn))))
      (is (thrown? Exception (emit-str (element :e {:a :xmlns.U%3A/qn}))))
      (is (thrown? Exception (emit-str (element :e {} (QName. "U:" "qn")))))
      (is (thrown? Exception (emit-str (element :e {:a (QName. "U:" "qn")})))))))

(deftest test-event-seq-emit
  (is (= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a>123</a>"
         (emit-str (event-seq (java.io.StringReader. "<a>123</a>") {})))))

(deftest test-sibling-xmlns
  (let [el (element (as-qname "{NS1}top") {}
                    (element (as-qname "{NS2}foo"))
                    (element (as-qname "{NS2}bar")))]
    (is (= (parse-str (emit-str el)) el))))

(alias-uri :xml name/xml-uri)

(deftest test-default-xmlns
  (let [nss-meta (comp :clojure.data.xml/nss meta)]
    (is (= (pu/merge-prefix-map nil {"" "NS"})
           (nss-meta (parse-str "<foo xmlns=\"NS\"/>"))
           (nss-meta (parse-str (emit-str (parse-str "<foo xmlns=\"NS\"/>")))))))
  (is (thrown? Exception (emit-str {:tag :el :attrs {(name/qname name/xmlns-uri "xml")   "foo"}})))
  (is (thrown? Exception (emit-str {:tag :el :attrs {(name/qname name/xmlns-uri "xmlns") "foo"}})))
  (is (thrown? Exception (emit-str {:tag :el :attrs {:xmlns/xml   "foo"}})))
  (is (thrown? Exception (emit-str {:tag :el :attrs {:xmlns/xmlns "foo"}})))
  (is (thrown? Exception (parse-str "<element xmlns:xmlns=\"http://www.w3.org/2000/xmlns/\" />"))
      "TODO: find out if this is standard conforming, or a bug in StAX")
  (is (= (emit-str {:tag :el :attrs {:xmlns/xmlns "http://www.w3.org/2000/xmlns/"}})
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><el/>"))
  (is (= (emit-str {:tag :el :attrs {:xmlns/xml "http://www.w3.org/XML/1998/namespace" ::xml/lang "en"}})
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><el xml:lang=\"en\"/>")))

(deftest test-empty-elements
  (is (= (emit-str {:tag :a :content []}) "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a/>"))
  (is (= (emit-str {:tag :a :content [""]}) "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a></a>")))

(deftest test-roundtrip
  (is (= (emit-str (with-meta (parse-str "<foo:element xmlns:foo=\"FOO:\"/>")
                     nil))
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a:element xmlns:a=\"FOO:\"/>"))
  (is (= (emit-str (parse-str "<foo:element xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" xmlns:foo=\"FOO:\"/>"))
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo:element xmlns:foo=\"FOO:\"/>"))
  (is (= (emit-str (parse-str "<element xmlns=\"FOO:\"/>"))
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><element xmlns=\"FOO:\"/>"))
  ; builtins
  (is (= (emit-str (parse-str "<element xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" xml:foo=\"FOO!\"/>"))
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?><element xml:foo=\"FOO!\"/>"))
  (is (thrown? Exception (parse-str "<xmlns:el/>"))
      "TODO: find out if this is standard conforming, or a bug in StAX"))
