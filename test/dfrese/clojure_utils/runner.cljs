(ns dfrese.clojure-utils.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            dfrese.clojure-utils.functions-test
            ))

(doo-tests 'dfrese.clojure-utils.functions-test
           )
