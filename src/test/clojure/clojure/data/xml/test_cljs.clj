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
    (catch Exception e
      (println "WARN: clojurescript test suite not available with Clojure"
               *clojure-version* (System/getProperty "java.runtime.name")
               (System/getProperty "java.vm.version") (System/getProperty "java.runtime.version")
               \newline e))))
