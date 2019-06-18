(ns tincture.test-runner
  (:require [figwheel.main.testing :refer-macros [run-tests-async]]
            [tincture.core-test]
            [tincture.cssfns-test]
            [tincture.events-test]
            [tincture.subs-test]
            [tincture.gradient-test]))

(defn -main [& args]
  ;; this needs to be the last statement in the main function so that it can
  ;; return the value `[:figwheel.main.async-result/wait 10000]`
  (run-tests-async 10000))
