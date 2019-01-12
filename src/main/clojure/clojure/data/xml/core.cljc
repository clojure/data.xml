(ns clojure.data.xml.core
  #?(:cljs (:require-macros clojure.data.xml.core)))

#?
(:clj
 (defmacro code-gen [plainsyms gensyms expr]
   (clojure.core/require (clojure.core/ns-name clojure.core/*ns*))
   (eval `(let ~(-> []
                    (into
                     (mapcat (juxt identity #(list 'quote %)))
                     plainsyms)
                    (into
                     (mapcat (juxt identity #(list 'quote (gensym (str %)))))
                     gensyms))
            ~expr))))

(defn unwrap-reduced [o]
  (if (reduced? o)
    @o o))

(defn wrap-reduced [o]
  (if (reduced? o)
    (reduced o)
    o))

(defn sym [& parts]
  (symbol (apply str parts)))

(defn completing-kxf
  ([kxf] (completing-kxf kxf identity))
  ([kxf cf]
   (fn
     ([s] (cf s))
     ([s k v] (kxf s k v)))))

(defn juxt-kv [key-f val-f]
  (fn [kxf]
    (completing-kxf #(kxf %1 (key-f %2 %3) (val-f %2 %3)) kxf)))

(defn xf-kxf [xf]
  (completing-kxf #(xf %1 [%2 %3]) xf))

(defn split [kxf]
  (completing #(kxf %1 %2 %2) kxf))

(defn juxt-kxf [key-f val-f]
  (fn [kxf]
    (completing-kxf #(kxf %1 (key-f %2) (val-f %3)) kxf)))

(defn juxt-xf [key-f val-f]
  (comp split (juxt-kxf key-f val-f)))

(defn kv-key [k v] k)
(defn kv-val [k v] v)

(def kxf-xf (juxt-xf #(nth % 0) #(nth % 1)))

(defn transduce-k [kxform f init kv-coll]
  (let [xf (kxform f)]
    (xf
     (reduce-kv xf init kv-coll))))

(defn kv-eduction [& kxforms]
  (transduce-k
   (apply comp (butlast kxforms))
   (completing-kxf assoc! persistent!)
   (transient {}) (last kxforms)))

(defn kv-from-coll [& kxforms]
  (transduce
   (apply comp (butlast kxforms))
   (completing-kxf assoc! persistent!)
   (transient {}) (last kxforms)))

(defn map-vals [f]
  (juxt-kv kv-key (comp f kv-val)))

(defn map-keys [f]
  (juxt-kv (comp f kv-key) kv-val))

(def swap-kv (juxt-kv kv-val kv-key))

(defn index-by [f]
  (juxt-xf f identity))

(kv-eduction
 (map-vals #(list "Hi" %))
 (kv-from-coll
  (index-by type)
  ["a" 1 :foo]))
