(ns clojure.data.xml.cljs-repls
  (:require
   [cljs.repl :as repl]
   [cljs.repl.nashorn :as repl-nh]
   [cemerick.piggieback :as pback]
   [cljs.closure :as closure]
   [figwheel-sidecar.repl-api :refer [start-figwheel! stop-figwheel! cljs-repl]]))

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
      :source-paths ["src/main/clojure" "src/test/clojure" "src/test/clojurescript"]
      :figwheel {:on-jsload "clojure.data.xml.test-cljs/-main"}
      :compiler {:main 'clojure.data.xml.test-cljs
                 :preloads '[devtools.preload]
                 :output-to "target/gen-resources/public/cljs-tests/main.js"
                 :output-dir "target/gen-resources/public/cljs-tests/output"
                 :asset-path "output"
                 :source-map true}}]})
  (cljs-repl))

(defn repl-piggieback! []
  (pback/cljs-repl (nashorn-env)))

(defn repl-main! []
  (repl/repl (nashorn-env)))
