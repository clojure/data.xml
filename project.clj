(defproject org.clojure/data.xml "0-UE-DEVELOPMENT"
  :source-paths ["src/main/clojure" "src/main/clojurescript"]
  :test-paths ["src/test/clojure" "src/test/clojurescript"]
  :resource-paths ["src/main/resources" "src/test/resources" "target/gen-resources"]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.codec "0.1.1"]
                 [org.clojure/clojurescript "1.10.516"]
                 [cider/piggieback "0.3.10"]
                 [nrepl "0.5.3"]
                 [org.clojure/test.check "0.9.0"]
                 [figwheel-sidecar "0.5.18"]
                 [binaryage/devtools "0.9.10"]]
  :plugins [[cider/cider-nrepl "0.20.0"]])
