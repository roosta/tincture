(ns tincture.test-runner
  (:require  [clojure.test :refer [deftest]]
             [clj-chrome-devtools.cljs.test :refer [build-and-test]]))

(deftest test-runner
  (build-and-test "test" '[tincture.core-test]))
