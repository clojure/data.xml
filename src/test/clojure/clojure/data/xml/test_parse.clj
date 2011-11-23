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
  (:use [clojure.test :only [deftest is are]]
        [clojure.data.xml :as xml :only [element]]
        [clojure.data.xml.test-utils :only [test-stream lazy-parse*]]))

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
    (is (= expected (lazy-parse* input)))))

#_(deftest source-seq-release-head
  (doseq [func [xml/source-seq xml/lazy-source-seq]]
    (let [event1 (atom (xml/event :start-element :foo)) 
          weak (java.lang.ref.WeakReference. @event1)]
      (with-redefs [xml/fill-from-sax
                    (fn [src strt fill]
                      (fill @event1)
                      (fill (xml/event :end-element :foo)))]
        (let [more (rest (func nil nil))]
          (reset! event1 nil)
          (System/gc)
          (is (= nil (.get weak))))))))
