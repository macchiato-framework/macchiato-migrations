(ns macchiato.test.runner
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [macchiato.test.migrations.core-test]))

(doo-tests 'macchiato.test.migrations.core-test)
