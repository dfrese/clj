(ns dfrese.clj.functions-test
  (:require #?(:cljs [cljs.test :refer-macros [deftest is testing]])
            #?(:clj [clojure.test :refer [deftest is testing]])
            [dfrese.clj.functions :as f]))

(deftest partial-test
  (is (= 44 ((f/partial + 42) 2)))
  (is (= (f/partial + 42) (f/partial + 42)))
  (is (not= (f/partial + 42) (f/partial + 21)))
  (is (not= (f/partial + 42) (f/partial - 42))))

(deftest constantly-test
  (is (= 42 ((f/constantly 42) 21)))
  (is (= (f/constantly 42) (f/constantly 42)))
  (is (not= (f/constantly 42) (f/constantly 21))))

(deftest comp-test
  (is (= 42 ((f/comp :a :b) {:b {:a 42}})))
  (is (= (f/comp :a :b) (f/comp :a :b)))
  (is (not= (f/comp :a :b) (f/comp :b :a))))

(deftest juxt-test
  (is (= [42 43] ((f/juxt identity inc) 42)))
  (is (= (f/juxt identity inc) (f/juxt identity inc))))

(deftest identity-test
  (identical? f/identity identity))

(deftest fsome-test
  (is (nil? ((f/fsome #(assert false)) nil)))
  (is (= 42 ((f/fsome inc) 41)))
  (is (= (f/fsome inc) (f/fsome inc))))
