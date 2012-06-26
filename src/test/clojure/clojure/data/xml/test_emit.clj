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
  (is (= (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
              "<cdata-stuff><![CDATA[<goes><here>]]></cdata-stuff>")
         (emit-str (element :cdata-stuff {}
                                (cdata "<goes><here>")))))  )

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

(comment




(import '[clojure.data.xml Element])

  ;;
  ;; Doesn't work
  ;;
  (defn just-elements2 [total]
    (Element. :sparql {:xmlns "http://www.w3.org/2005/sparql-results#"}
              (list
               (Element. :foo {}
                         (for [x (range 1 total)]
                           (Element. :result {}
                                     (list
                                      (Element. :binding {:name "a"} (list (Element. :literal {} (list (str x)))))
                                      (Element. :binding {:name "b"} (list (Element. :literal {} (list (str "b" x)))))
                                      (Element. :binding {:name "c"} (list (Element. :literal {} (list (str "c" x))))))))))))








  ;;
  ;; Works
  ;;
  (defn just-elements [total]
    (Element. :sparql {:xmlns "http://www.w3.org/2005/sparql-results#"}
              (for [x (range 1 total)]
                (Element. :result {}
                          (list
                           (Element. :binding {:name "a"} (list (Element. :literal {} (list (str x)))))
                           (Element. :binding {:name "b"} (list (Element. :literal {} (list (str "b" x)))))
                           (Element. :binding {:name "c"} (list (Element. :literal {} (list (str "c" x))))))))))





























  (defn just-elements4 [total]
    (Element. :sparql {:xmlns "http://www.w3.org/2005/sparql-results#"}
              (list
               (Element. :foo {}
                         (for [x (range 1 total)]
                           (Element. :result {}
                                     (list
                                      (Element. :binding {:name "a"} (list ))
                                      (Element. :binding {:name "b"} (list ))
                                      (Element. :binding {:name "c"} (list ))))))))

    )

  (defn just-elements-nate [total]
    (Element. :sparql {:xmlns "http://www.w3.org/2005/sparql-results#"}
              (lazy-seq
               (list
                (Element. :foo {}
                          (for [x (range 1 total)]
                            (Element. :result {}
                                      (list
                                       (Element. :binding {:name "a"} (list (Element. :literal {} (list (str x)))))
                                       (Element. :binding {:name "b"} (list (Element. :literal {} (list (str "b" x)))))
                                       (Element. :binding {:name "c"} (list (Element. :literal {} (list (str "c" x)))))))))))))

  (defn just-elements3 [total]
    [:sparql {:xmlns "http://www.w3.org/2005/sparql-results#"}
     [[:foo {}
       (for [x (range 1 total)]
         [:result {}
          [[:binding {:name "a"} [[:literal {} (str x)]]]
           [:binding {:name "b"} [[:literal {} (str "b" x)]]]
           [:binding {:name "c"} [[:literal {} (str "c" x)]]]]])]]])


  (defn output-big-xml [path doc]
    (with-open [oo (java.io.FileWriter. path)]
      (emit doc oo)))

  (defn output-big-xml-event [path doc]
    (with-open [oo (java.io.FileWriter. path)]
      (emit-events doc oo)))

  #_(defn output-big-xml2 [path doc]
      (with-open [oo (java.io.FileWriter. path)]
        (emit2 doc oo))))