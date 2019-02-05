(ns clojure.data.xml.js.parse
  (:require
   [goog.object :as gob]
   [clojure.data.xml.sax-js :as sax]
   [clojure.data.xml.protocols :as p]
   [clojure.data.xml.core :as core]
   [clojure.data.xml.name :as name]
   [clojure.data.xml.tree :as tree]
   [clojure.data.xml.event :as event]
   [clojure.data.xml.push-handler :as push-handler]
   [clojure.string :as str]))

(defn qname [qn]
  (name/qname (gob/get qn "uri")
              (gob/get qn "local")
              (gob/get qn "prefix")))

(defn attributes [ao cont]
  (name/separate-xmlns
   (core/kv-from-coll
    (map #(gob/get ao %))
    (core/juxt-xf qname #(gob/get % "value"))
    (js-keys ao))
   cont))

(defn parser [{:keys [strict trim normalize
                      lowercase xmlns position
                      strict-entities]
               :or {strict true
                    trim false
                    normalize false
                    lowercase true
                    position true
                    strict-entities false
                    xmlns true}}]
  (sax/parser strict #js {"trim" trim
                          "normalize" normalize
                          "lowercase" lowercase
                          "xmlns" xmlns
                          "position" position
                          "strictEntities" strict-entities}))

(defn parser-xf [opts]
  (fn [push-handler]
    (let [p (parser opts)
          actions (volatile! [])
          ph-drive (fn [state action & args]
                     (if (reduced? state)
                       (do (vswap! actions conj
                                   #(apply action push-handler % args))
                           state)
                       (let [res (apply action push-handler state args)]
                         (if (reduced? res)
                           (p/end-event push-handler @res)
                           res))))
          state (volatile! nil)]
      ;; OPEN TAG
      (set! (.-onopentag p)
            #(attributes
              (.-attributes %)
              (fn [attrs nss]
                (vswap! state ph-drive p/start-element-event (qname %) attrs nss nil))))

      ;; CLOSE TAG
      (set! (.-onclosetag p)
            #(vswap! state ph-drive p/end-element-event))

      ;; GET TEXT
      (set! (.-ontext p)
            #(vswap! state ph-drive p/chars-event %))

      ;; CDATA HANDLING
      (set! (.-oncdata p)
            #(vswap! state ph-drive p/c-data-event %))

      ;; COMMENTS
      (set! (.-oncomment p)
            #(vswap! state ph-drive p/comment-event %))

      ;; END PARSING
      (set! (.-onend p)
            #(vswap! state ph-drive p/end-event))

      ;; ERROR
      (set! (.-onerror p)
            #(vswap! state ph-drive p/error-event %))
      (fn
        ([s]
         (vreset! state s)
         (.close p)
         @state)
        ([s string]
         (vreset! state s)
         (.write p string)
         @state)))))

(defmulti make-stream-source
  (fn [{:keys [impl async]} source]
    [(type source) impl async]))

(defmethod make-stream-source :default
  [opts source]
  (throw (ex-info (str "No source method for " (type source))
                  {:source source :opts opts})))

(defmethod make-stream-source [js/String :xhr true]
  [opts uri]
  (js/Promise.
   (fn [resolve reject]
     (doto (js/XMLHttpRequest.)
       (.open "GET" uri)
       (.send)
       (.addEventListener "load" (fn [e] (resolve (cons (.. e -target -response) nil))))
       (.addEventListener "error" (fn [e] (reject e)))
       (.addEventListener "abort" (fn [e] (reject [:abort e])))
       (.addEventListener "progress" (fn [e]))))))

(defmethod make-stream-source [js/String :fetch true]
  [opts uri]
  (reify IReduce
    (-reduce [_ rf init]
      (.. (js/fetch uri)
          (then (fn [resp]
                  (let [rdr (.. resp -body getReader)
                        decoder (js/TextDecoder. "utf-8")]
                    (.. ((fn read-next [state]
                           (if (reduced? state)
                             (rf @state)
                             (.. rdr read
                                 (then (fn [chunk]
                                         (if (.-done chunk)
                                           (core/unwrap-reduced state)
                                           (read-next (rf state (.decode decoder (.-value chunk))))))))))
                         init)
                        (finally (fn [_] (.releaseLock rdr)))))))))))

(defmethod make-stream-source [js/String :jdk false]
  [opts uri]
  (lazy-seq
   (let [rdr (js/java.io.InputStreamReader.
              (.openStream (js/java.net.URL. uri))
              "UTF-8")
         CharArray (js/Java.type "char[]")
         arr (new CharArray 1024)]
     ((fn read-next []
        (let [cnt (.read rdr arr)]
          (when-not (neg? cnt)
            (cons (js/java.lang.String. arr 0 cnt)
                  (lazy-seq (read-next))))))))))

(defn pthen [p f]
  (if (instance? js/Promise p)
    (.then p #(f %))
    (f p)))

(defn promise-xf [xf]
  (fn
    ([] (xf))
    ([s] (pthen s xf))
    ([s v] (pthen s (fn [s] (pthen v (fn [v] (xf s v))))))))

(comment
  (require 'clojure.data.xml.js.parse :reload)

  (.. (js/fetch "https://raw.githubusercontent.com/Ekryd/sortpom/master/pom.xml")
      (then console.log))

  (-> (transduce
       (comp promise-xf
             (parser-xf {})
             push-handler/event-xf-ph
             (drop-while #(and (instance? event/CharsEvent %)
                               (str/blank? (:string x)))))
       conj []
       (make-stream-source {:impl :fetch :async true}
                           "https://raw.githubusercontent.com/Ekryd/sortpom/master/pom.xml"))
      (pthen tree/event-tree)
      (pthen js/console.log))

  (-> "https://raw.githubusercontent.com/Ekryd/sortpom/master/pom.xml"
      (->> (make-stream-source {:impl :fetch :async true})
           (transduce (comp promise-xf
                            (parser-xf {}))
                      tree/push-handler
                      (list (transient []))))
      (pthen js/console.log))

  (-> "file:///home/herwig/checkout/data.xml/pom.xml"
      (->> (make-stream-source {:impl :jdk :async false})
           (sequence (comp (parser-xf {})
                           push-handler/event-xf-ph)))
      tree/event-tree)

  (first
   (transduce
    (parser-xf {})
    tree/push-handler
    (list (transient []))
    ["<roo"
     "t xmlns:a=\"GOO:\" foo=\"bar\" "
     "a:roo=\"ra\">lalala<![CDATA[foo GAARR "
     "bar]]><a:gaga/><!--  la  la --></root"
     ">"]))

  (-> (tree/event-tree
       (sequence
        (comp (parser-xf {})
              push-handler/event-xf-ph)
        ["<roo"
         "t xmlns:a=\"GOO:\" foo=\"bar\" "
         "a:roo=\"ra\">lalala<![CDATA[foo GAARR "
         "bar]]><a:gaga/><!--  la  la --></root"
         ">"])))

  )
