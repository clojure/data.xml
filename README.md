# data.xml

[data.xml](https://github.com/clojure/data.xml) is a Clojure library for reading and writing XML data. This
library is the successor to
[lazy-xml](http://clojure.github.com/clojure-contrib/lazy-xml-api.html).
data.xml has the following features:

* Parses XML documents into Clojure data structures
* Emits XML from Clojure data structures
* No additional dependencies if using JDK >= 1.6
* Uses StAX internally
* lazy - should allow parsing and emitting of large XML documents

## API Reference

Generated API docs for data.xml are available [here](http://clojure.github.com/data.xml).

## Bugs

Please report bugs using JIRA [here](http://dev.clojure.org/jira/browse/DXML).

## Installation

Latest stable release: `0.0.8`

Latest preview release: `0.2.0-alpha6`

(The main features of the `0.2.0` series are XML Namespace support and Clojurescript support)

* [All Released Versions](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.clojure%22%20AND%20a%3A%22data.xml%22)

* [Development Snapshot Versions](https://oss.sonatype.org/index.html#nexus-search;gav~org.clojure~data.xml~~~)

### Maven
For Maven projects, add the following XML in your `pom.xml`'s `<dependencies>` section:

    For stable:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>data.xml</artifactId>
      <version>0.0.8</version>
     </dependency>

---

    For preview:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>data.xml</artifactId>
      <version>0.2.0-alpha6</version>
     </dependency>

### Leiningen
Add the following to the `project.clj` dependencies:

    For stable:

    [org.clojure/data.xml "0.0.8"]

---

    For preview:

    [org.clojure/data.xml "0.2.0-alpha6"]

## Examples

The examples below assume you have added a `:refer :all` for data.xml:

    (require '[clojure.data.xml :refer :all])

data.xml supports parsing and emitting XML. The parsing functions will
read XML from a
[Reader](http://docs.oracle.com/javase/6/docs/api/java/io/Reader.html)
or
[InputStream](http://docs.oracle.com/javase/6/docs/api/java/io/InputStream.html).

    (let [input-xml (java.io.StringReader. "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
                                            <foo><bar><baz>The baz value</baz></bar></foo>")]
      (parse input-xml))

    #clojure.data.xml.Element{:tag :foo,
                              :attrs {},
                              :content (#clojure.data.xml.Element{:tag :bar,
                                                                  :attrs {},
                                                                  :content (#clojure.data.xml.Element{:tag :baz,
                                                                                                      :attrs {},
                                                                                                      :content ("The baz value")})})}

The data is returned as defrecords and can be manipulated using the
normal clojure data structure functions. Additional parsing options
can be passed via key pairs:

    (parse-str "<a><![CDATA[\nfoo bar\n]]><![CDATA[\nbaz\n]]></a>" :coalescing false)
    #clojure.data.xml.Element{:tag :a, :attrs {}, :content ("\nfoo bar\n" "\nbaz\n")}

XML elements can be created using the typical defrecord constructor
functions or the element function used below or just a plain map with :tag :attrs :content keys, and written using a
[java.io.Writer](http://docs.oracle.com/javase/6/docs/api/java/io/Writer.html).:

    (let [tags (element :foo {:foo-attr "foo value"}
                 (element :bar {:bar-attr "bar value"}
                   (element :baz {} "The baz value")))]
      (with-open [out-file (java.io.FileWriter. "/tmp/foo.xml")]
        (emit tags out-file)))

    ;;-> Writes XML to /tmp/foo.xml

The same can also be expressed using a more Hiccup-like style of defining the elements using sexp-as-element:

    (= (element :foo {:foo-attr "foo value"}
         (element :bar {:bar-attr "bar value"}
           (element :baz {} "The baz value")))
       (sexp-as-element
          [:foo {:foo-attr "foo value"}
           [:bar {:bar-attr "bar value"}
            [:baz {} "The baz value"]]]))
    ;;-> true

Comments and CDATA can also be emitted as an S-expression with the special tag names :-cdata and :-comment:

    (= (element :tag {:attr "value"}
         (element :body {} (cdata "not parsed <stuff")))
       (sexp-as-element [:tag {:attr "value"} [:body {} [:-cdata "not parsed <stuff"]]]
    ;;-> true

XML can be "round tripped" through the library:

    (let [tags (element :foo {:foo-attr "foo value"}
                 (element :bar {:bar-attr "bar value"}
                   (element :baz {} "The baz value")))]
      (with-open [out-file (java.io.FileWriter. "/tmp/foo.xml")]
        (emit tags out-file))
      (with-open [input (java.io.FileInputStream. "/tmp/foo.xml")]
        (parse input)))

    #clojure.data.xml.Element{:tag :foo, :attrs {:foo-attr "foo value"}...}

There are also some string based functions that are useful for
debugging.

    (let [tags (element :foo {:foo-attr "foo value"}
                 (element :bar {:bar-attr "bar value"}
                   (element :baz {} "The baz value")))]
      (= tags (parse-str (emit-str tags))))

    true

Indentation is supported, but should be treated as a debugging feature
as it's likely to be pretty slow:

    (print (indent-str (element :foo {:foo-attr "foo value"}
                         (element :bar {:bar-attr "bar value"}
                           (element :baz {} "The baz value1")
                           (element :baz {} "The baz value2")
                           (element :baz {} "The baz value3")))))

    <?xml version="1.0" encoding="UTF-8"?>
    <foo foo-attr="foo value">
      <bar bar-attr="bar value">
        <baz>The baz value1</baz>
        <baz>The baz value2</baz>
        <baz>The baz value3</baz>
      </bar>
    </foo>

CDATA can be emitted:

    (emit-str (element :foo {}
                (cdata "<non><escaped><info><here>")))

    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo><![CDATA[<non><escaped><info><here>]]></foo>"

But will be read as regular character data:

    (parse-str (emit-str (element :foo {}
                 (cdata "<non><escaped><info><here>"))))

    #clojure.data.xml.Element{:tag :foo, :attrs {}, :content ("<non><escaped><info><here>")}

Comments can also be emitted:

    (emit-str (element :foo {}
                (xml-comment "Just a <comment> goes here")
                (element :bar {} "and another element")))

    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo><!--Just a <comment> goes here--><bar>and another element</bar></foo>"

But are ignored when read:

    (emit-str
      (parse-str
        (emit-str (element :foo {}
                    (xml-comment "Just a <comment> goes here")
                    (element :bar {} "and another element")))))

    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo><bar>and another element</bar></foo>"

## Namespace Support

XML Namespaced names (QNames) are encoded into clojure keywords, by percent-encoding the (XML) namespace: `{http://www.w3.org/1999/xhtml}head` is encoded in data.xml as `:http%3A%2F%2Fwww.w3.org%2F1999%2Fxhtml/head`.

Below is an example of parsing an XHTML document:

    (parse-str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
                <foo:html xmlns:foo=\"http://www.w3.org/1999/xhtml\"/>")

    #...Element{:tag :xmlns.http%3A%2F%2Fwww.w3.org%2F1999%2Fxhtml/html,
                :attrs {},
                :content ()}

Emitting namespaced XML is usually done by using `alias-uri` in combination with clojure's built-in `::kw-ns/shorthands`:

    (alias-uri 'xh "http://www.w3.org/1999/xhtml")

    (emit-str {:tag ::xh/html
               :content [{:tag ::xh/head} {:tag ::xh/body :content ["DOCUMENT"]}]})

    <?xml version="1.0" encoding="UTF-8"?>
    <a:html xmlns:a="http://www.w3.org/1999/xhtml">
      <a:head/>
      <a:body>DOCUMENT</a:body>
    </a:html>

It is also allowable to use `javax.xml.namespace.QName` instances, as well as strings with the informal `{ns}n` encoding.

    (emit-str {:tag (qname "http://www.w3.org/1999/xhtml" "html")})
    (emit-str {:tag "{http://www.w3.org/1999/xhtml}html"})

    <?xml version=\"1.0\" encoding=\"UTF-8\"?><a:html xmlns:a=\"http://www.w3.org/1999/xhtml\"></a:html>

### Namespace Prefixes

Prefixes are mostly an artifact of xml serialisation. They can be
customized by explicitly declaring them as attributes in the `xmlns`
kw-namespace:

    (emit-str (element (qname "http://www.w3.org/1999/xhtml" "title")
                       {:xmlns/foo "http://www.w3.org/1999/xhtml"}
                       "Example title"))
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo:title xmlns:foo=\"http://www.w3.org/1999/xhtml\">Example title</foo:title>"

Not specifying a namespace prefix will results in a prefix being generated:

    (emit-str (element ::xh/title
                       {}
                       "Example title"))

    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a:title xmlns:a=\"http://www.w3.org/1999/xhtml\">Example title</a:title>"

The above example auto assigns prefixes for the namespaces used. In
this case it was named `a` by the emitter. Emitting several nested
tags with the same namespace will use one prefix:

    (emit-str (element ::xh/html
                       {}
                       (element ::xh/head
                                {}
                                (element ::xh/title
                                         {}
                                         "Example title"))))

    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a:html xmlns:a=\"http://www.w3.org/1999/xhtml\"><a:head><a:title>Example title</a:title></a:head></a:html>"

Note that the jdk QName ignores namespace prefixes for equality, but allows to preserve them for emitting.

    (= (parse-str "<foo:title xmlns:foo=\"http://www.w3.org/1999/xhtml2\">Example title</foo:title>")
       (parse-str "<bar:title xmlns:bar=\"http://www.w3.org/1999/xhtml2\">Example title</bar:title>"))

In data.xml prefix mappings are (by default) retained in metadata on a tag record. If there is no metadata, new prefixes will be generated when emitting.

    (emit-str (parse-str "<foo:element xmlns:foo=\"FOO:\" />"))

## Location information as meta

By default the parser attaches location information as element meta,
 `:character-offset`, `:column-number` and `:line-number` are available under
 the `:clojure.data.xml/location-info` key:

    (deftest test-location-meta
      (let [input "<a><b/>\n<b/></a>"
            location-meta (comp :clojure.data.xml/location-info meta)]
        (is (= 1 (-> input parse-str location-meta :line-number)))

To elide location information, pass `:location-info false` to the parser:

    (parse-str your-input :location-info false)

## Clojurescript support

The Clojurescript implementation uses the same namespace as the Clojure one `clojure.data.xml`.

### Native DOM support

data.xml can directly work with native dom nodes.

- To parse into DOM objects, call parse with `:raw true`
- To use DOM objects like regular persistent maps, call `(extend-dom-as-data!)`.
  This extends the native dom node prototypes to Clojurescript collection protocols, such that you can treat them as data.xml parse trees.
- To coerce to native dom use `element-node`
- To coerce to records use `element-data`

### Missing Features, Patches Welcome

#### Streaming

data.xml on Clojurescript doesn't currently support streaming, hence only the `*-str` variants of `parse`/`emit` are implemented. Those are just wrappers for browser's native xml parsing/printing.

Pull parsing doesn't seem the right solution for Clojurescript, because when code cannot block, the parser has no way of waiting on its input. For this reason, parsing in Clojurescript cannot be based around `event-seq`.

Push parsing, on the other hand should not pose a problem, because when data arrives in a callback, it can be pushed on into the parser. Fortunately, clojure already has a nice push-based pendant for lazy sequences: transducers.

#### Utilities

Some utilities, like `process/*-xmlns`, `prxml/sexp-as-*`, `indent` aren't yet implemented.

#### Immutable updates for dom types

Make `extend-dom-as-data!` also support assoc, ... on dom nodes.

#### Feel free to pick a [ticket](http://dev.clojure.org/jira/secure/IssueNavigator.jspa?reset=true&jqlQuery=project+%3D+DXML+AND+status+in+%28Open%2C+%22In+Progress%22%2C+Reopened%29) to work on

## License

Licensed under the [Eclipse Public License](http://www.opensource.org/licenses/eclipse-1.0.php).

## Developer Information

* [GitHub project](https://github.com/clojure/data.xml)

* [Bug Tracker](http://dev.clojure.org/jira/browse/DXML)

* [Continuous Integration](http://build.clojure.org/job/data.xml/)

* [Compatibility Test Matrix](http://build.clojure.org/job/data.xml-test-matrix/)

## Contributing

All contributions need to be made via patches attached to tickets in
[JIRA](http://dev.clojure.org/jira/browse/DXML). Check the
[Contributing to Clojure](http://clojure.org/contributing) page for
more information.
