(defproject org.clojure/data.xml "0-UE-DEVELOPMENT"
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure"]
  :resource-paths ["src/test/resources" "target/gen-resources"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [com.cemerick/piggieback "0.2.1"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/test.check "0.9.0"]
                 [figwheel-sidecar "0.5.8"]
                 [binaryage/devtools "0.8.3"]]
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
