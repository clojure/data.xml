(ns clojure.data.xml.test-cljs
  (:require [cljs.test :as test]
            [clojure.data.xml :as xml]
            clojure.data.xml.test-cljs-basic
            clojure.data.xml.test-cljs-extended))

(defn ^:export -main []
  (set! *print-newline* false)
  (set! *print-fn* js/print)
  (set! *print-err-fn* js/print)
  (println "Running Basic Tests")
  (test/run-tests 'clojure.data.xml.test-cljs-basic)
  (println "Extending DOM Objects and running again + extended tests")
  (xml/extend-dom-as-data!)
  (test/testing "with extended native dom"
    (test/run-tests 'clojure.data.xml.test-cljs-basic 'clojure.data.xml.test-cljs-extended)))
