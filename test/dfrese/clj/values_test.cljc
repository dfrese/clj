(ns dfrese.clj.values-test
  (:require #?(:cljs [cljs.test :refer-macros [deftest is testing]])
            #?(:clj [clojure.test :refer [deftest is testing]])
            [dfrese.clj.values :as v]))

(deftest tags-test
  (is (= (v/tag :a 42) (v/tag :a 42)))
  (is (not= (v/tag :a 42) (v/tag :a 21)))
  (is (not= (v/tag :a 42) (v/tag :b 42)))
  
  (is (v/tagged? (v/tag :a 42)))
  (is (not (v/tagged? 42)))

  (is (= (v/tagger :a) (v/tagger :a)))
  (is (not= (v/tagger :b) (v/tagger :a)))
  (is (= ((v/tagger :b) 42) (v/tag :b 42)))

  (is (= [:a 42] (v/untag (v/tag :a 42))))
  (is (= [42 nil] (v/untag 42)))
  )
