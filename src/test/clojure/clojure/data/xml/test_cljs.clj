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
   [clojure.test :refer :all]
   [cljs.repl :as repl]
   [cljs.repl.nashorn :as repl-nh]
   [cljs.closure :as closure]
   [cljs.build.api :as bapi]
   [clojure.string :as str]
   [clojure.java.io :as io])
  (:import
   java.nio.file.Files
   java.nio.file.attribute.FileAttribute))

(defn nashorn-env []
  (let [{:as env :keys [engine]} (repl-nh/repl-env)]
    (repl-nh/eval-resource engine "dxml-nashorn.generated.js" true)
    env))

(defn tempdir []
  (str (Files/createTempDirectory
        "cljs-nashorn-" (into-array FileAttribute []))))

(defn compile-testsuite! [dir]
  (let [out (io/file dir "tests.js")]
    (println "Building in" dir)
    (bapi/build (bapi/inputs "src/main/clojure" "src/test/clojure")
                {:output-to (str out)
                 :output-dir dir
                 :main 'clojure.data.xml.test-cljs
                 :optimizations :simple
                 ;; :pseudo-names true
                 :preamble ["dxml-nashorn.generated.js"]})
    (spit (io/file dir "tests.reopt.js")
          (closure/optimize {:optimizations :simple
                             :pretty-print true}
                            (slurp out)))))

(defn run-testsuite! [dir]
  (let [{:keys [engine]} (repl-nh/repl-env)]
    (compile-testsuite! dir)
    (.eval engine (io/reader (io/file dir "tests.reopt.js")))
    (.eval engine "clojure.data.xml.test_cljs._main()")))

(deftest clojurescript-test-suite
  (is (= false (run-testsuite! (tempdir)))))

(comment

  (def td (tempdir))
  (def engine (:engine (repl-nh/repl-env)))
  (run-testsuite! td)
  (.eval engine (io/reader (io/file td "tests.reopt.js")))
  (.eval engine "clojure.data.xml.test_cljs._main()")

  )

#_(deftest clojurescript-test-suite
    (is (= :success
           (read-string
            (repl/evaluate-form
             (doto (repl-nh/repl-env :debug true)
               (repl/-setup {:output-dir (tempdir)}))
             (ana/empty-env)
             "TESTSUITE LAUNCHER"
             (list 'do
                   (list 'require ''clojure.data.xml.test-cljs)
                   (list 'clojure.data.xml.test-cljs/run-tests)))))))
