# data.xml

'data.xml' is a Clojure library for reading and writing XML data. This
library is the successor to
[lazy-xml](http://clojure.github.com/clojure-contrib/lazy-xml-api.html).
data.xml has the following features:

* Parses XML documents into Clojure data structures
* Emits XML from Clojure data structures
* No additional dependencies if using 1.6
* Uses StAX internally
* lazy - should allow parsing and emitting of large XML documents

## Bugs

Please [data.xml JIRA](http://dev.clojure.org/jira/browse/DXML) for bug reports.

## Contributing

All contributions need to be made via patches attached to tickets in
[JIRA](http://dev.clojure.org/jira/browse/DXML). Check the
[Contributing to Clojure](http://clojure.org/contributing) page for
more information.

## Installation

### Maven
For Maven projects, add the following XML in your `pom.xml`'s `<dependencies>` section:

    <dependency>
	  <groupId>org.clojure</groupId>
	  <artifactId>data.xml</artifactId>
	  <version>0.0.2-SNAPSHOT</version>
     </dependency>

### Leiningen
Add the following to the `project.clj` dependencies:

    [org.clojure/data.xml "0.0.2-SNAPSHOT"]

## Examples

## License

Licensed under the [Eclipse Public License](http://www.opensource.org/licenses/eclipse-1.0.php).