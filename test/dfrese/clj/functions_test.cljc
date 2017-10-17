(ns dfrese.clj.functions-test
  (:require #?(:cljs [cljs.test :refer-macros [deftest is testing]])
            #?(:clj [clojure.test :refer [deftest is testing]])
            [dfrese.clj.functions :as f]))

(deftest partial-test
  (is (= 44 ((f/partial + 42) 2)))
  (is (= 44 ((f/partial + 40) 2 2)))
  (is (= + (f/partial +)))
  (is (= 42 ((f/partial (f/partial + 18) 20) 4)))
  (is (= (f/partial + 42) (f/partial + 42)))
  (is (not= (f/partial + 42) (f/partial + 21)))
  (is (not= (f/partial + 42) (f/partial - 42)))

  (is (=  2 ((f/partial + 1) 1)))
  (is (=  3 ((f/partial + 1) 1 1)))
  (is (=  4 ((f/partial + 1) 1 1 1)))
  (is (=  5 ((f/partial + 1) 1 1 1 1)))
  (is (=  6 ((f/partial + 1) 1 1 1 1 1)))
  (is (=  7 ((f/partial + 1) 1 1 1 1 1 1)))
  (is (=  8 ((f/partial + 1) 1 1 1 1 1 1 1)))
  (is (=  9 ((f/partial + 1) 1 1 1 1 1 1 1 1)))
  (is (= 10 ((f/partial + 1) 1 1 1 1 1 1 1 1 1)))
  (is (= 11 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1)))
  (is (= 12 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 13 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 14 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 15 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 16 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 17 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 18 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 19 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 20 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 21 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 22 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 23 ((f/partial + 1) 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)))
  (is (= 24 (apply (f/partial + 1) [1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1])))
  )

(deftest constantly-test
  (is (= 42 ((f/constantly 42) 21)))
  (is (= (f/constantly 42) (f/constantly 42)))
  (is (not= (f/constantly 42) (f/constantly 21))))

(deftest comp-test
  (is (= 42 ((f/comp :a :b) {:b {:a 42}})))
  (is (= (f/comp :a :b) (f/comp :a :b)))
  (is (= 42 ((f/comp (f/comp #(+ 10 %) #(+ 5 %)) #(+ 12 %)) 15)))
  (is (= 42 ((f/comp) 42)))
  (is (= + (f/comp +)))
  (is (not= (f/comp :a :b) (f/comp :b :a))))

(deftest juxt-test
  (is (= [42 43] ((f/juxt identity inc) 42)))
  (is (= (f/juxt identity inc) (f/juxt identity inc))))

(deftest identity-test
  (identical? f/identity identity))

(deftest fnil-test
  (= (f/fnil :a 1) (f/fnil :a 1))
  (= [:a] ((f/fnil vector :a) nil))
  (= [42] ((f/fnil vector :a) 42))
  (= [:a 42] ((f/fnil vector :a :b) nil 42))
  (= [:a 42 :c] ((f/fnil vector :a :b :c) nil 42 nil)))

(deftest fsome-test
  (is (nil? ((f/fsome #(assert false)) nil)))
  (is (= 42 ((f/fsome inc) 41)))
  (is (= (f/fsome inc) (f/fsome inc))))
