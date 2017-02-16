;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.jvm.pprint
  (:import
   (javax.xml.transform Transformer OutputKeys TransformerFactory)
   (java.io Writer StringReader StringWriter)
   (javax.xml.transform.stream StreamSource StreamResult)))

(defn ^Transformer indenting-transformer []
  (doto (-> (TransformerFactory/newInstance) .newTransformer)
    (.setOutputProperty OutputKeys/INDENT "yes")
    (.setOutputProperty OutputKeys/METHOD "xml")
    (.setOutputProperty "{http://xml.apache.org/xslt}indent-amount" "2")
    ;; print newline after preamble
    (.setOutputProperty OutputKeys/DOCTYPE_PUBLIC "yes")))

(defn indent-xml
  [xml-str ^Writer writer]
  (let [source (-> xml-str StringReader. StreamSource.)
        result (StreamResult. writer)]
    (.transform (indenting-transformer) source result)))


