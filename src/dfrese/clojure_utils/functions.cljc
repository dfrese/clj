(ns dfrese.clojure-utils.functions
  "Higher order functions that can replace the built-in clojure functions of the same name, but they are transparent and comparable.

   Unlike with the built-in functions, this means for example:

   `(= (partial f x) (partial f x))`  => true

   `(= (comp f (comp g h)) (comp (comp f g) h))`  => true
  "
  (:require [clojure.core :as c])
  (:refer-clojure :exclude [partial constantly comp juxt identity]))

(defrecord ^:no-doc F
  [base base-arg]
  #?@(:cljs [IFn
             (-invoke [this & args] (base base-arg args))])
  #?@(:clj [clojure.lang.IFn
            (invoke [this a0] (apply this [a0]))
            (invoke [this a0 a1] (apply this [a0 a1]))
            ;; TODO: up to 20 args; really? (https://gist.github.com/devn/c52a7f5f7cdd45d772a9)
            (applyTo [this args] (base base-arg args))]))

(defn- f? [base v]
  ;; instance? F v   ... slow in cljs, but ok in clj?
  (identical? (:base v) base))

(defn- partial- [[f args] margs]
  (apply f (concat args margs)))

(defn partial
  "Takes a function f and fewer than the normal arguments to f, and
  returns a fn that takes a variable number of additional args. When
  called, the returned function calls f with args + additional args."
  ([f] f)
  ([f & args]
   (if (f? partial- f)
     (update f :base-arg (fn [[f args0]]
                           [f (concat args0 args)]))
     (F. partial- [f args]))))

(defn- constantly- [x args]
  x)

(defn constantly
  "Returns a function that takes any number of arguments and returns x."
  [x]
  (F. constantly- x))

(defn- comp- [fs args]
  (apply (apply c/comp fs) args))

(defn- decomp [f]
  (if (f? comp- f)
    (:base-arg f)
    [f]))

(defn comp
  "Takes a set of functions and returns a fn that is the composition
  of those fns.  The returned fn takes a variable number of args,
  applies the rightmost of fns to the args, the next
  fn (right-to-left) to the result, etc."
  ([] c/identity)
  ([f] f)
  ([f & fs]
   ;; removing everything after a constantly - would allow no impurity.
   (F. comp- (mapcat decomp (cons f fs)))))

(defn- juxt- [fs args]
  (apply vector (map #(apply % args) fs)))

(defn juxt
  "Takes a set of functions and returns a fn that is the juxtaposition
  of those fns.  The returned fn takes a variable number of args, and
  returns a vector containing the result of applying each fn to the
  args (left-to-right).
  `((juxt a b c) x) => [(a x) (b x) (c x)]`"
  [f & fs]
  ;; TODO: if all are const, the result is const?
  (F. juxt- (cons f fs)))

(def ^{:doc "Returns its argument."} identity c/identity)

;; TODO? fnil some-fn every-pred

;; comp? - compose, but short-circuit on nil
