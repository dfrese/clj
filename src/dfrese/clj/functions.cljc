(ns dfrese.clj.functions
  "Higher order functions that can replace the built-in clojure functions of the same name, but they are transparent and comparable.

   Unlike with the built-in functions, this means for example:

   `(= (partial f x) (partial f x))`  => true

   `(= (comp f g h) (comp f g h))`  => true
  "
  (:require [clojure.core :as c])
  (:refer-clojure :exclude [partial constantly comp juxt identity fnil]))

(defrecord ^:no-doc F-1
  [base base-arg]
  #?@(:cljs [IFn
             (-invoke [this a] (base base-arg a))])
  #?@(:clj [clojure.lang.IFn
            (invoke [this a] (base base-arg a))]))

(defrecord ^:no-doc F-n
  [base base-arg]
  #?@(:cljs [IFn
             (-invoke [this & args] (base base-arg args))])
  #?@(:clj [clojure.lang.IFn
            (invoke [this a0] (base base-arg [a0]))
            (invoke [this a0 a1] (base base-arg [a0 a1]))
            (invoke [this a0 a1 a2] (base base-arg [a0 a1 a2]))
            (invoke [this a0 a1 a2 a3] (base base-arg [a0 a1 a2 a3]))
            (invoke [this a0 a1 a2 a3 a4] (base base-arg [a0 a1 a2 a3 a4]))
            (invoke [this a0 a1 a2 a3 a4 a5] (base base-arg [a0 a1 a2 a3 a4 a5]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6] (base base-arg [a0 a1 a2 a3 a4 a5 a6]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18]))
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19] (base base-arg [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19]))
            
            (invoke [this a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 more] (base base-arg (concat [a0 a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19] more)))
            (applyTo [this args] (base base-arg args))]))

(defn- f? [base v]
  ;; instance? F v   ... slow in cljs, but ok in clj?
  (identical? (:base v) base))

(defn- f-n [f arg]
  (F-n. f arg))

(defn- f-1 [f arg]
  (F-1. f arg))

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
     (f-n partial- [f args]))))

(defn- constantly- [x args]
  x)

(defn constantly
  "Returns a function that takes any number of arguments and returns x."
  [x]
  (f-n constantly- x))

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
   (f-n comp- (mapcat decomp (cons f fs)))))

(defn- juxt- [fs args]
  (apply (apply c/juxt fs) args))

(defn juxt
  "Takes a set of functions and returns a fn that is the juxtaposition
  of those fns.  The returned fn takes a variable number of args, and
  returns a vector containing the result of applying each fn to the
  args (left-to-right).

  `((juxt a b c) x) => [(a x) (b x) (c x)]`"
  [f & fs]
  (f-n juxt- (cons f fs)))

(def ^{:doc "Returns its argument."} identity c/identity)

(defn- fnil-
  [[f & yargs] args]
  (apply (apply c/fnil f yargs) args))

(defn fnil
  "Takes a function f, and returns a function that calls f, replacing
  a nil first argument to f with the supplied value x. Higher arity
  versions can replace arguments in the second and third positions (y,
  z). Note that the function f can take any number of arguments, not
  just the one(s) being nil-patched."
  ([f x]
   (f-n fnil- [f x]))
  ([f x y]
   (f-n fnil- [f x y]))
  ([f x y z]
   (f-n fnil- [f x y z])))

;; TODO? some-fn every-pred

(defn- fsome- [f a]
  (when (some? a) (f a)))

(defn fsome
  "Returns a function of one argument, which calls `f` only when that
   argument is not nil and returns its result, and nil otherwise.
  
  `(when (some? a) (f a))`."
  [f]
  (f-1 fsome- f))
