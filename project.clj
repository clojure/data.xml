(defproject org.clojure/data.xml "0-UE-DEVELOPMENT"
  :source-paths ["src/main/clojure" "src/main/clojurescript"]
  :test-paths ["src/test/clojure" "src/test/clojurescript"]
  :resource-paths ["src/main/resources" "src/test/resources" "target/gen-resources"]
  :dependencies [[org.clojure/clojure "1.11.4"]
                 [org.clojure/clojurescript "1.10.439"]
                 [cider/piggieback "0.5.3"]
                 [org.clojure/tools.nrepl "0.2.13"]
                 [org.clojure/test.check "1.1.3"]
                 [figwheel-sidecar "0.5.17"]
                 [binaryage/devtools "0.9.10"]
                 [org.openjdk.nashorn/nashorn-core "15.3"]]
  :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]})
