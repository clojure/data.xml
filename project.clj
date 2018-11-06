(defproject org.clojure/data.xml "0-UE-DEVELOPMENT"
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure" "src/test/clojurescript"]
  :resource-paths ["src/test/resources" "target/gen-resources"]
  :dependencies [[org.clojure/clojure "1.10.0-RC1"]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [com.cemerick/piggieback "0.2.2"]
                 [org.clojure/tools.nrepl "0.2.13"]
                 [org.clojure/test.check "0.9.0"]
                 [figwheel-sidecar "0.5.10"]
                 [binaryage/devtools "0.9.4"]]
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
