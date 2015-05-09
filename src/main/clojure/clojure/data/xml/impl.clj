;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.impl
  "Shared private code for data.xml namespaces"
  {:author "Herwig Hochleitner"})

(defn- export-form [var-name]
  (let [vsym (symbol (name var-name))]
    `[(def ~vsym ~var-name)
      (alter-meta! (var ~vsym)
                   (constantly (assoc (meta (var ~var-name))
                                 :wrapped-by (var ~vsym))))]))

(defmacro export-api
  "This creates vars, that take their (local) name, value and metadata from another var"
  [& names]
  (cons 'do (mapcat export-form names)))
