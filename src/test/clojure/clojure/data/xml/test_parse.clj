;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for XML parsing functions."
      :author "Chris Houser"}
  clojure.data.xml.test-parse
  (:require
   [clojure.test :refer :all]
   [clojure.data.xml :refer [parse-str element]]
   [clojure.data.xml.test-utils :refer [test-stream lazy-parse*]]))

(deftest simple
  (let [input "<html><body bg=\"red\">This is <b>bold</b> test</body></html>"
        expected (element :html {} (element :body {:bg "red"}
                   "This is " (element :b {} "bold") " test"))]
    (is (= expected (lazy-parse* input)))))

(deftest deep
  (let [input (str "<a h='1' i=\"2\" j='3'>"
                   "  t1<b k=\"4\">t2</b>"
                   "  t3<c>t4</c>"
                   "  t5<d>t6</d>"
                   "  t7<e l='5' m='6'>"
                   "    t8<f>t10</f>t11</e>"
                   "  t12<g>t13</g>t14"
                   "</a>")
        expected (element :a {:h "1", :i "2", :j "3"}
                   "  t1" (element :b {:k "4"} "t2")
                   "  t3" (element :c {} "t4")
                   "  t5" (element :d {} "t6")
                   "  t7" (element :e {:l "5" :m "6"}
                   "    t8" (element :f {} "t10") "t11")
                   "  t12" (element :g {} "t13") "t14")]
    (is (= expected (lazy-parse* input)))
    (is (= expected (parse-str input)))))

(deftest test-xml-with-whitespace
    (let [input (str "<a>\n<b with-attr=\"s p a c e\">123</b>\n<c>1 2 3</c>\n\n</a>")
        expected (element :a {}
                          (element :b {:with-attr "s p a c e"} "123")
                          (element :c {}  "1 2 3"))]
    (is (= expected (lazy-parse* input)))))

(deftest test-cdata-parse
(let [input "<cdata><is><here><![CDATA[<dont><parse><me>]]></here></is></cdata>"
      expected (element :cdata {} (element :is {}
                                           (element :here {}
                                                    "<dont><parse><me>")))]
  (is (= expected (lazy-parse* input)))))

(deftest test-comment-parse
(let [input "<comment><is><here><!-- or could be -->there</here></is></comment>"
      expected (element :comment {} (element :is {}
                                           (element :here {}
                                                    "there")))]
  (is (= expected (lazy-parse* input)))))

(deftest test-parsing-processing-instructions
  (let [input "<?xml version=\"1.0\" encoding=\"utf-8\"?>
                <?xml-stylesheet type='text/xsl' href='someFile.xsl'?>
                <ATag>With Stuff</ATag>"
        expected (element :ATag {} "With Stuff")]
    (is (= expected (parse-str input)))))

(deftest test-parsing-doctypes
  (let [input "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"
               \"foo://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">
               <html><h1>Heading Stuff</h1></html>"
        expected (element :html {}
                          (element :h1 {} "Heading Stuff"))]
    (is (= expected (parse-str input)))))

(deftest test-coalescing
  (let [input "<a><![CDATA[\nfoo bar\n]]><![CDATA[\nbaz\n]]></a>"]
    (is (= ["\nfoo bar\n\nbaz\n"] (:content (parse-str input))))
    (is (= ["\nfoo bar\n" "\nbaz\n"] (:content
                                      (parse-str input :coalescing false))))))

(deftest test-location-meta
  (let [input "<a><b/>\n<b/></a>"
        location-meta (comp :clojure.data.xml/location-info meta)]
    ;the numbers look 1 based
    (is (= 1 (-> input parse-str location-meta :line-number)))
    (is (= 1 (-> input parse-str location-meta :column-number)))
    (is (= 1 (-> input parse-str :content first location-meta :line-number)))
    (is (= 4 (-> input parse-str :content first location-meta :column-number)))
    (is (= 2 (-> input parse-str :content second location-meta :line-number)))
    (is (nil? (-> input
                  (parse-str :location-info false)
                  location-meta)))))
