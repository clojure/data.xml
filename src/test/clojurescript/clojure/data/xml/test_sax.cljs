(ns clojure.data.xml.test-sax
  (:require [clojure.test :as t :refer-macros [deftest testing is are]]
            [clojure.data.xml.js.parse :as parse]
            [clojure.data.xml.push-handler :as push-handler]
            [clojure.data.xml.tree :as tree]
            [clojure.data.xml :as xml]
            [cljs.reader :refer [read-string]]
            [clojure.data.xml.name :as name]
            [clojure.data.xml.node :as node]))

(deftest test-basics
  (is (= node/Element
         (type #xml/element {:tag :a :content []}))))


(deftest init
  (is (= [(read-string
           {:readers {'xml/ns name/uri-symbol
                      'xml/element node/tagged-element}}
           "#xml/element
                     {:tag :root,
                      :attrs {:foo \"bar\", :xmlns.GOO%3A/roo \"ra\"},
                      :content [\"lalala\" \"foo GAARR bar\" #xml/element{:tag :xmlns.GOO%3A/gaga} \"  la  la \"]}")]
         [#xml/element
          {:tag :root,
           :attrs {:foo "bar", :xmlns.GOO%3A/roo "ra"},
           :content ["lalala" "foo GAARR bar" #xml/element{:tag :xmlns.GOO%3A/gaga} "  la  la "]}]
         [(node/tagged-element
           {:tag :root,
            :attrs {:foo "bar", :xmlns.GOO%3A/roo "ra"},
            :content ["lalala" "foo GAARR bar" #xml/element{:tag :xmlns.GOO%3A/gaga} "  la  la "]})]
         (transduce
          (parse/parser-xf {})
          tree/push-handler
          (list (transient []))
          (list "<roo"
                "t xmlns:a=\"GOO:\" foo=\"bar\" "
                "a:roo=\"ra\">lalala<![CDATA[foo GAARR "
                "bar]]><a:gaga/><!--  la  la --></root"
                ">")))))
