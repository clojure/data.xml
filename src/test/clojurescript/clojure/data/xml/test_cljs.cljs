(ns clojure.data.xml.test-cljs
  (:require [cljs.test :as test]
            [clojure.data.xml :as xml]
            clojure.data.xml.test-cljs-basic
            clojure.data.xml.test-cljs-extended
            clojure.data.xml.test-equiv
            clojure.data.xml.test-pu
            clojure.data.xml.test-process))

(def ^:dynamic *results*)

(defmethod test/report [::test/default :end-run-tests]
  [m]
  (assert (nil? *results*))
  (set! *results* m))

(defn ^:export -main-nashorn []
  (set! *print-newline* false)
  (set! *print-fn* js/print)
  (set! *print-err-fn* js/print)
  (binding [*results* nil]
    (println "Running Basic Tests")
    (test/run-tests 'clojure.data.xml.test-cljs-basic
                    'clojure.data.xml.test-equiv
                    'clojure.data.xml.test-pu
                    'clojure.data.xml.test-process)
    (pr-str *results*)))

(defn ^:export -main []
  (binding [*results* nil]
    (println "Running Basic Tests")
    (test/run-tests 'clojure.data.xml.test-cljs-basic
                    'clojure.data.xml.test-pu)
    (println "Extending DOM Objects and running again + extended tests")
    (xml/extend-dom-as-data!)
    (test/testing "with extended native dom"
      (test/run-tests 'clojure.data.xml.test-cljs-basic
                      'clojure.data.xml.test-cljs-extended
                      'clojure.data.xml.test-equiv))
    *results*))
