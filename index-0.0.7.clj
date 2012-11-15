{:namespaces
 ({:source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
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
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L335",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/emit",
   :doc
   "Prints the given Element tree as XML text to stream.\nOptions:\n :encoding <str>          Character encoding to use",
   :var-type "function",
   :line 335,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e]),
   :name "emit-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L352",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/emit-str",
   :doc "Emits the Element to String and returns it",
   :var-type "function",
   :line 352,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([events]),
   :name "event-tree",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L165",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/event-tree",
   :doc
   "Returns a lazy tree of Element objects for the given seq of Event\nobjects. See source-seq and parse.",
   :var-type "function",
   :line 165,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e stream & {:as opts}]),
   :name "indent",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L365",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/indent",
   :doc
   "Emits the XML and indents the result.  WARNING: this is slow\nit will emit the XML and read it in again to indent it.  Intended for \ndebugging/testing only.",
   :var-type "function",
   :line 365,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([e]),
   :name "indent-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L376",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/indent-str",
   :doc
   "Emits the XML and indents the result.  Writes the results to a String and returns it",
   :var-type "function",
   :line 376,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([source & props]),
   :name "parse",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L309",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/parse",
   :doc
   "Parses the source, which can be an\nInputStream or Reader, and returns a lazy tree of Element records. Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 309,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([s & props]),
   :name "parse-str",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L317",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/parse-str",
   :doc
   "Parses the passed in string to Clojure data structures.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 317,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([parent exit? node coll]),
   :name "seq-tree",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L129",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/seq-tree",
   :doc
   "Takes a seq of events that logically represents\na tree by each event being one of: enter-sub-tree event,\nexit-sub-tree event, or node event.\n\nReturns a lazy sequence whose first element is a sequence of\nsub-trees and whose remaining elements are events that are not\nsiblings or descendants of the initial event.\n\nThe given exit? function must return true for any exit-sub-tree\nevent.  parent must be a function of two arguments: the first is an\nevent, the second a sequence of nodes or subtrees that are children\nof the event.  parent must return nil or false if the event is not\nan enter-sub-tree event.  Any other return value will become\na sub-tree of the output tree and should normally contain in some\nway the children passed as the second arg.  The node function is\ncalled with a single event arg on every event that is neither parent\nnor exit, and its return value will become a node of the output tree.\n\n(seq-tree #(when (= %1 :<) (vector %2)) #{:>} str\n          [1 2 :< 3 :< 4 :> :> 5 :> 6])\n;=> ((\"1\" \"2\" [(\"3\" [(\"4\")])] \"5\") 6)",
   :var-type "function",
   :line 129,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([sexp]),
   :name "sexp-as-element",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L227",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/sexp-as-element",
   :doc "Convert a single sexp into an Element",
   :var-type "function",
   :line 227,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([] [sexp] [sexp & sexps]),
   :name "sexps-as-fragment",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L211",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/sexps-as-fragment",
   :doc
   "Convert a compact prxml/hiccup-style data structure into the more formal\ntag/attrs/content format. A seq of elements will be returned, which may\nnot be suitable for immediate use as there is no root element. See also\nsexp-as-element.\n\nThe format is [:tag-name attr-map? content*]. Each vector opens a new tag;\nseqs do not open new tags, and are just used for inserting groups of elements\ninto the parent tag. A bare keyword not in a vector creates an empty element.\n\nTo provide XML conversion for your own data types, extend the AsElements\nprotocol to them.",
   :var-type "function",
   :line 211,
   :file "src/main/clojure/clojure/data/xml.clj"}
  {:arglists ([s & {:as props}]),
   :name "source-seq",
   :namespace "clojure.data.xml",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L295",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/source-seq",
   :doc
   "Parses the XML InputSource source using a pull-parser. Returns\na lazy sequence of Event records.  Accepts key pairs\nwith XMLInputFactory options, see http://docs.oracle.com/javase/6/docs/api/javax/xml/stream/XMLInputFactory.html\nand xml-input-factory-props for more information. Defaults coalescing true.",
   :var-type "function",
   :line 295,
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
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L178",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/AsElements",
   :namespace "clojure.data.xml",
   :line 178,
   :var-type "protocol",
   :doc nil,
   :name "AsElements"}
  {:file "src/main/clojure/clojure/data/xml.clj",
   :raw-source-url
   "https://github.com/clojure/data.xml/raw/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj",
   :source-url
   "https://github.com/clojure/data.xml/blob/620b21f023d09d2150253be8a17977d2a406525c/src/main/clojure/clojure/data/xml.clj#L60",
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/EventGeneration",
   :namespace "clojure.data.xml",
   :line 60,
   :var-type "protocol",
   :doc "Protocol for generating new events based on element type",
   :name "EventGeneration"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/as-elements",
   :namespace "clojure.data.xml",
   :var-type "function",
   :arglists ([expr]),
   :doc "Return a seq of elements represented by an expression.",
   :name "as-elements"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/gen-event",
   :namespace "clojure.data.xml",
   :var-type "function",
   :arglists ([item]),
   :doc "Function to generate an event for e.",
   :name "gen-event"}
  {:file nil,
   :raw-source-url nil,
   :source-url nil,
   :wiki-url
   "http://clojure.github.com/data.xml//clojure.data.xml-api.html#clojure.data.xml/next-events",
   :namespace "clojure.data.xml",
   :var-type "function",
   :arglists ([item next-items]),
   :doc
   "Returns the next set of events that should occur after e.  next-events are the\nevents that should be generated after this one is complete.",
   :name "next-events"})}
