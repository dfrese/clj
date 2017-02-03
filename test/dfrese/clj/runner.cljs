(ns dfrese.clj.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            dfrese.clj.functions-test
            dfrese.clj.values-test
            ))

(doo-tests 'dfrese.clj.functions-test
           'dfrese.clj.values-test
           )
