(ns dfrese.clj.values
  (:require [dfrese.clj.functions :as f]))

;; TODO: look at clojure.core.match

(defrecord ^:no-doc Tagged [tag value])

(defn tagged
  "Returns `v` tagged with `tag`."
  [tag v]
  (Tagged. tag v))

(defn tagged?
  "Returns true if `v` is a tagged value."
  [v]
  (instance? Tagged v))

(defn tagger
  "Returns a function that tags any value with tag `t`."
  [t]
  (f/partial tagged t))

(defn untag
  "Returns a tuple `[tag value]`, if `v` is a [[tagged?]] value, or `[v nil]` otherwise.

  Note the order for untagged values, `[v nil]`, should simplify
  treating untagged values like a tag. Similar to enumeration types
  with and without associated values in other languages."
  [v]
  (if (tagged? v)
    [(:tag v) (:value v)]
    [v nil]))

;; TODO: a 'case-tag' macro?
