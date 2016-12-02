(ns clojure.data.xml.node
  (:require
   [clojure.data.xml.name :refer [qname-uri qname-local canonical-name]]))

(def doc
  (.. (js/DOMParser.)
      (parseFromString "<d />" "text/xml")))

(defn text-node
  "Create a Text node"
  [s]
  (.createTextNode doc s))

(defn element*
  "Create an xml element from a content collection and optional metadata"
  ([tag attrs content meta]
   (let [el (element* tag attrs content)]
     (specify! el
       IMeta
       (-meta [_] meta)
       IWithMeta
       (-with-meta [_ meta]
         (specify el
           IMeta
           (-meta [_] meta)
           IWithMeta
           (-with-meta [_ meta]
             (-with-meta el meta)))))
     el))
  ([tag attrs content]
   (let [el (.createElementNS doc (qname-uri tag) (qname-local tag))]
     (reduce-kv (fn [_ k v]
                  (let [uri (qname-uri k)]
                    (if (= uri "http://www.w3.org/2000/xmlns/")
                      (.setAttribute el (str "xmlns:" (qname-local k)) v)
                      (.setAttributeNS el uri (qname-local k) v))))
                nil attrs)
     (reduce (fn [_ n]
               (.appendChild el (if (string? n)
                                  (text-node n)
                                  n)))
             nil content)
     el)))

(defn element
  "Create an xml Element from content varargs"
  ([tag] (element* tag nil nil))
  ([tag attrs] (element* tag attrs nil))
  ([tag attrs & content] (element* tag attrs content)))

(defn cdata
  "Create a CData node"
  [content]
  (.createCDATASection doc content))

(defn xml-comment
  "Create a Comment node"
  [content]
  (.createComment doc content))

(declare element-node)

(defn node-list
  "Create a NodeList"
  [elements]
  (let [f (.createDocumentFragment doc)]
    (doseq [el elements]
      (.appendChild f (element-node el)))
    (.-childNodes f)))

;; ## Types

;; we get these from reflection, to only depend only on js/DOMParser and js/XMLSerializer
;; these can easily be provided in nashorn, ...

(def Text (type (text-node "")))
(def Element (type (element :e)))
(def NamedNodeMap (type (.-attributes (element :e))))
(def NodeList (type (node-list [])))

;; ## Coercions

;; ## -> DOM

(defn element-node
  "Coerce xml elements to dom nodes"
  [el]
  (cond
    (string? el) (text-node el)
    (instance? Element el) el
    ;; stupid xmldom, (some? (.-item el))
    (instance? NodeList el) el
    (instance? Text el) el
    (satisfies? ILookup el) (element* (:tag el)
                                      (:attrs el)
                                      (map element-node (:content el)))
    (satisfies? ISequential el) (node-list el)
    :else (throw (ex-info "Cannot coerce" {:form el}))))

;; ## -> DATA

(defn- dom-element-tag [el]
  (canonical-name (.-namespaceURI el)
                  (.-localName el)
                  ""))

(defn dom-element-attrs [el]
  (persistent!
   (reduce (fn [ta attr-node]
             (assoc! ta
                     (dom-element-tag attr-node)
                     (.-value attr-node)))
           (transient {})
           (array-seq el))))

(declare element-data)

(defn- node-list-vec [nl]
  (mapv element-data (array-seq nl)))

(defn- as-node [n]
  (if (instance? Text n)
    (.-nodeValue n) ;; .-data
    n))

(defn element-data
  "Coerce xml elements to element maps / content vectors"
  [el]
  (cond
    (instance? Text el)
    (.-nodeValue el)
    (instance? Element el)
    {:tag (dom-element-tag el)
     :attrs (dom-element-attrs (.-attributes el))
     :content (node-list-vec (.-childNodes el))}
    ;;(instance? NamedNodeMap el)
    (.-getNamedItemNS el)
    (dom-element-attrs el)
    (instance? NodeList el) (node-list-vec el)
    (string? el) el
    (satisfies? ILookup el) el
    (satisfies? ISequential el) el
    :else (throw (ex-info "Element cannot be converted to data" {:element el}))))

(defn extend-dom-as-data! []
  (extend-type Element
    ILookup
    (-lookup [el k]
      (case k
        :tag (dom-element-tag el)
        :attrs (.-attributes el)
        :content (.-childNodes el)
        (throw "XML tag has no key" {:key k :el el})))
    IEquiv
    (-equiv [el0 el1]
      (.isEqualNode el0 el1)))
  (extend-type NamedNodeMap
    ILookup
    (-lookup
      ([attrs attr]
       (if-let [i (.getNamedItemNS attrs (qname-uri attr) (qname-local attr))]
         (.-value i)
         nil))
      ([attrs attr not-found]
       (if-let [i (.getNamedItemNS attrs (qname-uri attr) (qname-local attr))]
         (.-value i)
         not-found)))
    ICounted
    (-count [nm] (alength nm))
    IKVReduce
    (-kv-reduce [nm f init]
      (reduce (fn [acc attr]
                (f acc (dom-element-tag attr) (.-value attr)))
              init nm))
    IIndexed
    (-nth
      ([nm n] (.-value (aget nm n)))
      ([nm n nf] (if (and (<= 0 n) (< n (alength nm)))
                   (.-value (aget nm n))
                   nf)))
    IEquiv
    (-equiv [nm0 nm1]
      (and (identical? (count nm0) (count nm1))
           (reduce-kv (fn [_ qn v]
                        (or (identical? v (get nm1 qn ""))
                            (reduced false)))
                      true nm0))))
  (extend-type NodeList
                                        ;specify! (.. (node-list []) -constructor -prototype)
    ISeqable
    (-seq [nl] (map as-node (array-seq nl)))
    ISequential
    ICounted
    (-count [nl] (alength nl))
    IIndexed
    (-nth
      ([nl n]
       (as-node (aget nl n)))
      ([nl n nf]
       (if (and (<= 0 n) (< n (alength nl)))
         (as-node (aget nl n))
         nf)))
    IEquiv
    (-equiv [nl0 nl1]
      (and (identical? (count nl0) (count nl1))
           (reduce (fn [_ n]
                     (or (= (nth nl0 n) (nth nl1 n))
                         (reduced false)))
                   true (range (count nl0))))))
  (extend-type Text
    IEquiv
    (-equiv [t0 t1]
      (identical? (.-nodeValue t0) (.-nodeValue t1))))
  {'Text Text
   'Element Element
   'NamedNodeMap NamedNodeMap
   'NodeList NodeList})
