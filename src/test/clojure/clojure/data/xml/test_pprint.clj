;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Tests for emit to print XML text."
      :author "Herwig Hochleitner"}
  clojure.data.xml.test-pprint
  (:require
   [clojure.test :refer :all]
   [clojure.data.xml :refer :all]))

(def xml
  "<foo><bar/></foo>")

(def indented-xml
  (str
   "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
   "\n"
   "<foo>
  <bar/>
</foo>
"))

(deftest test-indent
  (is (= indented-xml (indent-str (parse-str xml)))))

