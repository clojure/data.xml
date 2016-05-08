(ns clojure.data.xml
  (:require-macros
   [clojure.data.xml.impl :refer [export-api]])
  (:require
   [clojure.data.xml.name :as name]
   [clojure.data.xml.node :as node]
   [clojure.data.xml.protocols :refer [AsQName]]))

(export-api
 name/ns-uri name/uri-ns name/declare-ns
 name/parse-qname name/qname-uri name/qname-local name/qname name/to-qname
 node/element* node/element node/cdata node/xml-comment)

(defn canonical-name
  "Put (q)name into canonical form as per ns-env"
  [n]
  (name/canonical-name (qname-uri n) (qname-local n) ""))

;; TODO event-seq
;; TODO parse (use goog StringBuffer?)

(defn parse-str
  "Use DOMParser to parse xml string"
  ;; TODO detect browser specific parsererror tags
  ;;  see http://stackoverflow.com/questions/11563554/how-do-i-detect-xml-parsing-errors-when-using-javascripts-domparser-in-a-cross
  [s & {:keys [content-type on-error]
        :or {content-type "text/xml"
             on-error #(throw "XML parser error" {:doc % :input s})}}]
  (let [dom (. (js/DOMParser.)
               (parseFromString s content-type))
        doc (.-documentElement dom)]
    (if (= "parsererror" (.-nodeName doc))
      (on-error doc)
      doc)))

;; TODO emit (use goog StringBuffer?)

(defn emit-str
  "Use XMLSerializer to render an xml string"
  [e & {:keys []}]
  (. (js/XMLSerializer.)
     (serializeToString
      (node/coerce-to-dom e))))

(defn extend-nodes-as-qname! []
  (extend-protocol AsQName
    js/Element
    (qname-local [e] (.-localName e))
    (qname-uri [e] (.-namespaceURI e))
    js/Attr
    (qname-local [e] (.-localName e))
    (qname-uri [e] (.-namespaceURI e))))

(defn- as-node [n]
  (if (instance? js/Text n)
    (.-wholeText n) ;; .-data
    n))

(defn extend-dom-as-data! []
  (extend-type js/Element
    ILookup
    (-lookup [el k]
      (case k
        :tag (name/canonical-name (.-namespaceURI el)
                                  (.-localName el)
                                  "")
        :attrs (.-attributes el)
        :content (.-childNodes el)
        (throw "XML tag has no key" {:key k :el el}))))
  (extend-type js/NamedNodeMap
    ILookup
    (-lookup
      ([attrs attr]
       (if-let [i (.getNamedItemNS attrs (qname-uri attr) (qname-local attr))]
         (.-value i)
         nil))
      ([attrs attr not-found]
       (if-let [i (.getNamedItemNS attrs (qname-uri attr) (qname-local attr))]
         (.-value i)
         not-found))))
  (extend-type js/NodeList
    ISeqable
    (-seq [nl] (map as-node (array-seq nl)))
    ISequential
    ICounted
    (-count [nl] (.-length nl))
    IIndexed
    (-nth
      ([nl n]
       (as-node (aget nl n)))
      ([nl n nf]
       (if (and (<= 0 n) (< n (.-length nl)))
         (as-node (aget nl n))
         nf)))))

(comment

  (extend-dom-as-data!)
  (extend-nodes-as-qname!)

  (let [{tag :tag {a1 "{DAV:}a1" a2 "a2" :as attrs} :attrs [c1 c2 c3] :content}
        (parse-str "<xml xmlns:d=\"DAV:\" d:a1=\"dav-a1\" a2=\"normal a2\">
Fancy Content <br/> More
</xml>")]
    [tag a1 a2 c1 c2 c3])
  
  )
