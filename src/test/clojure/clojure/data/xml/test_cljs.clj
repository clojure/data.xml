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
     [cemerick.piggieback :as pback]
     [cljs.closure :as closure]
     #_[cljs.analyzer :as ana]
     [cljs.build.api :as bapi]
     [figwheel-sidecar.repl-api :refer [start-figwheel! stop-figwheel! cljs-repl]])
    (:import
     java.nio.file.Files
     java.nio.file.attribute.FileAttribute))

(defn nashorn-env []
  (let [{:as env :keys [engine]} (repl-nh/repl-env)]
    (repl-nh/eval-resource engine "dxml-nashorn.generated.js" true)
    env))

(def handle-redirect (constantly {:status 307 :headers {"Location" "/cljs-tests/index.html"}}))

(defn repl-figwheel! []
  (start-figwheel!
   {:figwheel-options
    {:http-server-root "public"
     :ring-handler `handle-redirect}
    :all-builds
    [{:id "tests"
      :source-paths ["src/main/clojure" "src/test/clojure"]
      :figwheel {:on-jsload "clojure.data.xml.test-cljs/-main"}
      :compiler {:main 'clojure.data.xml.test-cljs
                 :output-to "target/gen-resources/public/cljs-tests/main.js"
                 :output-dir "target/gen-resources/public/cljs-tests/output"
                 :asset-path "output"
                 :source-map true}}]})
  (cljs-repl))

(defn repl-piggieback! []
  (pback/cljs-repl (nashorn-env)))

(defn repl-main! []
  (repl/repl (nashorn-env)))

(defn tempdir []
  (str (Files/createTempDirectory
        "cljs-nashorn-" (into-array FileAttribute []))))

(defn compile-testsuite []
  (bapi/build (bapi/inputs "src/main/clojure" "src/test/clojure")
              (let [dir (tempdir)]
                (println "Building in" dir)
                {:output-to (str dir "/tests.js")
                 :output-dir dir
                 :optimizations :whitespace
                 :pretty-print true
                 :preamble ["src/test/resources/dxml-nashorn.generated.js"]})))

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
