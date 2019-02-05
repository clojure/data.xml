(ns clojure.data.xml.js.node
  (:require [clojure.data.xml.js.parse :as parse]
            [clojure.data.xml.tree :as tree]
            [clojure.data.xml.push-handler :as push-handler]))

(defn alloc-buffer [size]
  (new (.-Buffer (js/require "buffer")) size))

(defn reduce-fd-async [rf state fd]
  (let [fs (js/require "fs")]
    (js/Promise.
     (fn [return reject]
       (let [buf (alloc-buffer 1024)]
         ((fn read-loop [state pos]
            (if (reduced? state)
              (return (rf @state))
              (.read fs fd buf 0 1024 pos
                     (fn [err bytes-read _]
                       (if err
                         (reject err)
                         (if (pos? bytes-read)
                           (read-loop (rf state (.slice buf 0 bytes-read))
                                      (+ pos bytes-read))
                           (return (rf state))))))))
          state 0))))))

(defn make-fd-source [{:keys [sync]} fd]
  (let [fs (js/require "fs")
        fd-seq (fn fd-seq [pos]
                 (lazy-seq
                  (let [buf (alloc-buffer 1024)
                        bytes-read (.readSync fs fd buf 0 1024 pos)]
                    (when (pos? bytes-read)
                      (cons (.slice buf 0 bytes-read)
                            (fd-seq (+ pos bytes-read)))))))
        reduce-async (fn reduce-async [f state buf pos]
                       (if (reduced? state)
                         (f @state)
                         (.read fs fd buf 0 1024 pos
                                (fn [err bytes-read _]
                                  (if (pos? bytes-read)
                                    (reduce-async f (f state (.slice buf 0 bytes-read))
                                                  buf (+ pos bytes-read))
                                    (f state))))))]
    (if sync
      (reify
        ISeqable
        (-seq [_]
          (fd-seq 0))
        IReduce
        (-reduce [_ f init]
          (let [b (alloc-buffer 1024)]
            (loop [p 0 s init]
              (if (reduced? s)
                @s
                (let [bytes-read (.readSync fs fd b 0 1024 p)]
                  (if (pos? bytes-read)
                    (recur (+ p bytes-read)
                           (f s (.slice b 0 bytes-read)))
                    s)))))))
      (reify IReduce
        (-reduce [_ f init]
          (reduce-fd-async f init fd))))))

(defn fopen [{:keys [sync flags]
              :or {flags "r"}
              :as opts}
             filename]
  (let [fs (js/require "fs")]
    (if sync
      (.openSync fs filename flags)
      (js/Promise.
       (fn [return reject]
         (.open fs filename flags
                (fn [err fd]
                  (if err (reject err) (return fd)))))))))




(defn make-stream-reader [{:keys [sync]
                           :as opts}
                          source]
  (if sync
    (->> (fopen opts source)
         (make-fd-source opts))
    (-> (fopen opts source)
        (.then #(make-fd-source opts %)))))

(->>
 (fopen {:sync true} "/tmp/foo")
 (make-fd-source {:sync true})
 (into [])
 pr-str js/console.log)

(-> (fopen {:sync false} "/tmp/foo")
    (.then #(make-fd-source {:sync false} %))
    (.then #(reduce conj [] %))
    (.then
     (comp js/console.log pr-str)
     (comp js/console.error pr-str)))

(->> (make-stream-reader {:sync true} "/tmp/foo")
     (into []))

(-> (make-stream-reader {:sync false} "/tmp/foo")
    (.then #(reduce conj [] %))
    (.then
     (comp js/console.log pr-str)
     (comp js/console.error pr-str)))

(-> (make-stream-reader {:sync false} "/tmp/foo")
    (.then #(reduce ((parse/parser-xf {})
                     tree/push-handler)
                    (list (transient []))
                    %))
    (.then first)
    (.then
     (comp js/console.log pr-str)
     (comp js/console.error pr-str)))

(tree/event-tree
 (sequence
  (comp (parse/parser-xf {})
        push-handler/event-xf-ph)
  (make-stream-reader {:sync true} "/tmp/foo")))
