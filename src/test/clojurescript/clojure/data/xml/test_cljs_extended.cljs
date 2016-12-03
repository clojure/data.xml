(ns clojure.data.xml.test-cljs-extended
  (:require
   [cljs.test :as test :refer [deftest is are]]
   [clojure.data.xml :as xml :refer [parse-str emit-str element element-data element-node]]
   [clojure.data.xml.node :as node]
   [clojure.data.xml.js.dom :as dom]))

(comment

  (= (xml/element :foo)
     (xml/element-node (xml/element :foo)))
  (= (xml/element-node (xml/element :foo))
     (xml/element :foo))

  (= (xml/element-node (xml/element :foo))
     {:tag :foo :attrs {} :content []})

  (= {:tag :foo :attrs {} :content []}
     (xml/element-node (xml/element :foo)))

  (= {:lala "1" :oo "a"} (:attrs (xml/element-node (xml/element :foo {:lala "1" :oo "a"}))))
  (= {} (:attrs (xml/element-node (xml/element :foo))))

  (= ["a"] (:content (xml/element-node (xml/element :foo {} "a"))))

  (= (xml/element :foo {} "a")
     (xml/element-node (xml/element :foo {} "a")))

  (= (xml/element :foo {} "a")
     {:tag :foo :attrs {} :content ["a"]})

  (= (xml/element :foo {} "a")
     (xml/element :foo {} "a"))

  (= () [])

  ( (:content (xml/element-node (xml/element :foo))))

  (= ["a"] (dom/node-list ["a"]))
  (= [] (dom/node-list []))

  PersistentArrayMap
  PersistentVector

  (xml/emit-str (xml/element "{DAV:}name" {}))
  (xml/parse-str "<foo xmlns=\"DAV:\"/>")
  (meta (xml/parse-str "<foo xmlns=\"DAV:\"/>"))

  (= (element-node (xml/element :xmlns.DAV%3A/foo))
     (xml/parse-str "<foo xmlns=\"DAV:\"/>" :raw true)
     )

  (.log js/console (element-node (xml/element :xmlns.DAV%3A/foo)))
  (xml/emit-str (element-node (xml/element :xmlns.DAV%3A/foo)))
  (xml/emit-str (xml/parse-str "<foo xmlns=\"DAV:\"/>" :raw true))
  
  )

(deftest extended-equalities
  (are [dxml xml] (do (is (= dxml (xml/parse-str xml :raw true)))
                      (is (= (element-node dxml) (xml/parse-str (xml/emit-str dxml))))
                      (is (= (element-node dxml) (xml/parse-str xml :raw true)))
                      (is (= (element-node dxml) (xml/parse-str (xml/emit-str dxml) :raw true))))
    (xml/element :foo) "<foo/>"
    (xml/element :xmlns.DAV%3A/foo) "<foo xmlns=\"DAV:\"/>"))
