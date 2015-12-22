{:namespaces
 ({:source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml-api.html",
   :name "clojure.data.xml",
   :author "Chris Houser",
   :doc
   "Functions to parse XML into lazy sequences and lazy trees and\nemit these as text."}
  {:source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.event-api.html",
   :name "clojure.data.xml.event",
   :author "Herwig Hochleitner",
   :doc "Data type for xml pull events"}
  {:source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.impl-api.html",
   :name "clojure.data.xml.impl",
   :author "Herwig Hochleitner",
   :doc "Shared private code for data.xml namespaces"}
  {:source-url
   "https://github.com/clojure/data.xml/blob/8b620747d032d71ae94ecae488aa4647ca54e687/src/main/clojure/clojure/data/xml/jvm/emit.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.jvm.emit-api.html",
   :name "clojure.data.xml.jvm.emit",
   :author "Herwig Hochleitner",
   :doc "JVM implementation of the emitter details"}
  {:source-url
   "https://github.com/clojure/data.xml/blob/49c8f6e2613c92e9346d54e2542c9700c44ef685/src/main/clojure/clojure/data/xml/jvm/parse.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.jvm.parse-api.html",
   :name "clojure.data.xml.jvm.parse",
   :doc nil}
  {:source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.name-api.html",
   :name "clojure.data.xml.name",
   :doc nil}
  {:source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.node-api.html",
   :name "clojure.data.xml.node",
   :author "Herwig Hochleitner",
   :doc "Data types for xml nodes: Element, CData and Comment"}
  {:source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.process-api.html",
   :name "clojure.data.xml.process",
   :doc nil}
  {:source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/protocols.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.protocols-api.html",
   :name "clojure.data.xml.protocols",
   :doc nil}
  {:source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.prxml-api.html",
   :name "clojure.data.xml.prxml",
   :doc nil}
  {:source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml.tree-api.html",
   :name "clojure.data.xml.tree",
   :doc nil}),
 :vars
 ({:arglists ([xml]),
   :name "aggregate-xmlns",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L39",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/aggregate-xmlns",
   :doc "Put all occurring xmlns into the root",
   :var-type "function",
   :line 39,
   :file "src/main/clojure/clojure/data/xml/process.clj"}
  {:arglists ([& {:as alias-nss}]),
   :name "alias-ns",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L105",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/alias-ns",
   :doc
   "Define a clojure namespace alias for shortened keyword and symbol namespaces.\n Similar to clojure.core/alias, but if namespace doesn't exist, it is created.\n\n ## Example\n (declare-ns :xml.dav \"DAV:\")\n (alias-ns :D :xml.dav)\n{:tag ::D/propfind :content []}",
   :var-type "function",
   :line 105,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([n]),
   :name "canonical-name",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L40",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/canonical-name",
   :doc "Put (q)name into canonical form as per ns-env",
   :var-type "function",
   :line 40,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([content]),
   :name "cdata",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L51",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/cdata",
   :doc "Create a CData node",
   :var-type "function",
   :line 51,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([& {:as cljns-xmlnss}]),
   :name "declare-ns",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L86",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/declare-ns",
   :doc
   "Define mappings in the global keyword-ns -> qname-uri mapping table.\nArguments are pairs of ns-name - qname-uri\nns-name must be a string, symbol, keyword or clojure namespace. The canonical form is string.\nns-uri must be a string",
   :var-type "function",
   :line 86,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([tag] [tag attrs] [tag attrs & content]),
   :name "element",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L45",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/element",
   :doc "Create an xml Element from content varargs",
   :var-type "function",
   :line 45,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([tag attrs content meta] [tag attrs content]),
   :name "element*",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L30",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/element*",
   :doc
   "Create an xml element from a content collection and optional metadata",
   :var-type "function",
   :line 30,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([{:keys [attrs], :as element}]),
   :name "element-nss",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L22",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/element-nss",
   :doc "Get xmlns environment from element",
   :var-type "function",
   :line 22,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([e writer & {:as opts}]),
   :name "emit",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L76",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/emit",
   :doc
   "Prints the given Element tree as XML text to stream.\nOptions:\n :encoding <str>          Character encoding to use",
   :var-type "function",
   :line 76,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e & opts]),
   :name "emit-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L83",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/emit-str",
   :doc "Emits the Element to String and returns it",
   :var-type "function",
   :line 83,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([source {:as props}]),
   :name "event-seq",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L45",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/event-seq",
   :doc
   "Parses the XML InputSource source using a pull-parser. Returns\na lazy sequence of Event records.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information.\nDefaults coalescing true and supporting-external-entities false.\n:include-node? can be a set of #{:start-element :end-element :characters :comment}",
   :var-type "function",
   :line 45,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([xml]),
   :name "find-xmlns",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L32",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/find-xmlns",
   :doc "Find all xmlns occuring in a root",
   :var-type "function",
   :line 32,
   :file "src/main/clojure/clojure/data/xml/process.clj"}
  {:arglists ([e writer & opts]),
   :name "indent",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L90",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/indent",
   :doc
   "Emits the XML and indents the result.  WARNING: this is slow\nit will emit the XML and read it in again to indent it.  Intended for\ndebugging/testing only.",
   :var-type "function",
   :line 90,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e & opts]),
   :name "indent-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L97",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/indent-str",
   :doc
   "Emits the XML and indents the result.  Writes the results to a String and returns it",
   :var-type "function",
   :line 97,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([ns]),
   :name "ns-uri",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L35",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/ns-uri",
   :doc "Look up xmlns uri to keyword namespace",
   :var-type "function",
   :line 35,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([source & opts]),
   :name "parse",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L61",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/parse",
   :doc
   "Parses the source, which can be an\nInputStream or Reader, and returns a lazy tree of Element records. Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 61,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([s & opts]),
   :name "parse-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj#L69",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/parse-str",
   :doc
   "Parses the passed in string to Clojure data structures.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 69,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([sexp]),
   :name "sexp-as-element",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L66",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/sexp-as-element",
   :doc "Convert a single sexp into an Element",
   :var-type "function",
   :line 66,
   :file "src/main/clojure/clojure/data/xml/prxml.clj"}
  {:arglists ([] [sexp] [sexp & sexps]),
   :name "sexps-as-fragment",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L50",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/sexps-as-fragment",
   :doc
   "Convert a compact prxml/hiccup-style data structure into the more formal\ntag/attrs/content format. A seq of elements will be returned, which may\nnot be suitable for immediate use as there is no root element. See also\nsexp-as-element.\n\nThe format is [:tag-name attr-map? content*]. Each vector opens a new tag;\nseqs do not open new tags, and are just used for inserting groups of elements\ninto the parent tag. A bare keyword not in a vector creates an empty element.\n\nTo provide XML conversion for your own data types, extend the AsElements\nprotocol to them.",
   :var-type "function",
   :line 50,
   :file "src/main/clojure/clojure/data/xml/prxml.clj"}
  {:arglists ([uri]),
   :name "uri-ns",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L40",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/uri-ns",
   :doc "Look up keyword namespace to xmlns uri",
   :var-type "function",
   :line 40,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([content]),
   :name "xml-comment",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L56",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/xml-comment",
   :doc "Create a Comment node",
   :var-type "function",
   :line 56,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([str]),
   :name "->CDataEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L33",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/->CDataEvent",
   :doc
   "Positional factory function for class clojure.data.xml.event.CDataEvent.",
   :var-type "function",
   :line 33,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([str]),
   :name "->CharsEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L32",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/->CharsEvent",
   :doc
   "Positional factory function for class clojure.data.xml.event.CharsEvent.",
   :var-type "function",
   :line 32,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([str]),
   :name "->CommentEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L34",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/->CommentEvent",
   :doc
   "Positional factory function for class clojure.data.xml.event.CommentEvent.",
   :var-type "function",
   :line 34,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([tag]),
   :name "->EndElementEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L31",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/->EndElementEvent",
   :doc
   "Positional factory function for class clojure.data.xml.event.EndElementEvent.",
   :var-type "function",
   :line 31,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([tag attrs nss]),
   :name "->StartElementEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L30",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/->StartElementEvent",
   :doc
   "Positional factory function for class clojure.data.xml.event.StartElementEvent.",
   :var-type "function",
   :line 30,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([{:keys [attrs], :as element}]),
   :name "element-nss",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L22",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/element-nss",
   :doc "Get xmlns environment from element",
   :var-type "function",
   :line 22,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->CDataEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L33",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/map->CDataEvent",
   :doc
   "Factory function for class clojure.data.xml.event.CDataEvent, taking a map of keywords to field values.",
   :var-type "function",
   :line 33,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->CharsEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L32",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/map->CharsEvent",
   :doc
   "Factory function for class clojure.data.xml.event.CharsEvent, taking a map of keywords to field values.",
   :var-type "function",
   :line 32,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->CommentEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L34",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/map->CommentEvent",
   :doc
   "Factory function for class clojure.data.xml.event.CommentEvent, taking a map of keywords to field values.",
   :var-type "function",
   :line 34,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->EndElementEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L31",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/map->EndElementEvent",
   :doc
   "Factory function for class clojure.data.xml.event.EndElementEvent, taking a map of keywords to field values.",
   :var-type "function",
   :line 31,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->StartElementEvent",
   :namespace "clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj#L30",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5896a50e2f46b5231215b24d950350741ef912bc/src/main/clojure/clojure/data/xml/event.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/map->StartElementEvent",
   :doc
   "Factory function for class clojure.data.xml.event.StartElementEvent, taking a map of keywords to field values.",
   :var-type "function",
   :line 30,
   :file "src/main/clojure/clojure/data/xml/event.clj"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/CDataEvent",
   :namespace "clojure.data.xml.event",
   :var-type "record",
   :name "CDataEvent"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/CharsEvent",
   :namespace "clojure.data.xml.event",
   :var-type "record",
   :name "CharsEvent"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/CommentEvent",
   :namespace "clojure.data.xml.event",
   :var-type "record",
   :name "CommentEvent"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/EndElementEvent",
   :namespace "clojure.data.xml.event",
   :var-type "record",
   :name "EndElementEvent"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.event/StartElementEvent",
   :namespace "clojure.data.xml.event",
   :var-type "record",
   :name "StartElementEvent"}
  {:arglists ([& names]),
   :name "export-api",
   :namespace "clojure.data.xml.impl",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj#L26",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.impl/export-api",
   :doc
   "This creates vars, that take their (local) name, value and metadata from another var",
   :var-type "macro",
   :line 26,
   :file "src/main/clojure/clojure/data/xml/impl.clj"}
  {:arglists ([proto & types+mmaps]),
   :name "extend-protocol-fns",
   :namespace "clojure.data.xml.impl",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj#L41",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.impl/extend-protocol-fns",
   :doc
   "Helper to many types to a protocol with a method map, similar to extend",
   :var-type "macro",
   :line 41,
   :file "src/main/clojure/clojure/data/xml/impl.clj"}
  {:arglists ([val & cases]),
   :name "static-case",
   :namespace "clojure.data.xml.impl",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj#L31",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.impl/static-case",
   :doc "Variant of case where keys are evaluated at compile-time",
   :var-type "macro",
   :line 31,
   :file "src/main/clojure/clojure/data/xml/impl.clj"}
  {:arglists ([swriter events opts]),
   :name "write-document",
   :namespace "clojure.data.xml.jvm.emit",
   :source-url
   "https://github.com/clojure/data.xml/blob/8b620747d032d71ae94ecae488aa4647ca54e687/src/main/clojure/clojure/data/xml/jvm/emit.clj#L110",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8b620747d032d71ae94ecae488aa4647ca54e687/src/main/clojure/clojure/data/xml/jvm/emit.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.jvm.emit/write-document",
   :doc
   "Writes the given event seq as XML text to writer.\nOptions:\n :encoding <str>          Character encoding to use",
   :var-type "function",
   :line 110,
   :file "src/main/clojure/clojure/data/xml/jvm/emit.clj"}
  {:arglists ([sreader include-node? ns-envs]),
   :name "pull-seq",
   :namespace "clojure.data.xml.jvm.parse",
   :source-url
   "https://github.com/clojure/data.xml/blob/49c8f6e2613c92e9346d54e2542c9700c44ef685/src/main/clojure/clojure/data/xml/jvm/parse.clj#L60",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/49c8f6e2613c92e9346d54e2542c9700c44ef685/src/main/clojure/clojure/data/xml/jvm/parse.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.jvm.parse/pull-seq",
   :doc
   "Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to\nbe triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete.",
   :var-type "function",
   :line 60,
   :file "src/main/clojure/clojure/data/xml/jvm/parse.clj"}
  {:name "*gen-prefix-counter*",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L168",
   :dynamic true,
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/*gen-prefix-counter*",
   :doc "Thread local counter for a single document",
   :var-type "var",
   :line 168,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([& {:as alias-nss}]),
   :name "alias-ns",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L105",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/alias-ns",
   :doc
   "Define a clojure namespace alias for shortened keyword and symbol namespaces.\n Similar to clojure.core/alias, but if namespace doesn't exist, it is created.\n\n ## Example\n (declare-ns :xml.dav \"DAV:\")\n (alias-ns :D :xml.dav)\n{:tag ::D/propfind :content []}",
   :var-type "function",
   :line 105,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([& {:as cljns-xmlnss}]),
   :name "declare-ns",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L86",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/declare-ns",
   :doc
   "Define mappings in the global keyword-ns -> qname-uri mapping table.\nArguments are pairs of ns-name - qname-uri\nns-name must be a string, symbol, keyword or clojure namespace. The canonical form is string.\nns-uri must be a string",
   :var-type "function",
   :line 86,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([] [n]),
   :name "gen-prefix",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L172",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/gen-prefix",
   :doc
   "Generates an xml prefix.\nZero-arity can only be called, when *gen-prefix-counter* is bound and will increment it.",
   :var-type "function",
   :line 172,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([nss1 nss2]),
   :name "merge-nss",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L124",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/merge-nss",
   :doc
   "Merge two attribute sets, deleting assignments of empty-string",
   :var-type "function",
   :line 124,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([ns]),
   :name "ns-uri",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L35",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/ns-uri",
   :doc "Look up xmlns uri to keyword namespace",
   :var-type "function",
   :line 35,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([attrs cont]),
   :name "separate-xmlns",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L144",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/separate-xmlns",
   :doc "Call cont with two args: attributes and xmlns attributes",
   :var-type "function",
   :line 144,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([uri]),
   :name "uri-ns",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L40",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/uri-ns",
   :doc "Look up keyword namespace to xmlns uri",
   :var-type "function",
   :line 40,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([qn]),
   :name "xmlns-attr?",
   :namespace "clojure.data.xml.name",
   :source-url
   "https://github.com/clojure/data.xml/blob/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj#L135",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/41aaffb316a67379b23561f242fb068eda9bb4cc/src/main/clojure/clojure/data/xml/name.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.name/xmlns-attr?",
   :doc "Is this qname an xmlns declaration?",
   :var-type "function",
   :line 135,
   :file "src/main/clojure/clojure/data/xml/name.clj"}
  {:arglists ([content]),
   :name "->CData",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L27",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/->CData",
   :doc
   "Positional factory function for class clojure.data.xml.node.CData.",
   :var-type "function",
   :line 27,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([content]),
   :name "->Comment",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L28",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/->Comment",
   :doc
   "Positional factory function for class clojure.data.xml.node.Comment.",
   :var-type "function",
   :line 28,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([tag attrs content]),
   :name "->Element",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L16",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/->Element",
   :doc
   "Positional factory function for class clojure.data.xml.node.Element.",
   :var-type "function",
   :line 16,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([content]),
   :name "cdata",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L51",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/cdata",
   :doc "Create a CData node",
   :var-type "function",
   :line 51,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([tag] [tag attrs] [tag attrs & content]),
   :name "element",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L45",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/element",
   :doc "Create an xml Element from content varargs",
   :var-type "function",
   :line 45,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([tag attrs content meta] [tag attrs content]),
   :name "element*",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L30",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/element*",
   :doc
   "Create an xml element from a content collection and optional metadata",
   :var-type "function",
   :line 30,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->CData",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L27",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/map->CData",
   :doc
   "Factory function for class clojure.data.xml.node.CData, taking a map of keywords to field values.",
   :var-type "function",
   :line 27,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->Comment",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L28",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/map->Comment",
   :doc
   "Factory function for class clojure.data.xml.node.Comment, taking a map of keywords to field values.",
   :var-type "function",
   :line 28,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([m__5818__auto__]),
   :name "map->Element",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L16",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/map->Element",
   :doc
   "Factory function for class clojure.data.xml.node.Element, taking a map of keywords to field values.",
   :var-type "function",
   :line 16,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:arglists ([content]),
   :name "xml-comment",
   :namespace "clojure.data.xml.node",
   :source-url
   "https://github.com/clojure/data.xml/blob/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj#L56",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/8f47a1113152a2ab7df950b710837560d974ee52/src/main/clojure/clojure/data/xml/node.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/xml-comment",
   :doc "Create a Comment node",
   :var-type "function",
   :line 56,
   :file "src/main/clojure/clojure/data/xml/node.clj"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/CData",
   :namespace "clojure.data.xml.node",
   :var-type "record",
   :name "CData"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/Comment",
   :namespace "clojure.data.xml.node",
   :var-type "record",
   :name "Comment"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.node/Element",
   :namespace "clojure.data.xml.node",
   :var-type "record",
   :name "Element"}
  {:arglists ([xml]),
   :name "aggregate-xmlns",
   :namespace "clojure.data.xml.process",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L39",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.process/aggregate-xmlns",
   :doc "Put all occurring xmlns into the root",
   :var-type "function",
   :line 39,
   :file "src/main/clojure/clojure/data/xml/process.clj"}
  {:arglists ([xml]),
   :name "find-xmlns",
   :namespace "clojure.data.xml.process",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L32",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.process/find-xmlns",
   :doc "Find all xmlns occuring in a root",
   :var-type "function",
   :line 32,
   :file "src/main/clojure/clojure/data/xml/process.clj"}
  {:file "src/main/clojure/clojure/data/xml/protocols.clj",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/protocols.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/protocols.clj#L25",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/AsElements",
   :namespace "clojure.data.xml.protocols",
   :line 25,
   :var-type "protocol",
   :doc nil,
   :name "AsElements"}
  {:file "src/main/clojure/clojure/data/xml/protocols.clj",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/protocols.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/protocols.clj#L13",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/AsQName",
   :namespace "clojure.data.xml.protocols",
   :line 13,
   :var-type "protocol",
   :doc nil,
   :name "AsQName"}
  {:file "src/main/clojure/clojure/data/xml/protocols.clj",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/protocols.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/protocols.clj#L17",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/EventGeneration",
   :namespace "clojure.data.xml.protocols",
   :line 17,
   :var-type "protocol",
   :doc "Protocol for generating new events based on element type",
   :name "EventGeneration"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/as-elements",
   :namespace "clojure.data.xml.protocols",
   :var-type "function",
   :arglists ([expr]),
   :doc "Return a seq of elements represented by an expression.",
   :name "as-elements"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/qname-local",
   :namespace "clojure.data.xml.protocols",
   :var-type "function",
   :arglists ([qname]),
   :doc "Get the name for this qname",
   :name "qname-local"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/qname-uri",
   :namespace "clojure.data.xml.protocols",
   :var-type "function",
   :arglists ([qname]),
   :doc "Get the namespace uri for this qname",
   :name "qname-uri"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/gen-event",
   :namespace "clojure.data.xml.protocols",
   :var-type "function",
   :arglists ([item]),
   :doc "Function to generate an event for e.",
   :name "gen-event"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.protocols/next-events",
   :namespace "clojure.data.xml.protocols",
   :var-type "function",
   :arglists ([item next-items]),
   :doc
   "Returns the next set of events that should occur after e.  next-events are the\nevents that should be generated after this one is complete.",
   :name "next-events"}
  {:arglists ([sexp]),
   :name "sexp-as-element",
   :namespace "clojure.data.xml.prxml",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L66",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.prxml/sexp-as-element",
   :doc "Convert a single sexp into an Element",
   :var-type "function",
   :line 66,
   :file "src/main/clojure/clojure/data/xml/prxml.clj"}
  {:arglists ([] [sexp] [sexp & sexps]),
   :name "sexps-as-fragment",
   :namespace "clojure.data.xml.prxml",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj#L50",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.prxml/sexps-as-fragment",
   :doc
   "Convert a compact prxml/hiccup-style data structure into the more formal\ntag/attrs/content format. A seq of elements will be returned, which may\nnot be suitable for immediate use as there is no root element. See also\nsexp-as-element.\n\nThe format is [:tag-name attr-map? content*]. Each vector opens a new tag;\nseqs do not open new tags, and are just used for inserting groups of elements\ninto the parent tag. A bare keyword not in a vector creates an empty element.\n\nTo provide XML conversion for your own data types, extend the AsElements\nprotocol to them.",
   :var-type "function",
   :line 50,
   :file "src/main/clojure/clojure/data/xml/prxml.clj"}
  {:arglists ([events]),
   :name "event-tree",
   :namespace "clojure.data.xml.tree",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj#L66",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.tree/event-tree",
   :doc
   "Returns a lazy tree of Element objects for the given seq of Event\nobjects. See source-seq and parse.",
   :var-type "function",
   :line 66,
   :file "src/main/clojure/clojure/data/xml/tree.clj"}
  {:arglists ([elements]),
   :name "flatten-elements",
   :namespace "clojure.data.xml.tree",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj#L55",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.tree/flatten-elements",
   :doc "Flatten a collection of elements to an event seq",
   :var-type "function",
   :line 55,
   :file "src/main/clojure/clojure/data/xml/tree.clj"}
  {:arglists ([parent exit? node coll]),
   :name "seq-tree",
   :namespace "clojure.data.xml.tree",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj#L15",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml.tree/seq-tree",
   :doc
   "Takes a seq of events that logically represents\na tree by each event being one of: enter-sub-tree event,\nexit-sub-tree event, or node event.\n\nReturns a lazy sequence whose first element is a sequence of\nsub-trees and whose remaining elements are events that are not\nsiblings or descendants of the initial event.\n\nThe given exit? function must return true for any exit-sub-tree\nevent.  parent must be a function of two arguments: the first is an\nevent, the second a sequence of nodes or subtrees that are children\nof the event.  parent must return nil or false if the event is not\nan enter-sub-tree event.  Any other return value will become\na sub-tree of the output tree and should normally contain in some\nway the children passed as the second arg.  The node function is\ncalled with a single event arg on every event that is neither parent\nnor exit, and its return value will become a node of the output tree.\n\n(seq-tree #(when (= %1 :<) (vector %2)) #{:>} str\n          [1 2 :< 3 :< 4 :> :> 5 :> 6])\n;=> ((\"1\" \"2\" [(\"3\" [(\"4\")])] \"5\") 6)",
   :var-type "function",
   :line 15,
   :file "src/main/clojure/clojure/data/xml/tree.clj"})}
