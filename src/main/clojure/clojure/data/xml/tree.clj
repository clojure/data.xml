;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.data.xml.tree
  (:require [clojure.data.xml.protocols :refer
             [gen-event next-events]]
            [clojure.data.xml.event :refer
             [event-element event-node event-exit?]]))

(defn seq-tree
  "Takes a seq of events that logically represents
  a tree by each event being one of: enter-sub-tree event,
  exit-sub-tree event, or node event.

  Returns a lazy sequence whose first element is a sequence of
  sub-trees and whose remaining elements are events that are not
  siblings or descendants of the initial event.

  The given exit? function must return true for any exit-sub-tree
  event.  parent must be a function of two arguments: the first is an
  event, the second a sequence of nodes or subtrees that are children
  of the event.  parent must return nil or false if the event is not
  an enter-sub-tree event.  Any other return value will become
  a sub-tree of the output tree and should normally contain in some
  way the children passed as the second arg.  The node function is
  called with a single event arg on every event that is neither parent
  nor exit, and its return value will become a node of the output tree.

  (seq-tree #(when (= %1 :<) (vector %2)) #{:>} str
            [1 2 :< 3 :< 4 :> :> 5 :> 6])
  ;=> ((\"1\" \"2\" [(\"3\" [(\"4\")])] \"5\") 6)"
  [parent exit? node coll]
  (lazy-seq
   (when-let [[event] (seq coll)]
     (let [more (rest coll)]
       (if (exit? event)
         (cons nil more)
         (let [tree (seq-tree parent exit? node more)]
           (if-let [p (parent event (lazy-seq (first tree)))]
             (let [subtree (seq-tree parent exit? node (lazy-seq (rest tree)))]
               (cons (cons p (lazy-seq (first subtree)))
                     (lazy-seq (rest subtree))))
             (cons (cons (node event) (lazy-seq (first tree)))
                   (lazy-seq (rest tree))))))))))

;; # Break circular dependency of emitter-parser common infrastructure

;; "Parse" events off the in-memory representation

(defn flatten-elements
  "Flatten a collection of elements to an event seq"
  [elements]
  (when (seq elements)
    (lazy-seq
     (let [e (first elements)]
       (cons (gen-event e)
             (flatten-elements (next-events e (rest elements))))))))

;; "Emit" events to the in-memory representation

(defn event-tree
  "Returns a lazy tree of Element objects for the given seq of Event
  objects. See source-seq and parse."
  [events]
  (ffirst
   (seq-tree event-element event-exit? event-node events)))
