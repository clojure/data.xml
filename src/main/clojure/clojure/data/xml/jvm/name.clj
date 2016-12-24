;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.jvm.name
  (:require (clojure.data.xml
             [protocols :refer [AsQName qname-uri qname-local]])
            [clojure.string :as str])
  (:import java.io.Writer
           (javax.xml.namespace NamespaceContext QName)
           (java.net URLEncoder URLDecoder)))

(extend-protocol AsQName
  QName
  (qname-local [qname] (.getLocalPart qname))
  (qname-uri   [qname] (.getNamespaceURI qname)))

(def ^QName parse-qname
  (memoize
   (fn [s]
     ;; TODO weakly memoize this?
     (QName/valueOf s))))

(extend-protocol AsQName
  String
  (qname-local [s]
    (.getLocalPart (parse-qname s)))
  (qname-uri [s]
    (.getNamespaceURI (parse-qname s))))

(definline decode-uri [ns]
  `(URLDecoder/decode ~ns "UTF-8"))

(definline encode-uri [uri]
  `(URLEncoder/encode ~uri "UTF-8"))
