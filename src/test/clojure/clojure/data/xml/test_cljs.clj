;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Clojurescript tests for data.xml"}
    clojure.data.xml.test-cljs
  (:require
   [clojure.test :refer :all]))

(deftest clojurescript-test-suite
  (try
    (require 'clojure.data.xml.cljs-testsuite)
    (eval '(clojure.data.xml.cljs-testsuite/run-testsuite! "target/cljs-test-nashorn"))
    (when (not (neg? (compare (System/getProperty "java.runtime.version")
                              "1.9")))
      (println "CELEBRATION: CLJS-2377 has been fixed. Hooray! Please add jdk >= 1.9 back to the test matrix"))
    (catch Exception e
      (if (or (neg? (compare ((juxt :major :minor) *clojure-version*)
                             [1 8]))
              (neg? (compare (System/getProperty "java.runtime.version")
                             "1.8"))
              (not (neg? (compare (System/getProperty "java.runtime.version")
                                  "1.9"))))
        (println "WARN: ignoring cljs testsuite error on clojure < 1.8 or jdk < 1.8 also on jdk >= 1.9, see CLJS-2377"
                 *clojure-version* (System/getProperty "java.runtime.name")
                 (System/getProperty "java.vm.version") (System/getProperty "java.runtime.version")
                 \newline (str e))
        (do (println "ERROR: cljs nashorn test suite should be able to run on clojure >= 1.8 and jdk >= 1.8"
                     *clojure-version* (System/getProperty "java.runtime.name")
                     (System/getProperty "java.vm.version") (System/getProperty "java.runtime.version"))
            (throw e))))))
