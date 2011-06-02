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
        [clojure.data.xml :as xml :only [element]]))

(deftest simple
  (is (=
        (element :html {} (element :body {:bg "red"}
          "This is " (element :b {} "bold") " test"))
        (with-in-str
          "<html><body bg=\"red\">This is <b>bold</b> test</body></html>"
          (let [tree (xml/lazy-parse *in*)]
            (dorun (:content tree))
            tree)))))

