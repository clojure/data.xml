# data.xml

[data.xml](https://github.com/clojure/data.xml) is a Clojure library for reading and writing XML data. This
library is the successor to
[lazy-xml](http://clojure.github.com/clojure-contrib/lazy-xml-api.html).
data.xml has the following features:

* Parses XML documents into Clojure data structures
* Emits XML from Clojure data structures
* No additional dependencies if using 1.6
* Uses StAX internally
* lazy - should allow parsing and emitting of large XML documents

## Bugs

Please report bugs using JIRA [here](http://dev.clojure.org/jira/browse/DXML).

## Installation

Latest stable release: 0.0.8

* [All Released Versions](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.clojure%22%20AND%20a%3A%22data.xml%22)

* [Development Snapshot Versions](https://oss.sonatype.org/index.html#nexus-search;gav~org.clojure~data.xml~~~)

### Maven
For Maven projects, add the following XML in your `pom.xml`'s `<dependencies>` section:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>data.xml</artifactId>
      <version>0.0.8</version>
     </dependency>

### Leiningen
Add the following to the `project.clj` dependencies:

    [org.clojure/data.xml "0.0.8"]

## Installation - Beta

Latest beta release: 0.1.0-beta1

* [All Released Versions](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.clojure%22%20AND%20a%3A%22data.xml%22)

* [Development Snapshot Versions](https://oss.sonatype.org/index.html#nexus-search;gav~org.clojure~data.xml~~~)

### Maven
For Maven projects, add the following XML in your `pom.xml`'s `<dependencies>` section:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>data.xml</artifactId>
      <version>0.1.0-beta1</version>
     </dependency>

### Leiningen
Add the following to the `project.clj` dependencies:

    [org.clojure/data.xml "0.1.0-beta1"]

## Examples

The examples below assume you have added a `use` for data.xml:

    (use 'clojure.data.xml)

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
functions or the element function used below, and written using a
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

Generated API docs for data.xml are available [here](http://clojure.github.com/data.xml).

## Namespace Support

Parsing and emitting XML namespaces are supported and use the JDK built-in
QName class. Below is an example of parsing an XHTML document:

    (parse-str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
                <foo:html xmlns:foo=\"http://www.w3.org/1999/xhtml\">
                  <foo:head>
                    <foo:title>Example title</foo:title>
                  </foo:head>
                  <foo:body>
                    <foo:p>Example Paragraph</foo:p>
                  </foo:body>
                </foo:html>")

    #...Element{:tag #object[javax.xml.namespace.QName 0x68651690 "{http://www.w3.org/1999/xhtml}html"],
               :attrs {},
               :content (...)}

The above data structures are verbose. Each tag that includes a
namespace will include that in it's QName:

    #...Element{:tag #object[javax.xml.namespace.QName 0x7255cde4 "{http://www.w3.org/1999/xhtml}title"],
                :attrs {},
                :content ("Example title")}

This is the most basic representation of the parsed document that
includes namespaces. Emitting namespace information in a similar way
can use the `qname` function:

    (element (qname "title" "http://www.w3.org/1999/xhtml" "foo")
             {}
             "Example title")

    #...Element{:tag #object[javax.xml.namespace.QName 0x22a22c0e "{title}http://www.w3.org/1999/xhtml"],
                :attrs {},
                :content ("Example title")}

The emitting code above is similarly verbose. By declaring the
namespaces that will be parsed or emitted up-front via `declare-ns`,
these representations can be made much more succinct:

    (declare-ns "xml.html" "http://www.w3.org/1999/xhtml")
    (parse-str "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
                <foo:html xmlns:foo=\"http://www.w3.org/1999/xhtml\">
                  <foo:head>
                    <foo:title>Example title</foo:title>
                  </foo:head>
                  <foo:body>
                    <foo:p>Example Paragraph</foo:p>
                  </foo:body>
                </foo:html>")

    #...Element{:tag :xml.html/html, :attrs {}, :content (...)}

In the above example, all tags use the namespace
`http://www.w3.org/1999/xhtml`. That namespace is declared as "xml.html" in
Clojure. All the tags parsed from that document will be
`:xml.html/the-tag-name`. Note that `xml.html` is not related to the namespace
prefix declared in the document (`foo` in this example). `xml.html` is just
a way to refer to names in the `http://www.w3.org/1999/xhtml` namespace
with a keyword.

The declared namespace can also be used in combination with the
regular clojure namespace aliasing mechnism. When constructing XML
documents, this leads pretty succinct representation with alias-aware keywords.

    (alias-ns :xh :xml.html) ;; alias-ns will create the target ns - xml.html - so that it can be aliased into the current ns
    (emit-str (element ::xh/title
                       {:xmlns/foo "http://www.w3.org/1999/xhtml"}
                       "Example title"))

    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo:title xmlns:foo=\"http://www.w3.org/1999/xhtml\">Example title</foo:title>"

Take note, that the keyword-namespaces `:xmlns/...` as well as
`:xml/...` are predefined to refer to `http://www.w3.org/2000/xmlns/`
and `http://www.w3.org/XML/1998/namespace` respectively.

Because keywords interact with clojure's namespace - aliasing
mechanism, applications can choose descriptive names in `declare-ns`.

### Namespace Prefixes

Prefixes are mostly an artifact of xml serialisation. They can be
customized by explicitly declaring them as attributes in the `xmlns`
kw-namespace:

    (emit-str (element (qname "http://www.w3.org/1999/xhtml" "title")
                       {:xmlns/foo "http://www.w3.org/1999/xhtml"}
                       "Example title"))
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo:title xmlns:foo=\"http://www.w3.org/1999/xhtml\">Example title</foo:title>"

Not specifying a namespace prefix will results in a prefix being generated:

    ;; Assumes (declare-ns "xml.html" "http://www.w3.org/1999/xhtml") and (alias-ns :xh :xml.html)
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

Note that the Java QName does not consider namespace prefixes when
checking equality. Similarly constructing QNames from string
representations does not preserve prefixes. Prefixes are treated
similarly in data.xml. Prefixes are currently represented as metadata
on the elements. This preserves the same equality behavior that QNames
have:

    (= (parse-str "<foo:title xmlns:foo=\"http://www.w3.org/1999/xhtml2\">Example title</foo:title>")
       (parse-str "<bar:title xmlns:bar=\"http://www.w3.org/1999/xhtml2\">Example title</bar:title>"))

Removing the metadata will cause the elements to not have a prefix,
which is still correct, but will cause new prefixes to be generated
when the document is emitted.

## Location information as meta

By default the parser attaches location information as element meta,
 `:character-offset`, `:column-number` and `:line-number` are available under
 the `:clojure.data.xml/location-info` key:

    (deftest test-location-meta
      (let [input "<a><b/>\n<b/></a>"
            location-meta (comp :clojure.data.xml/location-info meta)]
        (is (= 1 (-> input parse-str location-meta :line-number)))
        
If you do not want to see the location info for any reason use an option to
prevent that:

    (parse-str your-input :location-info false)

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
