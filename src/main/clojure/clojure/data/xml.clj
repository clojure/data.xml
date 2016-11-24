;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Functions to parse XML into lazy sequences and lazy trees and
  emit these as text."
      :author "Chris Houser"}

  clojure.data.xml
  
  (:require
   (clojure.data.xml
    [process :as process]
    [impl :refer [export-api]]
    [node :as node]
    [prxml :as prxml]
    [name :as name]
    [event :as event])
   (clojure.data.xml.jvm
    [pprint :refer
     [indent-xml]]
    [parse :refer
     [pull-seq string-source make-stream-reader]]
    [emit :refer
     [write-document string-writer]])
   
   [clojure.data.xml.tree :refer
    [event-tree flatten-elements]]))

(export-api node/element* node/element node/cdata node/xml-comment
            prxml/sexp-as-element prxml/sexps-as-fragment event/element-nss
            name/ns-uri name/uri-ns name/declare-ns name/alias-ns
            name/parse-qname name/qname-uri name/qname-local name/qname name/to-qname
            process/find-xmlns process/aggregate-xmlns)

(defn canonical-name
  "Put (q)name into canonical form as per ns-env"
  [n]
  (name/canonical-name (qname-uri n) (qname-local n) ""))

(defn event-seq
  "Parses the XML InputSource source using a pull-parser. Returns
   a lazy sequence of Event records.  Accepts key pairs
   with XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
   and xml-input-factory-props for more information.
   Defaults coalescing true and supporting-external-entities false.
   :include-node? can be a set of #{:start-element :end-element :characters :comment}"
  [source {:as props}]
  (let [props* (merge {:include-node? #{:element :characters}
                       :coalescing true
                       :supporting-external-entities false
                       :location-info true}
                      props)]
    (pull-seq (make-stream-reader props* source)
              props*
              nil)))

(defn parse
  "Parses the source, which can be an
   InputStream or Reader, and returns a lazy tree of Element records. Accepts key pairs
   with XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
   and xml-input-factory-props for more information. Defaults coalescing true."
  [source & opts]
  (event-tree (event-seq source opts)))

(defn parse-str
  "Parses the passed in string to Clojure data structures.  Accepts key pairs
   with XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
   and xml-input-factory-props for more information. Defaults coalescing true."
  [s & opts]
  (apply parse (string-source s) opts))

(defn emit
  "Prints the given Element tree as XML text to stream.
   Options:
    :encoding <str>          Character encoding to use
    :doctype  <str>          Document type (DOCTYPE) declaration to use"
  [e writer & {:as opts}]
  (write-document writer (flatten-elements [e]) opts))

(defn emit-str
  "Emits the Element to String and returns it.
   Options:
    :encoding <str>          Character encoding to use
    :doctype  <str>          Document type (DOCTYPE) declaration to use"
  ([e & opts]
   (let [sw (string-writer)]
     (apply emit e sw opts)
     (str sw))))

(defn indent
  "Emits the XML and indents the result.  WARNING: this is slow
   it will emit the XML and read it in again to indent it.  Intended for
   debugging/testing only."
  [e writer & opts]
  (indent-xml (apply emit-str e opts) writer))

(defn indent-str
  "Emits the XML and indents the result.  Writes the results to a String and returns it"
  [e & opts]
  (let [sw (string-writer)]
    (apply indent e sw opts)
    (str sw)))

;; TODO implement ~normalize to simulate an emit-parse roundtrip
;;      in terms of xmlns environment and keywords vs qnames
