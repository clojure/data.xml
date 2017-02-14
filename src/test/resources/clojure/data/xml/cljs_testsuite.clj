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
  (let [out (io/file dir "tests.js")
        inputs ["src/main/clojure" "src/test/clojure" "src/test/clojurescript"]]
    (println "INFO" "Compiling cljs testsuite from" inputs "into" (str out))
    (bapi/build (apply bapi/inputs inputs)
                {:output-to (str out)
                 :output-dir dir
                 :main 'clojure.data.xml.test-cljs
                 :optimizations :advanced
                 :pseudo-names true
                 :pretty-print true
                 :preamble ["dxml-nashorn.generated.js"]})))

(defn run-testsuite! [dir]
  (System/setProperty "nashorn.persistent.code.cache" "target/nashorn_code_cache")
  (let [engine (repl-nh/create-engine)]
    (compile-testsuite! dir)
    (println "INFO" "Running cljs tests in nashorn with persistent code cache in" (System/getProperty "nashorn.persistent.code.cache"))
    (.eval engine (io/reader (io/file dir "tests.js")))
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
