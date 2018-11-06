(ns clojure.data.xml.js.dom
  (:require
   [clojure.data.xml.name :refer [qname-uri qname-local qname xmlns-uri]]
   [clojure.data.xml.node :as node]))

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
(def Attr (type (aget (.-attributes (element :e {:a "1"})) 0)))
(def CData (type (cdata "")))
(def Comment (type (xml-comment "")))

;; ## Coercions

;; ## -> DOM

(defn element-node
  "Coerce xml elements to dom nodes"
  [el]
  (cond
    (string? el) (text-node el)
    (instance? node/CData el) (cdata (:content el))
    (instance? node/Comment el) (xml-comment (:content el))
    (instance? Element el) el
    (instance? CData el) el
    (instance? Comment el) el
    ;; stupid xmldom, (some? (.-item el))
    #_(instance? NodeList el)
    (some? (.-item el)) el
    (instance? Text el) el
    (satisfies? ILookup el) (element* (:tag el)
                                      (:attrs el)
                                      (map element-node (:content el)))
    (satisfies? ISequential el) (node-list el)
    :else (throw (ex-info "Cannot coerce" {:form el}))))

;; ## -> DATA

(defn- dom-element-tag [el]
  (qname (.-namespaceURI el)
         (.-localName el)))

(defn- xmlns-attr? [a]
  (identical? xmlns-uri (.-namespaceURI a)))
(def remove-xmlns-attrs-xf (remove xmlns-attr?))
(def remove-xmlns-attrs (partial into {} remove-xmlns-attrs-xf))
(def filter-xmlns-attrs-xf (filter xmlns-attr?))
(def filter-xmlns-attrs (partial into {} filter-xmlns-attrs-xf))

(defn dom-element-attrs
  ([el] (dom-element-attrs remove-xmlns-attrs-xf el))
  ([xf el]
   (transduce
    xf
    (completing
     (fn [ta attr-node]
       (assoc! ta
               (dom-element-tag attr-node)
               (.-value attr-node)))
     persistent!)
    (transient {})
    (array-seq el))))

(declare element-data)

(defn- node-list-vec [nl]
  (into [] (map element-data) (array-seq nl)))

(defn- as-node [n]
  (if (instance? Text n)
    (.-nodeValue n) ;; .-data
    n))

(defn element-data
  "Coerce xml elements to element maps / content vectors"
  [el]
  (cond
    (instance? Comment el)
    (node/xml-comment (.-data el))
    (instance? CData el)
    (node/cdata (.-data el))
    (instance? Text el)
    (.-nodeValue el)
    (instance? Element el)
    (node/element* (dom-element-tag el)
                   (dom-element-attrs (.-attributes el))
                   (node-list-vec (.-childNodes el))
                   {:clojure.data.xml/nss (dom-element-attrs
                                           filter-xmlns-attrs-xf
                                           (.-attributes el))})
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
    IMap
    IMeta
    (-meta [el]
      {:clojure.data.xml/nss (filter-xmlns-attrs
                              (.-attributes el))})
    ILookup
    (-lookup
      ([el k]
       (case k
         :tag (dom-element-tag el)
         :attrs (.-attributes el)
         :content (.-childNodes el)
         (throw (ex-info "XML tag has no key" {:key k :el el}))))
      ([el k nf]
       #_(println "Element" k "=>" (case k
                                     :tag (dom-element-tag el)
                                     :attrs (.-attributes el)
                                     :content (.-childNodes el)
                                     nf))
       (case k
         :tag (dom-element-tag el)
         :attrs (remove-xmlns-attrs (.-attributes el))
         :content (.-childNodes el)
         nf)))
    ICounted
    (-count [nm] 3)
    IEquiv
    (-equiv [el0 el1]
      (if false #_(instance? Element el1)
          (do
            ;; we can't use .isEqualNode, since that has bugs with namespaces
            (.log js/console el0 el1)
            (println 'isEqualNode (.isEqualNode el0 el1))
            (.isEqualNode el0 el1))
          (and (= (:tag el0) (:tag el1))
               (= (:attrs el0) (:attrs el1))
               (= (:content el0) (:content el1))))))
  (extend-type NamedNodeMap
    IMap
    ISeqable
    (-seq [nm] (array-seq nm))
    ILookup
    (-lookup
      ([attrs attr]
       (if-let [i (.getNamedItemNS attrs (qname-uri attr) (qname-local attr))]
         (.-value i)
         nil))
      ([attrs attr not-found]
       #_(println "Attrs" attr "=>" (if-let [i (.getNamedItemNS attrs (qname-uri attr) (qname-local attr))]
                                      (.-value i)
                                      not-found))
       (if-let [i (.getNamedItemNS attrs (qname-uri attr) (qname-local attr))]
         (.-value i)
         not-found)))
    ICounted
    (-count [nm] (reduce (fn [acc attr]
                           (if (xmlns-attr? attr)
                             acc
                             (inc acc)))
                         0 nm))
    IKVReduce
    (-kv-reduce [nm f init]
      (reduce (fn [acc attr]
                (if (xmlns-attr? attr)
                  acc
                  (f acc (dom-element-tag attr) (.-value attr))))
              init nm))
    IEquiv
    (-equiv [nm0 nm1]
      #_(println "NamedNodeMap.-equiv" (identical? nm0 nm1) (count nm0) (count nm1))
      (or (identical? nm0 nm1)
          (and (identical? (count nm0) (count nm1))
               (reduce-kv (fn [_ qn v]
                            #_(println "=" v 'qn qn '(get nm1 qn "") (get nm1 qn ""))
                            (or (identical? v (get nm1 qn ""))
                                (reduced false)))
                          true nm0)))))
  (extend-type NodeList
                                        ;specify! (.. (node-list []) -constructor -prototype)
    ISeqable
    (-seq [nl] (seq (map as-node (array-seq nl))))
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
      #_(println "NodeList.-equiv")
      (or (identical? nl0 nl1)
          (and (identical? (count nl0) (count nl1))
               (reduce (fn [_ n]
                         (or (= (nth nl0 n) (nth nl1 n))
                             (reduced false)))
                       true (range (count nl0)))))))
  (extend-type Text
    IEquiv
    (-equiv [t0 t1]
      (identical? (.-nodeValue t0)
                  (if (instance? Text t1)
                    (.-nodeValue t1)
                    t1))))
  (extend-type Attr
    ISeqable
    (-seq [attr] (array-seq #js[(key attr) (key attr)]))
    IMapEntry
    (-key [attr] (dom-element-tag attr))
    (-val [attr] (.-value attr))
    ISequential
    ICounted
    (-count [_] 2)
    IIndexed
    (-nth
      ([attr n] (case n
                  0 (key attr)
                  1 (val attr)))
      ([attr n nf]
       (case n
         0 (dom-element-tag attr)
         1 (.-value attr)
         nf))))
  {'Text Text
   'Element Element
   'NamedNodeMap NamedNodeMap
   'NodeList NodeList})
