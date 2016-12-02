(ns clojure.data.xml.test-cljs
  (:require [cljs.test :as test]
            [clojure.data.xml :as xml]
            clojure.data.xml.test-cljs-basic
            clojure.data.xml.test-cljs-extended))

(defn ^:export -main []
  (test/run-tests 'clojure.data.xml.test-cljs-basic)
  (xml/extend-dom-as-data!)
  (test/testing "with extended native dom"
    (test/run-tests 'clojure.data.xml.test-cljs-basic 'clojure.data.xml.test-cljs-extended)))
