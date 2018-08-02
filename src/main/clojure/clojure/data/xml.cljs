(ns clojure.data.xml
  (:require-macros
   [clojure.data.xml.impl :refer [export-api]])
  (:require
   [clojure.data.xml.name :as name]
   [clojure.data.xml.node :as node]
   [clojure.data.xml.js.dom :as dom]
   [clojure.data.xml.protocols :refer [AsQName]]))

(export-api
 name/parse-qname name/qname-uri name/qname-local name/qname name/as-qname name/uri-symbol name/symbol-uri
 node/element* node/element node/cdata node/xml-comment node/element?
 dom/extend-dom-as-data! dom/element-node dom/element-data)

;;;; ## TODO event-seq
;; This probably won't happen due to js' non-blocking semantics
;; Instead, for clojurescript, the machinery around event-seq could be implemented
;; as a transducer stack, such that a push-based source for parser events, like sax-js,
;; could be used.

;; TODO parse (use goog StringBuffer?)

(defn parse-str
  "Use DOMParser to parse xml string"
  ;; TODO detect browser specific parsererror tags
  ;;  see http://stackoverflow.com/questions/11563554/how-do-i-detect-xml-parsing-errors-when-using-javascripts-domparser-in-a-cross
  [s & {:keys [content-type on-error raw]
        :or {content-type "text/xml"
             on-error #(throw (ex-info "XML parser error" {:doc % :input s}))}}]
  (let [dom (. (js/DOMParser.)
               (parseFromString s content-type))
        doc (.-documentElement dom)]
    (cond (= "parsererror" (.-nodeName doc))
          (on-error doc)
          raw doc
          :else (element-data doc))))

;; TODO emit (use goog StringBuffer?)

(defn emit-str
  "Use XMLSerializer to render an xml string"
  [e & {:keys []}]
  (. (js/XMLSerializer.)
     (serializeToString
      (element-node e))))

