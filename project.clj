(defproject nu/data.xml "0.2.0-alpha3"
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure" "src/test/clojurescript"]
  :resource-paths ["src/test/resources" "target/gen-resources"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/clojurescript "1.9.473"]
                 [com.cemerick/piggieback "0.2.1"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/test.check "0.9.0"]
                 [figwheel-sidecar "0.5.8"]
                 [binaryage/devtools "0.8.3"]]
  :deploy-repositories [["nu-maven" {:url        "s3p://nu-maven/releases/"
                                     :sign-releases false }] ]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
