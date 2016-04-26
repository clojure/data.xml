(ns clojure.data.xml.node
  (:require
   [clojure.data.xml.name :refer [qname-uri qname-local]]))

(def doc
  (.. (js/DOMParser.)
      (parseFromString "<d />" "text/xml")))

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
                                  (.createTextNode doc n)
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
