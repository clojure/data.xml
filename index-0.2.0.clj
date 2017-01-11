{:namespaces
 ({:doc
   "Functions to parse XML into lazy sequences and lazy trees and\nemit these as text.",
   :author "Chris Houser",
   :name "clojure.data.xml",
   :wiki-url "http://clojure.github.io/data.xml/index.html",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj"}
  {:doc "Data type for xml pull events",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.event",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj"}
  {:doc "Shared private code for data.xml namespaces",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.impl",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.impl",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj"}
  {:doc "JVM implementation of the emitter details",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.jvm.emit",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.jvm.emit",
   :source-url
   "https://github.com/clojure/data.xml/blob/fa497c80468b882b20e9ac03dcbbc27cacbbd259/src/main/clojure/clojure/data/xml/jvm/emit.clj"}
  {:doc nil,
   :name "clojure.data.xml.jvm.parse",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.jvm.parse",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/jvm/parse.clj"}
  {:doc nil,
   :name "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.name",
   :source-url nil}
  {:doc "Data types for xml nodes: Element, CData and Comment",
   :author "Herwig Hochleitner",
   :name "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.node",
   :source-url nil}
  {:doc nil,
   :name "clojure.data.xml.process",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.process",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj"}
  {:doc nil,
   :name "clojure.data.xml.protocols",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.protocols",
   :source-url nil}
  {:doc nil,
   :name "clojure.data.xml.prxml",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.prxml",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/prxml.clj"}
  {:doc nil,
   :name "clojure.data.xml.tree",
   :wiki-url
   "http://clojure.github.io/data.xml/index.html#clojure.data.xml.tree",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/tree.clj"}),
 :vars
 ({:raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :name "aggregate-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L39",
   :line 39,
   :var-type "function",
   :arglists ([xml]),
   :doc "Put all occurring xmlns into the root",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/aggregate-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "alias-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L99",
   :line 99,
   :var-type "function",
   :arglists ([& {:as alias-nss}]),
   :doc
   "Define a Clojure namespace aliases for xmlns uris.\n\nThis sets up the current namespace for reading qnames denoted with\nClojure's ::alias/keywords reader feature.\n\n\n## Example\n(alias-uri :D \"DAV:\")\n                         ; similar in effect to\n;; (require '[xmlns.DAV%3A :as D])\n                         ; but required namespace is auto-created\n                         ; henceforth, shorthand keywords can be used\n{:tag ::D/propfind}\n                         ; ::D/propfind will be expanded to :xmlns.DAV%3A/propfind\n                         ; in the current namespace by the reader\n\n## Clojurescript support\nCurrently, namespaces can't be auto-created in Clojurescript.\nDummy files for aliased uris have to exist. Have a look at `uri-file` and `print-uri-file-command!` to create those.",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/alias-uri"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "cdata",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L202",
   :line 202,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a CData node",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/cdata"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L196",
   :line 196,
   :var-type "function",
   :arglists ([tag] [tag attrs] [tag attrs & content]),
   :doc "Create an xml Element from content varargs",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element*",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L180",
   :line 180,
   :var-type "function",
   :arglists ([tag attrs content meta] [tag attrs content]),
   :doc
   "Create an xml element from a content collection and optional metadata",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/element*"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "element-nss",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L22",
   :line 22,
   :var-type "function",
   :arglists ([{:keys [attrs], :as element}]),
   :doc "Get xmlns environment from element",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/element-nss"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj",
   :name "emit",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj#L73",
   :line 73,
   :var-type "function",
   :arglists ([e writer & {:as opts}]),
   :doc
   "Prints the given Element tree as XML text to stream.\nOptions:\n :encoding <str>          Character encoding to use\n :doctype  <str>          Document type (DOCTYPE) declaration to use",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/emit"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj",
   :name "emit-str",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj#L81",
   :line 81,
   :var-type "function",
   :arglists ([e & opts]),
   :doc
   "Emits the Element to String and returns it.\nOptions:\n :encoding <str>          Character encoding to use\n :doctype  <str>          Document type (DOCTYPE) declaration to use",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/emit-str"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj",
   :name "event-seq",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj#L41",
   :line 41,
   :var-type "function",
   :arglists ([source {:as props}]),
   :doc
   "Parses the XML InputSource source using a pull-parser. Returns\na lazy sequence of Event records.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information.\nDefaults coalescing true and supporting-external-entities false.\n:include-node? can be a set of #{:start-element :end-element :characters :comment}",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/event-seq"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :name "find-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L32",
   :line 32,
   :var-type "function",
   :arglists ([xml]),
   :doc "Find all xmlns occuring in a root",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/find-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj",
   :name "indent",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj#L91",
   :line 91,
   :var-type "function",
   :arglists ([e writer & opts]),
   :doc
   "Emits the XML and indents the result.  WARNING: this is slow\nit will emit the XML and read it in again to indent it.  Intended for\ndebugging/testing only.",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/indent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj",
   :name "indent-str",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj#L98",
   :line 98,
   :var-type "function",
   :arglists ([e & opts]),
   :doc
   "Emits the XML and indents the result.  Writes the results to a String and returns it",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/indent-str"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj",
   :name "parse",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj#L58",
   :line 58,
   :var-type "function",
   :arglists ([source & opts]),
   :doc
   "Parses the source, which can be an\nInputStream or Reader, and returns a lazy tree of Element records. Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/parse"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj",
   :name "parse-str",
   :file "src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml.clj#L66",
   :line 66,
   :var-type "function",
   :arglists ([s & opts]),
   :doc
   "Parses the passed in string to Clojure data structures.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/parse-str"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "print-uri-file-command!",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L93",
   :line 93,
   :var-type "function",
   :arglists ([uri]),
   :doc
   "Shell command to create a dummy file for xmlns. Execute from a source root.",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/print-uri-file-command!"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-local",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L44",
   :line 44,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the name for this qname",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/qname-local"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L39",
   :line 39,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the namespace uri for this qname",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/qname-uri"}
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
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/sexp-as-element"}
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
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/sexps-as-fragment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "uri-file",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L86",
   :line 86,
   :var-type "function",
   :arglists ([uri]),
   :doc "Dummy file name for :require'ing xmlns uri",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/uri-file"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "xml-comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L207",
   :line 207,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a Comment node",
   :namespace "clojure.data.xml",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml/xml-comment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "->CDataEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L33",
   :line 33,
   :var-type "function",
   :arglists ([str]),
   :doc
   "Positional factory function for class clojure.data.xml.event.CDataEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->CDataEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "->CharsEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L32",
   :line 32,
   :var-type "function",
   :arglists ([str]),
   :doc
   "Positional factory function for class clojure.data.xml.event.CharsEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->CharsEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "->CommentEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L34",
   :line 34,
   :var-type "function",
   :arglists ([str]),
   :doc
   "Positional factory function for class clojure.data.xml.event.CommentEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->CommentEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "->EndElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L31",
   :line 31,
   :var-type "function",
   :arglists ([tag]),
   :doc
   "Positional factory function for class clojure.data.xml.event.EndElementEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->EndElementEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "->StartElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L30",
   :line 30,
   :var-type "function",
   :arglists ([tag attrs nss location-info]),
   :doc
   "Positional factory function for class clojure.data.xml.event.StartElementEvent.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/->StartElementEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "element-nss",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L22",
   :line 22,
   :var-type "function",
   :arglists ([{:keys [attrs], :as element}]),
   :doc "Get xmlns environment from element",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/element-nss"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->CDataEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L33",
   :line 33,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.CDataEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->CDataEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->CharsEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L32",
   :line 32,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.CharsEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->CharsEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->CommentEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L34",
   :line 34,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.CommentEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->CommentEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->EndElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L31",
   :line 31,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.EndElementEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->EndElementEvent"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj",
   :name "map->StartElementEvent",
   :file "src/main/clojure/clojure/data/xml/event.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/144f799507a5041a00ba683681ef41300eb7c9ca/src/main/clojure/clojure/data/xml/event.clj#L30",
   :line 30,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.event.StartElementEvent, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.event",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/map->StartElementEvent"}
  {:name "CDataEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/CDataEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "CharsEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/CharsEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "CommentEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/CommentEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "EndElementEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/EndElementEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "StartElementEvent",
   :var-type "record",
   :namespace "clojure.data.xml.event",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.event/StartElementEvent",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj",
   :name "export-api",
   :file "src/main/clojure/clojure/data/xml/impl.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj#L26",
   :line 26,
   :var-type "macro",
   :arglists ([& names]),
   :doc
   "This creates vars, that take their (local) name, value and metadata from another var",
   :namespace "clojure.data.xml.impl",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.impl/export-api"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj",
   :name "extend-protocol-fns",
   :file "src/main/clojure/clojure/data/xml/impl.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj#L41",
   :line 41,
   :var-type "macro",
   :arglists ([proto & types+mmaps]),
   :doc
   "Helper to many types to a protocol with a method map, similar to extend",
   :namespace "clojure.data.xml.impl",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.impl/extend-protocol-fns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj",
   :name "static-case",
   :file "src/main/clojure/clojure/data/xml/impl.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/3d231d6be9b70e829817e1415297ac0dca98a647/src/main/clojure/clojure/data/xml/impl.clj#L31",
   :line 31,
   :var-type "macro",
   :arglists ([val & cases]),
   :doc "Variant of case where keys are evaluated at compile-time",
   :namespace "clojure.data.xml.impl",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.impl/static-case"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/fa497c80468b882b20e9ac03dcbbc27cacbbd259/src/main/clojure/clojure/data/xml/jvm/emit.clj",
   :name "write-document",
   :file "src/main/clojure/clojure/data/xml/jvm/emit.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/fa497c80468b882b20e9ac03dcbbc27cacbbd259/src/main/clojure/clojure/data/xml/jvm/emit.clj#L133",
   :line 133,
   :var-type "function",
   :arglists ([swriter events opts]),
   :doc
   "Writes the given event seq as XML text to writer.\nOptions:\n :encoding <str>          Character encoding to use\n :doctype  <str>          Document type (DOCTYPE) declaration to use",
   :namespace "clojure.data.xml.jvm.emit",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.jvm.emit/write-document"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/jvm/parse.clj",
   :name "pull-seq",
   :file "src/main/clojure/clojure/data/xml/jvm/parse.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/jvm/parse.clj#L67",
   :line 67,
   :var-type "function",
   :arglists
   ([sreader
     {:keys [include-node? location-info skip-whitespace], :as opts}
     ns-envs]),
   :doc
   "Creates a seq of events.  The XMLStreamConstants/SPACE clause below doesn't seem to\nbe triggered by the JDK StAX parser, but is by others.  Leaving in to be more complete.",
   :namespace "clojure.data.xml.jvm.parse",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.jvm.parse/pull-seq"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "*gen-prefix-counter*",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L178",
   :dynamic true,
   :line 178,
   :var-type "var",
   :arglists nil,
   :doc "Thread local counter for a single document",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/*gen-prefix-counter*"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "alias-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L99",
   :line 99,
   :var-type "function",
   :arglists ([& {:as alias-nss}]),
   :doc
   "Define a Clojure namespace aliases for xmlns uris.\n\nThis sets up the current namespace for reading qnames denoted with\nClojure's ::alias/keywords reader feature.\n\n\n## Example\n(alias-uri :D \"DAV:\")\n                         ; similar in effect to\n;; (require '[xmlns.DAV%3A :as D])\n                         ; but required namespace is auto-created\n                         ; henceforth, shorthand keywords can be used\n{:tag ::D/propfind}\n                         ; ::D/propfind will be expanded to :xmlns.DAV%3A/propfind\n                         ; in the current namespace by the reader\n\n## Clojurescript support\nCurrently, namespaces can't be auto-created in Clojurescript.\nDummy files for aliased uris have to exist. Have a look at `uri-file` and `print-uri-file-command!` to create those.",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/alias-uri"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "gen-prefix",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L182",
   :line 182,
   :var-type "function",
   :arglists ([] [n]),
   :doc
   "Generates an xml prefix.\nZero-arity can only be called, when *gen-prefix-counter* is bound and will increment it.",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/gen-prefix"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "merge-nss",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L130",
   :line 130,
   :var-type "function",
   :arglists ([nss1 nss2]),
   :doc
   "Merge two attribute sets, deleting assignments of empty-string",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/merge-nss"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "print-uri-file-command!",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L93",
   :line 93,
   :var-type "function",
   :arglists ([uri]),
   :doc
   "Shell command to create a dummy file for xmlns. Execute from a source root.",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/print-uri-file-command!"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-local",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L44",
   :line 44,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the name for this qname",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/qname-local"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "qname-uri",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L39",
   :line 39,
   :var-type "function",
   :arglists ([v]),
   :doc "Get the namespace uri for this qname",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/qname-uri"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "separate-xmlns",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L150",
   :line 150,
   :var-type "function",
   :arglists ([attrs cont]),
   :doc "Call cont with two args: attributes and xmlns attributes",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/separate-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "uri-file",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L86",
   :line 86,
   :var-type "function",
   :arglists ([uri]),
   :doc "Dummy file name for :require'ing xmlns uri",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/uri-file"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc",
   :name "xmlns-attr?",
   :file "src/main/clojure/clojure/data/xml/name.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/e8eee2d46907e3190af45f219cb1c8f9a1eb5193/src/main/clojure/clojure/data/xml/name.cljc#L141",
   :line 141,
   :var-type "function",
   :arglists ([qn]),
   :doc "Is this qname an xmlns declaration?",
   :namespace "clojure.data.xml.name",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.name/xmlns-attr?"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->CData",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L177",
   :line 177,
   :var-type "function",
   :arglists ([content]),
   :doc
   "Positional factory function for class clojure.data.xml.node.CData.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->CData"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->Comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L178",
   :line 178,
   :var-type "function",
   :arglists ([content]),
   :doc
   "Positional factory function for class clojure.data.xml.node.Comment.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->Comment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->Element",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L42",
   :line 42,
   :var-type "function",
   :arglists ([tag attrs content meta]),
   :doc
   "Positional factory function for class clojure.data.xml.node.Element.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->Element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "->ElementIterator",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L34",
   :line 34,
   :var-type "function",
   :arglists ([el fields]),
   :doc
   "Positional factory function for class clojure.data.xml.node.ElementIterator.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/->ElementIterator"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "cdata",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L202",
   :line 202,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a CData node",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/cdata"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L196",
   :line 196,
   :var-type "function",
   :arglists ([tag] [tag attrs] [tag attrs & content]),
   :doc "Create an xml Element from content varargs",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/element"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "element*",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L180",
   :line 180,
   :var-type "function",
   :arglists ([tag attrs content meta] [tag attrs content]),
   :doc
   "Create an xml element from a content collection and optional metadata",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/element*"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "map->CData",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L177",
   :line 177,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.node.CData, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/map->CData"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "map->Comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L178",
   :line 178,
   :var-type "function",
   :arglists ([m#]),
   :doc
   "Factory function for class clojure.data.xml.node.Comment, taking a map of keywords to field values.",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/map->Comment"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc",
   :name "xml-comment",
   :file "src/main/clojure/clojure/data/xml/node.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/59885b2eeacef967c11525b6425296a8fed7734d/src/main/clojure/clojure/data/xml/node.cljc#L207",
   :line 207,
   :var-type "function",
   :arglists ([content]),
   :doc "Create a Comment node",
   :namespace "clojure.data.xml.node",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/xml-comment"}
  {:name "CData",
   :var-type "record",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/CData",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "Comment",
   :var-type "record",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/Comment",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "Element",
   :var-type "type",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/Element",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "ElementIterator",
   :var-type "type",
   :namespace "clojure.data.xml.node",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.node/ElementIterator",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :name "aggregate-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L39",
   :line 39,
   :var-type "function",
   :arglists ([xml]),
   :doc "Put all occurring xmlns into the root",
   :namespace "clojure.data.xml.process",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.process/aggregate-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj",
   :name "find-xmlns",
   :file "src/main/clojure/clojure/data/xml/process.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/1f511c10270d68f6c999715de2bfc89c84bfb4fe/src/main/clojure/clojure/data/xml/process.clj#L32",
   :line 32,
   :var-type "function",
   :arglists ([xml]),
   :doc "Find all xmlns occuring in a root",
   :namespace "clojure.data.xml.process",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.process/find-xmlns"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/d1d2b8d49326038247e9876523ac51f337e55fa2/src/main/clojure/clojure/data/xml/protocols.cljc",
   :name "AsElements",
   :file "src/main/clojure/clojure/data/xml/protocols.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/d1d2b8d49326038247e9876523ac51f337e55fa2/src/main/clojure/clojure/data/xml/protocols.cljc#L25",
   :line 25,
   :var-type "protocol",
   :arglists nil,
   :doc nil,
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/AsElements"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/d1d2b8d49326038247e9876523ac51f337e55fa2/src/main/clojure/clojure/data/xml/protocols.cljc",
   :name "AsQName",
   :file "src/main/clojure/clojure/data/xml/protocols.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/d1d2b8d49326038247e9876523ac51f337e55fa2/src/main/clojure/clojure/data/xml/protocols.cljc#L13",
   :line 13,
   :var-type "protocol",
   :arglists nil,
   :doc nil,
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/AsQName"}
  {:raw-source-url
   "https://github.com/clojure/data.xml/raw/d1d2b8d49326038247e9876523ac51f337e55fa2/src/main/clojure/clojure/data/xml/protocols.cljc",
   :name "EventGeneration",
   :file "src/main/clojure/clojure/data/xml/protocols.cljc",
   :source-url
   "https://github.com/clojure/data.xml/blob/d1d2b8d49326038247e9876523ac51f337e55fa2/src/main/clojure/clojure/data/xml/protocols.cljc#L17",
   :line 17,
   :var-type "protocol",
   :arglists nil,
   :doc "Protocol for generating new events based on element type",
   :namespace "clojure.data.xml.protocols",
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/EventGeneration"}
  {:name "as-elements",
   :doc "Return a seq of elements represented by an expression.",
   :var-type "function",
   :namespace "clojure.data.xml.protocols",
   :arglists ([expr]),
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/as-elements",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "qname-local",
   :doc "Get the name for this qname",
   :var-type "function",
   :namespace "clojure.data.xml.protocols",
   :arglists ([qname]),
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/qname-local",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "qname-uri",
   :doc "Get the namespace uri for this qname",
   :var-type "function",
   :namespace "clojure.data.xml.protocols",
   :arglists ([qname]),
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/qname-uri",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "gen-event",
   :doc "Function to generate an event for e.",
   :var-type "function",
   :namespace "clojure.data.xml.protocols",
   :arglists ([item]),
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/gen-event",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "next-events",
   :doc
   "Returns the next set of events that should occur after e.  next-events are the\nevents that should be generated after this one is complete.",
   :var-type "function",
   :namespace "clojure.data.xml.protocols",
   :arglists ([item next-items]),
   :wiki-url
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.protocols/next-events",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
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
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.prxml/sexp-as-element"}
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
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.prxml/sexps-as-fragment"}
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
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.tree/event-tree"}
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
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.tree/flatten-elements"}
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
   "http://clojure.github.io/data.xml//index.html#clojure.data.xml.tree/seq-tree"})}
