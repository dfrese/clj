(ns dfrese.clj.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            dfrese.clj.functions-test
            ))

(doo-tests 'dfrese.clj.functions-test
           )
