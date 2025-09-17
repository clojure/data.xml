## 0.2.0-alpha10

- Fix reflection warnings in clojure.data.xml.name (DXML-66)

## 0.2.0-alpha9

- Update parent pom, depend on Clojure 1.9.0

## 0.2.0-alpha8

- When parsing, only include namespace in tags when :namespace-aware is true

## 0.2.0-alpha7

- Replace data.codec with using Base64, now in the JDK
- Opt out of namespace awareness in the event-seq function by passing :namespace-aware mapped to a truthy value in the opts map

## 0.2.0-alpha6

- ClojureScript implementation fixes and tests

## 0.2.0-alpha5

- Fix error check for builtin prefixes DXML-49
- Remove reflection cases DXML-46

## 0.2.0-alpha3

- Minimum requirement is now clojure 1.7.0
- Print newline after preamble when pretty-printing (DXML-35)
- Serialize built-in data types in XML Schema (DXML-27)
- Reimplement namespace context tracking, due to bug in JDK
- Various fixes in documentation and error messages (DXML-39)
- Emit empty tags for elements with no content (DXML-25)
- Add clojure.data.xml/element? predicate
- Support empty protocol function on Element deftypes (DXML-44)
- Reflection cleanup (DXML-42)

## 0.2.0-alpha2

- qname function now returns canonical (keyword) names
- Remove QName defrecord from Clojurescript
- Rename canonical-name to as-qname
- Remove to-qname
- xml nodes now implement map equality

## 0.2.0-alpha1

- Define uniform mapping of xml namespaces to clojure namespaces via percent-encoding
- Remove declare-ns and alias-ns
- Introduce alias-uri
- Clojurescript support
- data.xml now requires Clojure 1.5.0+ (due to percent-sign in keywords)
- Preserve whitespace by default

## 0.1.0-beta3

- Fix emitter to keep non-namespaced xml names out of any set default namespace
- Add support for location info in parser

## 0.1.0-beta2

- Add support for emitting DOCTYPEs (DXML-10)
- Fix issue emitting sibling namespaces (DXML-33)
- Fix issue printing defaulted namespaces (DXML-30)

## 0.1.0-beta1

- Add support for XML namespaces (DXML-4)
- Fix pull-seq so it produces character events that work with emit-events (DXML-28)
- Removed docs and references to JDK 1.5, data.xml now requires 1.6+
- data.xml now requires Clojure 1.4.0+

## 0.0.8

- Remove relection warnings in emit-cdata (DXML-16)
- Added an EPL license file (DXML-19)
- Fixed bug in the handling of CData end tags (DXML-17)
- Added support for emitting booleans and numbers (DXML-14)

## 0.0.7

- Fixed bug with args to the indentation function (DXML-7)
- Strings now supported as tag names, previously was only kewords (DXML-8)
- Add CDATA and comments support to sexp-as-element (DXML-11)
- data.xml now properly handles CDATA records that contain an embedded ]]>
  by breaking it into two CDATA sections (DXML-12)
