;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for event seqs."}
  clojure.data.xml.test-event
  (:require [clojure.test :refer :all]
            [clojure.data.xml :refer :all]
            [clojure.data.xml.event :as event])
  (:import
    [java.io StringReader]
    [clojure.data.xml.event StartElementEvent CharsEvent EndElementEvent]))

(deftest test-end-element-info
  (let [ev (vec (event-seq (StringReader. "<a><b>123</b></a>") {}))]
    (is (= 5 (count ev)))
    (is (= [StartElementEvent StartElementEvent CharsEvent EndElementEvent EndElementEvent]
          (map type ev)))
    (let [ee (last ev)]
      (is (= :a (:tag ee)))
      (is (= {:line-number 1, :column-number 14, :character-offset 13} (:location-info ee))))))

(comment
  (run-tests)
  )