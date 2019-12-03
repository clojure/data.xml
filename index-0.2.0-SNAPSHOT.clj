{:namespaces
 ({:doc
   "Functions to parse XML into lazy sequences and lazy trees and\nemit these as text.",
   :author "Chris Houser",
   :name "clojure.data.xml",
   :wiki-url "https://clojure.github.io/data.xml/index.html",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj"}
  {:doc "Data type for xml pull events",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj"}
  {:doc "Shared private code for data.xml namespaces",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.impl",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.impl",
   :source-url
   "https://github.com/clojure/data.xml/blob/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj"}
  {:doc "JVM implementation of the emitter details",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.jvm.emit",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.jvm.emit",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/jvm/emit.clj"}
  {:doc nil,
   :name "clojure.data.xml.jvm.parse",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.jvm.parse",
   :source-url
   "https://github.com/clojure/data.xml/blob/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/jvm/parse.clj"}
  {:doc nil,
   :name "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.name",
   :source-url nil}
  {:doc "Data types for xml nodes: Element, CData and Comment",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.node",
   :source-url nil}
  {:doc nil,
   :name "clojure.data.xml.process",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.process",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj"}
  {:doc nil,
   :name "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.protocols",
   :source-url nil}
  {:doc nil,
   :name "clojure.data.xml.prxml",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.prxml",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj"}
  {:doc
   "Provides a bidirectional mapping for keeping track of prefix->uri mappings in xml namespaces.\n\nThis has the semantics of a basic key -> multiple values map + two special features, both of which are dictated by the xml standard:\n\n- instead of a special dissoc, there is assoc to empty string or nil\n- there are two fixed, unique mappings:\n  - \"xml\" <-> [\"http://www.w3.org/2000/xmlns/\"]\n  - \"xmlns\" <-> [\"http://www.w3.org/XML/1998/namespace\"]",
   :name "clojure.data.xml.pu-map",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.pu-map",
   :source-url nil}
  {:doc nil,
   :name "clojure.data.xml.tree",
   :wiki-url
   "https://clojure.github.io/data.xml/index.html#clojure.data.xml.tree",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj"}),
 :vars
 ({:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj",
   :name "aggregate-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj#L41",
   :line 41,
   :var-type "function",
   :arglists ([xml]),
   :doc "Put all occurring xmlns into the root",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/aggregate-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "alias-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L100",
   :line 100,
   :var-type "function",
   :arglists ([& {:as alias-nss}]),
   :doc
   "Define a Clojure namespace aliases for xmlns uris.\n\nThis sets up the current namespace for reading qnames denoted with\nClojure's ::alias/keywords reader feature.\n\n\n## Example\n(alias-uri :D \"DAV:\")\n                         ; similar in effect to\n;; (require '[xmlns.DAV%3A :as D])\n                         ; but required namespace is auto-created\n                         ; henceforth, shorthand keywords can be used\n{:tag ::D/propfind}\n                         ; ::D/propfind will be expanded to :xmlns.DAV%3A/propfind\n                         ; in the current namespace by the reader\n\n## Clojurescript support\nCurrently, namespaces can't be auto-created in Clojurescript.\nDummy files for aliased uris have to exist. Have a look at `uri-file` and `print-uri-file-command!` to create those.",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/alias-uri"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "cdata",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L207",
   :line 207,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a CData node",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/cdata"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L201",
   :line 201,
   :var-type "function",
   :arglists ([tag] [tag attrs] [tag attrs & content]),
   :doc "Create an xml Element from content varargs",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element*",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L185",
   :line 185,
   :var-type "function",
   :arglists ([tag attrs content meta] [tag attrs content]),
   :doc
   "Create an xml element from a content collection and optional metadata",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/element*"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "element-nss",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L27",
   :line 27,
   :var-type "function",
   :arglists ([{:keys [attrs], :as element}]),
   :doc "Get xmlns environment from element",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/element-nss"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj",
   :name "emit",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj#L135",
   :line 135,
   :var-type "function",
   :arglists ([e writer & {:as opts}]),
   :doc
   "Prints the given Element tree as XML text to stream.\nOptions:\n :encoding <str>          Character encoding to use\n :doctype  <str>          Document type (DOCTYPE) declaration to use",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/emit"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj",
   :name "emit-str",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj#L143",
   :line 143,
   :var-type "function",
   :arglists ([e & opts]),
   :doc
   "Emits the Element to String and returns it.\nOptions:\n :encoding <str>          Character encoding to use\n :doctype  <str>          Document type (DOCTYPE) declaration to use",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/emit-str"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj",
   :name "event-seq",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj#L51",
   :line 51,
   :var-type "function",
   :arglists
   ([source
     {:keys
      [include-node?
       location-info
       coalescing
       supporting-external-entities
       allocator
       namespace-aware
       replacing-entity-references
       validating
       reporter
       resolver
       support-dtd],
      :or
      {include-node? #{:characters :element},
       location-info true,
       coalescing true,
       supporting-external-entities false}}]),
   :doc
   "Parses an XML input source into a lazy sequence of pull events.\n\nInput source can be a java.io.InputStream or java.io.Reader\n\nOptions:\n\n  :include-node? can be a subset of #{:element :characters :comment} default #{:element :characters}\n  :location-info pass false to skip generating location meta data\n\nSee http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nfor documentation on options:\n\n  {:allocator                      XMLInputFactory/ALLOCATOR\n   :coalescing                     XMLInputFactory/IS_COALESCING\n   :namespace-aware                XMLInputFactory/IS_NAMESPACE_AWARE\n   :replacing-entity-references    XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES\n   :supporting-external-entities   XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES\n   :validating                     XMLInputFactory/IS_VALIDATING\n   :reporter                       XMLInputFactory/REPORTER\n   :resolver                       XMLInputFactory/RESOLVER\n   :support-dtd                    XMLInputFactory/SUPPORT_DTD}",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/event-seq"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj",
   :name "find-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj#L34",
   :line 34,
   :var-type "function",
   :arglists ([xml]),
   :doc "Find all xmlns occuring in a root",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/find-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj",
   :name "indent",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj#L153",
   :line 153,
   :var-type "function",
   :arglists ([e writer & opts]),
   :doc
   "Emits the XML and indents the result.  WARNING: this is slow\nit will emit the XML and read it in again to indent it.  Intended for\ndebugging/testing only.",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/indent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj",
   :name "indent-str",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj#L160",
   :line 160,
   :var-type "function",
   :arglists ([e & opts]),
   :doc
   "Emits the XML and indents the result.  Writes the results to a String and returns it",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/indent-str"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj",
   :name "parse",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj#L84",
   :line 84,
   :var-type "function",
   :arglists
   ([source
     &
     {:keys
      [include-node?
       location-info
       coalescing
       supporting-external-entities
       allocator
       namespace-aware
       replacing-entity-references
       validating
       reporter
       resolver
       support-dtd],
      :or
      {include-node? #{:characters :element},
       location-info true,
       coalescing true,
       supporting-external-entities false}}]),
   :doc
   "Parses an XML input source into a a tree of Element records.\nThe element tree is realized lazily, so huge XML files can be streamed through a depth-first tree walk.\n\nInput source can be a java.io.InputStream or java.io.Reader\n\nOptions:\n\n  :include-node? can be a subset of #{:element :characters :comment} default #{:element :characters}\n  :location-info pass false to skip generating location meta data\n\nSee http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nfor documentation on options:\n\n  {:allocator                      XMLInputFactory/ALLOCATOR\n   :coalescing                     XMLInputFactory/IS_COALESCING\n   :namespace-aware                XMLInputFactory/IS_NAMESPACE_AWARE\n   :replacing-entity-references    XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES\n   :supporting-external-entities   XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES\n   :validating                     XMLInputFactory/IS_VALIDATING\n   :reporter                       XMLInputFactory/REPORTER\n   :resolver                       XMLInputFactory/RESOLVER\n   :support-dtd                    XMLInputFactory/SUPPORT_DTD}",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/parse"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj",
   :name "parse-str",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5b13e2b2e8cb5445ac6c5f6926f3591b10eba66d/src/main/clojure/clojure/data/xml.clj#L111",
   :line 111,
   :var-type "function",
   :arglists
   ([string
     &
     {:keys
      [include-node?
       location-info
       coalescing
       supporting-external-entities
       allocator
       namespace-aware
       replacing-entity-references
       validating
       reporter
       resolver
       support-dtd],
      :or
      {include-node? #{:characters :element},
       location-info true,
       coalescing true,
       supporting-external-entities false}}]),
   :doc
   "Parses an XML String into a a tree of Element records.\n\nOptions:\n\n  :include-node? can be a subset of #{:element :characters :comment} default #{:element :characters}\n  :location-info pass false to skip generating location meta data\n\nSee http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nfor documentation on options:\n\n  {:allocator                      XMLInputFactory/ALLOCATOR\n   :coalescing                     XMLInputFactory/IS_COALESCING\n   :namespace-aware                XMLInputFactory/IS_NAMESPACE_AWARE\n   :replacing-entity-references    XMLInputFactory/IS_REPLACING_ENTITY_REFERENCES\n   :supporting-external-entities   XMLInputFactory/IS_SUPPORTING_EXTERNAL_ENTITIES\n   :validating                     XMLInputFactory/IS_VALIDATING\n   :reporter                       XMLInputFactory/REPORTER\n   :resolver                       XMLInputFactory/RESOLVER\n   :support-dtd                    XMLInputFactory/SUPPORT_DTD}",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/parse-str"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "print-uri-file-command!",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L94",
   :line 94,
   :var-type "function",
   :arglists ([uri]),
   :doc
   "Shell command to create a dummy file for xmlns. Execute from a source root.",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/print-uri-file-command!"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-local",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L44",
   :line 44,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the name for this qname",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/qname-local"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L39",
   :line 39,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the namespace uri for this qname",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/qname-uri"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :name "sexp-as-element",
   :file "src/main/clojure/clojure/data/xml/prxml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L66",
   :line 66,
   :var-type "function",
   :arglists ([sexp]),
   :doc "Convert a single sexp into an Element",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/sexp-as-element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :name "sexps-as-fragment",
   :file "src/main/clojure/clojure/data/xml/prxml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L50",
   :line 50,
   :var-type "function",
   :arglists ([] [sexp] [sexp & sexps]),
   :doc
   "Convert a compact prxml/hiccup-style data structure into the more formal\ntag/attrs/content format. A seq of elements will be returned, which may\nnot be suitable for immediate use as there is no root element. See also\nsexp-as-element.\n\nThe format is [:tag-name attr-map? content*]. Each vector opens a new tag;\nseqs do not open new tags, and are just used for inserting groups of elements\ninto the parent tag. A bare keyword not in a vector creates an empty element.\n\nTo provide XML conversion for your own data types, extend the AsElements\nprotocol to them.",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/sexps-as-fragment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "uri-file",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L87",
   :line 87,
   :var-type "function",
   :arglists ([uri]),
   :doc "Dummy file name for :require'ing xmlns uri",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/uri-file"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "xml-comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L212",
   :line 212,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a Comment node",
   :namespace "clojure.data.xml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml/xml-comment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "->CDataEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L37",
   :line 37,
   :var-type "function",
   :arglists ([str]),
   :doc
   "Positional factory function for class clojure.data.xml.event.CDataEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->CDataEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "->CharsEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L36",
   :line 36,
   :var-type "function",
   :arglists ([str]),
   :doc
   "Positional factory function for class clojure.data.xml.event.CharsEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->CharsEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "->CommentEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L38",
   :line 38,
   :var-type "function",
   :arglists ([str]),
   :doc
   "Positional factory function for class clojure.data.xml.event.CommentEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->CommentEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "->EmptyElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L35",
   :line 35,
   :var-type "function",
   :arglists ([tag attrs nss location-info]),
   :doc
   "Positional factory function for class clojure.data.xml.event.EmptyElementEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->EmptyElementEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "->QNameEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L39",
   :line 39,
   :var-type "function",
   :arglists ([qn]),
   :doc
   "Positional factory function for class clojure.data.xml.event.QNameEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->QNameEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "->StartElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L34",
   :line 34,
   :var-type "function",
   :arglists ([tag attrs nss location-info]),
   :doc
   "Positional factory function for class clojure.data.xml.event.StartElementEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->StartElementEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "element-nss",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L27",
   :line 27,
   :var-type "function",
   :arglists ([{:keys [attrs], :as element}]),
   :doc "Get xmlns environment from element",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/element-nss"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->CDataEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L37",
   :line 37,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.CDataEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->CDataEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->CharsEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L36",
   :line 36,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.CharsEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->CharsEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->CommentEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L38",
   :line 38,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.CommentEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->CommentEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->EmptyElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L35",
   :line 35,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.EmptyElementEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->EmptyElementEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->QNameEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L39",
   :line 39,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.QNameEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->QNameEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->StartElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/event.clj#L34",
   :line 34,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.StartElementEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->StartElementEvent"}
  {:name "CDataEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/CDataEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "CharsEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/CharsEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "CommentEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/CommentEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "EmptyElementEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/EmptyElementEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "EndElementEvent",
   :var-type "type",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/EndElementEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "QNameEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/QNameEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "StartElementEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.event/StartElementEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj",
   :name "compile-if",
   :file "src/main/clojure/clojure/data/xml/impl.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj#L54",
   :line 54,
   :var-type "macro",
   :arglists ([exp then else]),
   :doc
   "Evaluate `exp` and if it returns logical true and doesn't error, expand to\n`then`.  Else expand to `else`.\n\nsee clojure.core.reducers",
   :namespace "clojure.data.xml.impl",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.impl/compile-if"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj",
   :name "export-api",
   :file "src/main/clojure/clojure/data/xml/impl.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj#L27",
   :line 27,
   :var-type "macro",
   :arglists ([& names]),
   :doc
   "This creates vars, that take their (local) name, value and metadata from another var",
   :namespace "clojure.data.xml.impl",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.impl/export-api"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj",
   :name "extend-protocol-fns",
   :file "src/main/clojure/clojure/data/xml/impl.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj#L42",
   :line 42,
   :var-type "macro",
   :arglists ([proto & types+mmaps]),
   :doc
   "Helper to many types to a protocol with a method map, similar to extend",
   :namespace "clojure.data.xml.impl",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.impl/extend-protocol-fns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj",
   :name "static-case",
   :file "src/main/clojure/clojure/data/xml/impl.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/impl.clj#L32",
   :line 32,
   :var-type "macro",
   :arglists ([val & cases]),
   :doc "Variant of case where keys are evaluated at compile-time",
   :namespace "clojure.data.xml.impl",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.impl/static-case"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/jvm/emit.clj",
   :name "write-document",
   :file "src/main/clojure/clojure/data/xml/jvm/emit.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/jvm/emit.clj#L196",
   :line 196,
   :var-type "function",
   :arglists ([swriter events opts]),
   :doc
   "Writes the given event seq as XML text to writer.\nOptions:\n :encoding <str>          Character encoding to use\n :doctype  <str>          Document type (DOCTYPE) declaration to use",
   :namespace "clojure.data.xml.jvm.emit",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.jvm.emit/write-document"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/jvm/parse.clj",
   :name "pull-seq",
   :file "src/main/clojure/clojure/data/xml/jvm/parse.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/ef8d0c816fa8d9c02d759de990bdb2e0042c0d2a/src/main/clojure/clojure/data/xml/jvm/parse.clj#L69",
   :line 69,
   :var-type "function",
   :arglists
   ([sreader
     {:keys [include-node? location-info skip-whitespace], :as opts}
     ns-envs]),
   :doc
   "Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to\nbe triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete.",
   :namespace "clojure.data.xml.jvm.parse",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.jvm.parse/pull-seq"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "*gen-prefix-counter*",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L189",
   :dynamic true,
   :line 189,
   :var-type "var",
   :arglists nil,
   :doc "Thread local counter for a single document",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/*gen-prefix-counter*"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "alias-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L100",
   :line 100,
   :var-type "function",
   :arglists ([& {:as alias-nss}]),
   :doc
   "Define a Clojure namespace aliases for xmlns uris.\n\nThis sets up the current namespace for reading qnames denoted with\nClojure's ::alias/keywords reader feature.\n\n\n## Example\n(alias-uri :D \"DAV:\")\n                         ; similar in effect to\n;; (require '[xmlns.DAV%3A :as D])\n                         ; but required namespace is auto-created\n                         ; henceforth, shorthand keywords can be used\n{:tag ::D/propfind}\n                         ; ::D/propfind will be expanded to :xmlns.DAV%3A/propfind\n                         ; in the current namespace by the reader\n\n## Clojurescript support\nCurrently, namespaces can't be auto-created in Clojurescript.\nDummy files for aliased uris have to exist. Have a look at `uri-file` and `print-uri-file-command!` to create those.",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/alias-uri"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "gen-prefix",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L193",
   :line 193,
   :var-type "function",
   :arglists ([] [n]),
   :doc
   "Generates an xml prefix.\nZero-arity can only be called, when *gen-prefix-counter* is bound and will increment it.",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/gen-prefix"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "print-uri-file-command!",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L94",
   :line 94,
   :var-type "function",
   :arglists ([uri]),
   :doc
   "Shell command to create a dummy file for xmlns. Execute from a source root.",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/print-uri-file-command!"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-local",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L44",
   :line 44,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the name for this qname",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/qname-local"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L39",
   :line 39,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the namespace uri for this qname",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/qname-uri"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "separate-xmlns",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L159",
   :line 159,
   :var-type "function",
   :arglists ([attrs cont]),
   :doc "Call cont with two args: attributes and xmlns attributes",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/separate-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "uri-file",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L87",
   :line 87,
   :var-type "function",
   :arglists ([uri]),
   :doc "Dummy file name for :require'ing xmlns uri",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/uri-file"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc",
   :name "xmlns-attr?",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/63386cf3f66e491ff3896913423d961011dd3e3f/src/main/clojure/clojure/data/xml/name.cljc#L131",
   :line 131,
   :var-type "function",
   :arglists ([qn]),
   :doc "Is this qname an xmlns declaration?",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.name/xmlns-attr?"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->CData",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L182",
   :line 182,
   :var-type "function",
   :arglists ([content]),
   :doc
   "Positional factory function for class clojure.data.xml.node.CData.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->CData"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->Comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L183",
   :line 183,
   :var-type "function",
   :arglists ([content]),
   :doc
   "Positional factory function for class clojure.data.xml.node.Comment.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->Comment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->Element",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L42",
   :line 42,
   :var-type "function",
   :arglists ([tag attrs content meta]),
   :doc
   "Positional factory function for class clojure.data.xml.node.Element.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->Element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->ElementIterator",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L34",
   :line 34,
   :var-type "function",
   :arglists ([el fields]),
   :doc
   "Positional factory function for class clojure.data.xml.node.ElementIterator.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->ElementIterator"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "cdata",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L207",
   :line 207,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a CData node",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/cdata"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L201",
   :line 201,
   :var-type "function",
   :arglists ([tag] [tag attrs] [tag attrs & content]),
   :doc "Create an xml Element from content varargs",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element*",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L185",
   :line 185,
   :var-type "function",
   :arglists ([tag attrs content meta] [tag attrs content]),
   :doc
   "Create an xml element from a content collection and optional metadata",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/element*"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "map->CData",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L182",
   :line 182,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.node.CData, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/map->CData"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "map->Comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L183",
   :line 183,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.node.Comment, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/map->Comment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc",
   :name "xml-comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/7947b16c69983080e4f62dec27111ff1a26ed538/src/main/clojure/clojure/data/xml/node.cljc#L212",
   :line 212,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a Comment node",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/xml-comment"}
  {:name "CData",
   :var-type "record",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/CData",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "Comment",
   :var-type "record",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/Comment",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "Element",
   :var-type "type",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/Element",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "ElementIterator",
   :var-type "type",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.node/ElementIterator",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj",
   :name "aggregate-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj#L41",
   :line 41,
   :var-type "function",
   :arglists ([xml]),
   :doc "Put all occurring xmlns into the root",
   :namespace "clojure.data.xml.process",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.process/aggregate-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj",
   :name "find-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/60ba605b4f66e2ffb32e70bbadde0201f25e552d/src/main/clojure/clojure/data/xml/process.clj#L34",
   :line 34,
   :var-type "function",
   :arglists ([xml]),
   :doc "Find all xmlns occuring in a root",
   :namespace "clojure.data.xml.process",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.process/find-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc",
   :name "AsElements",
   :file "src/main/clojure/clojure/data/xml/protocols.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc#L25",
   :line 25,
   :var-type "protocol",
   :arglists nil,
   :doc nil,
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/AsElements"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc",
   :name "AsQName",
   :file "src/main/clojure/clojure/data/xml/protocols.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc#L13",
   :line 13,
   :var-type "protocol",
   :arglists nil,
   :doc nil,
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/AsQName"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc",
   :name "AsXmlString",
   :file "src/main/clojure/clojure/data/xml/protocols.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc#L28",
   :line 28,
   :var-type "protocol",
   :arglists nil,
   :doc nil,
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/AsXmlString"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc",
   :name "EventGeneration",
   :file "src/main/clojure/clojure/data/xml/protocols.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e7d7deebb59be833aa0a8211c8aea61b8810ebb7/src/main/clojure/clojure/data/xml/protocols.cljc#L17",
   :line 17,
   :var-type "protocol",
   :arglists nil,
   :doc "Protocol for generating new events based on element type",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/EventGeneration"}
  {:raw-source-url nil,
   :name "as-elements",
   :file nil,
   :source-url nil,
   :var-type "function",
   :arglists ([expr]),
   :doc "Return a seq of elements represented by an expression.",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/as-elements"}
  {:raw-source-url nil,
   :name "qname-local",
   :file nil,
   :source-url nil,
   :var-type "function",
   :arglists ([qname]),
   :doc "Get the name for this qname",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/qname-local"}
  {:raw-source-url nil,
   :name "qname-uri",
   :file nil,
   :source-url nil,
   :var-type "function",
   :arglists ([qname]),
   :doc "Get the namespace uri for this qname",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/qname-uri"}
  {:raw-source-url nil,
   :name "xml-str",
   :file nil,
   :source-url nil,
   :var-type "function",
   :arglists ([node]),
   :doc "Serialize atribute value or content node",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/xml-str"}
  {:raw-source-url nil,
   :name "gen-event",
   :file nil,
   :source-url nil,
   :var-type "function",
   :arglists ([item]),
   :doc "Function to generate an event for e.",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/gen-event"}
  {:raw-source-url nil,
   :name "next-events",
   :file nil,
   :source-url nil,
   :var-type "function",
   :arglists ([item next-items]),
   :doc
   "Returns the next set of events that should occur after e.  next-events are the\nevents that should be generated after this one is complete.",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/next-events"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :name "sexp-as-element",
   :file "src/main/clojure/clojure/data/xml/prxml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L66",
   :line 66,
   :var-type "function",
   :arglists ([sexp]),
   :doc "Convert a single sexp into an Element",
   :namespace "clojure.data.xml.prxml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.prxml/sexp-as-element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :name "sexps-as-fragment",
   :file "src/main/clojure/clojure/data/xml/prxml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L50",
   :line 50,
   :var-type "function",
   :arglists ([] [sexp] [sexp & sexps]),
   :doc
   "Convert a compact prxml/hiccup-style data structure into the more formal\ntag/attrs/content format. A seq of elements will be returned, which may\nnot be suitable for immediate use as there is no root element. See also\nsexp-as-element.\n\nThe format is [:tag-name attr-map? content*]. Each vector opens a new tag;\nseqs do not open new tags, and are just used for inserting groups of elements\ninto the parent tag. A bare keyword not in a vector creates an empty element.\n\nTo provide XML conversion for your own data types, extend the AsElements\nprotocol to them.",
   :namespace "clojure.data.xml.prxml",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.prxml/sexps-as-fragment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/760e8c054c889ee1ca79a09bc79788c976f5d371/src/main/clojure/clojure/data/xml/pu_map.cljc",
   :name "merge",
   :file "src/main/clojure/clojure/data/xml/pu_map.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/760e8c054c889ee1ca79a09bc79788c976f5d371/src/main/clojure/clojure/data/xml/pu_map.cljc#L100",
   :line 100,
   :var-type "function",
   :arglists ([pu {:keys [:p->u]}]),
   :doc "Merge two pu-maps, left to right",
   :namespace "clojure.data.xml.pu-map",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.pu-map/merge"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/760e8c054c889ee1ca79a09bc79788c976f5d371/src/main/clojure/clojure/data/xml/pu_map.cljc",
   :name "merge-prefix-map",
   :file "src/main/clojure/clojure/data/xml/pu_map.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/760e8c054c889ee1ca79a09bc79788c976f5d371/src/main/clojure/clojure/data/xml/pu_map.cljc#L95",
   :line 95,
   :var-type "function",
   :arglists ([pu pm]),
   :doc "Merge a prefix map into pu-map",
   :namespace "clojure.data.xml.pu-map",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.pu-map/merge-prefix-map"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/760e8c054c889ee1ca79a09bc79788c976f5d371/src/main/clojure/clojure/data/xml/pu_map.cljc",
   :name "reduce-diff",
   :file "src/main/clojure/clojure/data/xml/pu_map.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/760e8c054c889ee1ca79a09bc79788c976f5d371/src/main/clojure/clojure/data/xml/pu_map.cljc#L80",
   :line 80,
   :var-type "function",
   :arglists ([f s {ppu :p->u} {pu :p->u}]),
   :doc
   "A high-performance diffing operation, that reduces f over changed and removed prefixes",
   :namespace "clojure.data.xml.pu-map",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.pu-map/reduce-diff"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj",
   :name "event-tree",
   :file "src/main/clojure/clojure/data/xml/tree.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj#L66",
   :line 66,
   :var-type "function",
   :arglists ([events]),
   :doc
   "Returns a lazy tree of Element objects for the given seq of Event\nobjects. See source-seq and parse.",
   :namespace "clojure.data.xml.tree",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.tree/event-tree"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj",
   :name "flatten-elements",
   :file "src/main/clojure/clojure/data/xml/tree.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj#L55",
   :line 55,
   :var-type "function",
   :arglists ([elements]),
   :doc "Flatten a collection of elements to an event seq",
   :namespace "clojure.data.xml.tree",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.tree/flatten-elements"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj",
   :name "seq-tree",
   :file "src/main/clojure/clojure/data/xml/tree.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj#L15",
   :line 15,
   :var-type "function",
   :arglists ([parent exit? node coll]),
   :doc
   "Takes a seq of events that logically represents\na tree by each event being one of: enter-sub-tree event,\nexit-sub-tree event, or node event.\n\nReturns a lazy sequence whose first element is a sequence of\nsub-trees and whose remaining elements are events that are not\nsiblings or descendants of the initial event.\n\nThe given exit? function must return true for any exit-sub-tree\nevent.  parent must be a function of two arguments: the first is an\nevent, the second a sequence of nodes or subtrees that are children\nof the event.  parent must return nil or false if the event is not\nan enter-sub-tree event.  Any other return value will become\na sub-tree of the output tree and should normally contain in some\nway the children passed as the second arg.  The node function is\ncalled with a single event arg on every event that is neither parent\nnor exit, and its return value will become a node of the output tree.\n\n(seq-tree #(when (= %1 :<) (vector %2)) #{:>} str\n          [1 2 :< 3 :< 4 :> :> 5 :> 6])\n;=> ((\"1\" \"2\" [(\"3\" [(\"4\")])] \"5\") 6)",
   :namespace "clojure.data.xml.tree",
   :wiki-url
   "https://clojure.github.io/data.xml//index.html#clojure.data.xml.tree/seq-tree"})}
