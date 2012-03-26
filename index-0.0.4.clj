{:namespaces
 ({:source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml/clojure.data.xml-api.html",
   :name "clojure.data.xml",
   :author "Chris Houser",
   :doc
   "Functions to parse XML into lazy sequences and lazy trees and\nemit these as text."}),
 :vars
 ({:arglists ([e stream & {:as opts}]),
   :name "emit",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L276",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/emit",
   :doc
   "Prints the given Element tree as XML text to stream.\nOptions:\n :encoding <str>          Character encoding to use",
   :var-type "function",
   :line 276,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e]),
   :name "emit-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L292",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/emit-str",
   :doc "Emits the Element to String and returns it",
   :var-type "function",
   :line 292,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([events]),
   :name "event-tree",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L110",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/event-tree",
   :doc
   "Returns a lazy tree of Element objects for the given seq of Event\nobjects. See source-seq and parse.",
   :var-type "function",
   :line 110,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e stream & {:as opts}]),
   :name "indent",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L305",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/indent",
   :doc
   "Emits the XML and indents the result.  WARNING: this is slow\nit will emit the XML and read it in again to indent it.  Intended for \ndebugging/testing only.",
   :var-type "function",
   :line 305,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e]),
   :name "indent-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L316",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/indent-str",
   :doc
   "Emits the XML and indents the result.  Writes the results to a String and returns it",
   :var-type "function",
   :line 316,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([source & props]),
   :name "parse",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L250",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/parse",
   :doc
   "Parses the source, which can be an\nInputStream or Reader, and returns a lazy tree of Element records. Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 250,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([s & props]),
   :name "parse-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L258",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/parse-str",
   :doc
   "Parses the passed in string to Clojure data structures.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 258,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([sexp]),
   :name "sexp-as-element",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L172",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/sexp-as-element",
   :doc "Convert a single sexp into an Element",
   :var-type "function",
   :line 172,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([] [sexp] [sexp & sexps]),
   :name "sexps-as-fragment",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L156",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/sexps-as-fragment",
   :doc
   "Convert a compact prxml/hiccup-style data structure into the more formal\ntag/attrs/content format. A seq of elements will be returned, which may\nnot be suitable for immediate use as there is no root element. See also\nsexp-as-element.\n\nThe format is [:tag-name attr-map? content*]. Each vector opens a new tag;\nseqs do not open new tags, and are just used for inserting groups of elements\ninto the parent tag. A bare keyword not in a vector creates an empty element.\n\nTo provide XML conversion for your own data types, extend the AsElements\nprotocol to them.",
   :var-type "function",
   :line 156,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([s & {:as props}]),
   :name "source-seq",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L240",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/source-seq",
   :doc
   "Parses the XML InputSource source using a pull-parser. Returns\na lazy sequence of Event records.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 240,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/CData",
   :namespace "clojure.data.xml",
   :var-type "record",
   :name "CData"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/Comment",
   :namespace "clojure.data.xml",
   :var-type "record",
   :name "Comment"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/Element",
   :namespace "clojure.data.xml",
   :var-type "record",
   :name "Element"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/Event",
   :namespace "clojure.data.xml",
   :var-type "record",
   :name "Event"}
  {:file "src/main/clojure/clojure/data/xml.clj",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/5671774c5013f13fea3410be6e461a5f4956a7f5/src/main/clojure/clojure/data/xml.clj#L123",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/AsElements",
   :namespace "clojure.data.xml",
   :line 123,
   :var-type "protocol",
   :doc nil,
   :name "AsElements"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/as-elements",
   :namespace "clojure.data.xml",
   :var-type "function",
   :arglists ([expr]),
   :doc "Return a seq of elements represented by an expression.",
   :name "as-elements"})}
