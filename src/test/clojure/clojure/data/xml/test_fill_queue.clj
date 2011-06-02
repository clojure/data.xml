;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for feeding values from an imperative loop into a lazy seq."
      :author "Chris Houser"}
  clojure.data.xml.test-fill-queue
  (:use [clojure.test :only [deftest is are]]
        [clojure.data.xml :as xml :only []]))

(def fill-queue #'xml/fill-queue)

(deftest basic
  (is (= (range 5) (fill-queue (fn [fill] (doseq [i (range 5)] (fill i)))))))

(deftest unusual-values
  (let [v [:keyword 'symbol nil 42 false (Object.) true "string"]]
    (is (= v (fill-queue (fn [fill] (doseq [i v] (fill i))))))))

(deftest producer-error-delivered
  (is (thrown? Exception 
               (first (fill-queue (fn [f] (throw (Exception. "fail"))))))))

(deftest producer-blocks
  (is (= false
         (let [done (atom false)]
           (dorun (take 2 (fill-queue
                            (fn [f]
                                (doseq [i (range 4)] (f i))
                                (reset! done true)))))
           (Thread/sleep 1)
           @done)))
  (is (= false
         (let [done (atom false)]
           (dorun (take 2 (fill-queue
                            (fn [f]
                                (doseq [i (range 8)] (f i))
                                (reset! done true))
                            :queue-size 5)))
           (Thread/sleep 1)
           @done))))

(defn just [n]
  (concat (range n) (lazy-seq (throw (Exception. "not lazy enough")))))

(deftest producer-errors-delayed
  (is (= (range 0) (take 0 (fill-queue (fn [f] (doseq [i (just 0)] (f i)))))))
  (is (= (range 1) (take 1 (fill-queue (fn [f] (doseq [i (just 1)] (f i)))))))
  (is (= (range 5) (take 5 (fill-queue (fn [f] (doseq [i (just 5)] (f i))))))))
