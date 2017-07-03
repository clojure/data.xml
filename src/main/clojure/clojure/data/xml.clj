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

(export-api node/element* node/element node/cdata node/xml-comment node/element?
            prxml/sexp-as-element prxml/sexps-as-fragment event/element-nss
            name/alias-uri name/parse-qname name/qname-uri
            name/qname-local name/qname name/as-qname name/uri-symbol name/symbol-uri
            name/uri-file name/print-uri-file-command!
            process/find-xmlns process/aggregate-xmlns)

(def ^:private ^:const parser-opts-arg
  '{:keys [include-node? location-info
           coalescing supporting-external-entities
           allocator namespace-aware replacing-entity-references
           validating reporter resolver support-dtd]
    :or {include-node? #{:element :characters}
         location-info true
         coalescing true
         supporting-external-entities false}})

(defn event-seq
  "Parses an XML input source into a lazy sequence of pull events.

Input source can be a java.io.InputStream or java.io.Reader

Options:

  :include-node? can be a subset of #{:element :characters :comment} default #{:element :characters}
  :location-info pass false to skip generating location meta data

See http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
for documentation on options:

  {:allocator                      XMLInputFactory/ALLOCATOR
   :coalescing                     XMLInputFactory/IS_COALESCING
   :namespace-aware                XMLInputFactory/IS_NAMESPACE_AWARE
   :replacing-entity-references    XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES
   :supporting-external-entities   XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES
   :validating                     XMLInputFactory/IS_VALIDATING
   :reporter                       XMLInputFactory/REPORTER
   :resolver                       XMLInputFactory/RESOLVER
   :support-dtd                    XMLInputFactory/SUPPORT_DTD}"
  {:arglists (list ['source parser-opts-arg])}
  [source opts]
  (let [props* (merge {:include-node? #{:element :characters}
                       :coalescing true
                       :supporting-external-entities false
                       :location-info true}
                      opts)]
    (pull-seq (make-stream-reader props* source)
              props*
              nil)))

(defn parse
  "Parses an XML input source into a a tree of Element records.
The element tree is realized lazily, so huge XML files can be streamed through a depth-first tree walk.

Input source can be a java.io.InputStream or java.io.Reader

Options:

  :include-node? can be a subset of #{:element :characters :comment} default #{:element :characters}
  :location-info pass false to skip generating location meta data

See http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
for documentation on options:

  {:allocator                      XMLInputFactory/ALLOCATOR
   :coalescing                     XMLInputFactory/IS_COALESCING
   :namespace-aware                XMLInputFactory/IS_NAMESPACE_AWARE
   :replacing-entity-references    XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES
   :supporting-external-entities   XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES
   :validating                     XMLInputFactory/IS_VALIDATING
   :reporter                       XMLInputFactory/REPORTER
   :resolver                       XMLInputFactory/RESOLVER
   :support-dtd                    XMLInputFactory/SUPPORT_DTD}"
  {:arglists (list ['source '& parser-opts-arg])}
  [source & {:as opts}]
  (event-tree (event-seq source opts)))

(defn parse-str
  "Parses an XML String into a a tree of Element records.

Options:

  :include-node? can be a subset of #{:element :characters :comment} default #{:element :characters}
  :location-info pass false to skip generating location meta data

See http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html
for documentation on options:

  {:allocator                      XMLInputFactory/ALLOCATOR
   :coalescing                     XMLInputFactory/IS_COALESCING
   :namespace-aware                XMLInputFactory/IS_NAMESPACE_AWARE
   :replacing-entity-references    XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES
   :supporting-external-entities   XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES
   :validating                     XMLInputFactory/IS_VALIDATING
   :reporter                       XMLInputFactory/REPORTER
   :resolver                       XMLInputFactory/RESOLVER
   :support-dtd                    XMLInputFactory/SUPPORT_DTD}"
  {:arglists (list ['string '& parser-opts-arg])}
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
