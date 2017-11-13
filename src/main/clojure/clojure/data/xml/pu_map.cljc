(ns clojure.data.xml.pu-map
  "Provides a bidirectional mapping for keeping track of prefix->uri mappings in xml namespaces.

  This has the semantics of a basic key -> multiple values map + two special features, both of which are dictated by the xml standard:

  - instead of a special dissoc, there is assoc to empty string or nil
  - there are two fixed, unique mappings:
    - \"xml\" <-> [\"http://www.w3.org/2000/xmlns/\"]
    - \"xmlns\" <-> [\"http://www.w3.org/XML/1998/namespace\"]"
  (:require [clojure.data.xml.name :as name]
            [clojure.string :as str]
            [clojure.core :as core])
  (:refer-clojure :exclude [assoc! dissoc! transient persistent! get assoc merge]))

(def prefix-map :p->u)
(def uri-map    :u->ps)

;; TODO replace this with a deftype for memory savings
(def EMPTY {:u->ps {name/xml-uri ["xml"]
                    name/xmlns-uri ["xmlns"]}
            :p->u {"xml" name/xml-uri
                   "xmlns" name/xmlns-uri}})

;; TODO implement valid? with internal consistency check

(defn transient [pu]
  (let [{:keys [u->ps p->u] :as pu*}
        (or pu EMPTY)]
    (assert (and u->ps p->u) (str "Not a pu-map " (pr-str pu*)))
    (core/assoc! (core/transient {})
                 :p->u (core/transient p->u)
                 :u->ps (core/transient u->ps))))

(defn persistent! [put]
  (core/persistent!
   (core/assoc! put
                :p->u (core/persistent! (core/get put :p->u))
                :u->ps (core/persistent! (core/get put :u->ps)))))

(defn- assoc-uri! [u->ps uri prefix]
  (core/assoc! u->ps uri
               (if-let [ps (core/get u->ps uri)]
                 (conj ps prefix)
                 [prefix])))

(defn- dissoc-uri! [u->ps uri prefix]
  (if-let [ps (seq (remove #{prefix} (core/get u->ps uri)))]
    (core/assoc! u->ps uri (vec ps))
    (core/dissoc! u->ps uri)))

(defn assoc! [{:as put :keys [p->u u->ps]} prefix uri]
  (name/legal-xmlns-binding! prefix uri)
  (let [prefix* (str prefix)
        prev-uri (core/get p->u prefix*)]
    (core/assoc! put
                 :p->u (if (str/blank? uri)
                         (core/dissoc! p->u prefix*)
                         (core/assoc! p->u prefix* uri))
                 :u->ps (if (str/blank? uri)
                          (dissoc-uri! u->ps prev-uri prefix*)
                          (cond
                            (= uri prev-uri) u->ps
                            (not prev-uri) (assoc-uri! u->ps uri prefix*)
                            :else (-> u->ps
                                      (dissoc-uri! prev-uri prefix*)
                                      (assoc-uri! uri prefix*)))))))

(defn get [{:keys [p->u]} prefix]
  (core/get p->u (str prefix)))

(defn get-prefixes [{:keys [u->ps]} uri]
  (core/get u->ps uri))

(def get-prefix (comp first get-prefixes))

(defn assoc [put & {:as kvs}]
  (persistent!
   (reduce-kv assoc! (transient put) kvs)))

(defn reduce-diff
  "A high-performance diffing operation, that reduces f over changed and removed prefixes"
  [f s
   {ppu :p->u}
   {pu :p->u}]
  (let [s (reduce-kv (fn [s p _]
                       (if (contains? pu p)
                         s (f s p "")))
                     s ppu)
        s (reduce-kv (fn [s p u]
                       (if (= u (core/get ppu p))
                         s (f s p u)))
                     s pu)]
    s))

(defn merge-prefix-map
  "Merge a prefix map into pu-map"
  [pu pm]
  (persistent! (reduce-kv assoc! (transient pu) pm)))

(defn merge
  "Merge two pu-maps, left to right"
  [pu {:keys [:p->u]}]
  (merge-prefix-map pu p->u))
