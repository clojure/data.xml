(ns clojure.data.xml.cljs-testsuite
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

(defn tempdir []
  (str (Files/createTempDirectory
        "cljs-nashorn-" (into-array FileAttribute []))))

(defn compile-testsuite! [dir]
  (let [out (io/file dir "tests.js")]
    (println "Building in" dir)
    (bapi/build (bapi/inputs "src/main/clojure" "src/test/clojure" "src/test/clojurescript")
                {:output-to (str out)
                 :output-dir dir
                 :main 'clojure.data.xml.test-cljs
                 :optimizations :advanced
                 :pseudo-names true
                 :preamble ["dxml-nashorn.generated.js"]})
    (spit (io/file dir "tests.reopt.js")
          (closure/optimize {:optimizations :simple
                             :pretty-print true
                             :closure-warnings
                             {:non-standard-jsdoc :off}}
                            (slurp out)))))

(defn run-testsuite! [dir]
  (System/setProperty "nashorn.persistent.code.cache" "target/nashorn_code_cache")
  (let [engine (repl-nh/create-engine)]
    (println "INFO" "Running nashorn-repl with" (System/getProperty "nashorn.persistent.code.cache"))
    (compile-testsuite! dir)
    (.eval engine (io/reader (io/file dir "tests.reopt.js")))
    (let [{:as res :keys [fail error]} (read-string (.eval engine "clojure.data.xml.test_cljs._main_nashorn()"))]
      (is (and (zero? fail) (zero? error))
          (pr-str res)))))

(comment

  (def td (tempdir))
  (def engine (:engine (repl-nh/repl-env)))
  (run-testsuite! td)
  (.eval engine (io/reader (io/file td "tests.reopt.js")))
  (.eval engine "clojure.data.xml.test_cljs._main()")

  )
