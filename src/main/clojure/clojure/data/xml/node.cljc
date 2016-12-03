;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.node
  "Data types for xml nodes: Element, CData and Comment"
  {:author "Herwig Hochleitner"}
  (:require [clojure.data.xml.name :refer [to-qname]]))

;; Parsed data format
;; Represents a node of an XML tree
(defrecord Element [tag attrs content]
  Object
  (toString [_]
    (let [qname (to-qname tag)]
      (apply str (concat ["<" qname]
                         (mapcat (fn [[n a]]
                                   [" " (to-qname n) "=" (pr-str a)])
                                 attrs)
                         (if (seq content)
                           (concat [">"] content ["</" qname ">"])
                           ["/>"]))))))
(defrecord CData [content])
(defrecord Comment [content])

#?(:cljs ;; http://dev.clojure.org/jira/browse/CLJS-1859
   (extend-type Element
     IEquiv
     (-equiv [el o]
       (js/cljs.core.equiv_map el o))))

(defn element*
  "Create an xml element from a content collection and optional metadata"
  ([tag attrs content meta]
   (Element. tag (or attrs {}) (remove nil? content) meta nil))
  ([tag attrs content]
   (Element. tag (or attrs {}) (remove nil? content))))

#?(:clj
   ;; Compiler macro for inlining the two constructors
   (alter-meta! #'element* assoc :inline
                (fn
                  ([tag attrs content meta]
                   `(Element. ~tag (or ~attrs {}) (remove nil? ~content) ~meta nil))
                  ([tag attrs content]
                   `(Element. ~tag (or ~attrs {}) (remove nil? ~content))))))

(defn element
  "Create an xml Element from content varargs"
  ([tag] (element* tag nil nil))
  ([tag attrs] (element* tag attrs nil))
  ([tag attrs & content] (element* tag attrs content)))

(defn cdata
  "Create a CData node"
  [content]
  (CData. content))

(defn xml-comment
  "Create a Comment node"
  [content]
  (Comment. content))
