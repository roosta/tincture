(ns tincture.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tincture.core :as t]))

(deftest clamp
  (testing "Clamp function"
    (is (= (t/clamp 42 10 30) 30))
    (is (= (t/clamp 10 20 30) 20))
    (is (= (t/clamp 10 5 15) 10))))
