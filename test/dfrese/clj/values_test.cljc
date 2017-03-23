(ns dfrese.clj.values-test
  (:require #?(:cljs [cljs.test :refer-macros [deftest is testing]])
            #?(:clj [clojure.test :refer [deftest is testing]])
            [dfrese.clj.values :as v]))

(deftest tags-test
  (is (= (v/tagged :a 42) (v/tagged :a 42)))
  (is (not= (v/tagged :a 42) (v/tagged :a 21)))
  (is (not= (v/tagged :a 42) (v/tagged :b 42)))
  
  (is (v/tagged? (v/tagged :a 42)))
  (is (not (v/tagged? 42)))

  (is (= (v/tagger :a) (v/tagger :a)))
  (is (not= (v/tagger :b) (v/tagger :a)))
  (is (= ((v/tagger :b) 42) (v/tagged :b 42)))

  (is (= [:a 42] (v/untag (v/tagged :a 42))))
  (is (= [42 nil] (v/untag 42)))
  )
