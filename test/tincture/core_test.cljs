(ns tincture.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tincture.core :as t]))

(deftest clamp
  (testing "Clamp function"
    (is (= (t/clamp 42 10 30) 30))
    (is (= (t/clamp 10 20 30) 20))
    (is (= (t/clamp 10 5 15) 10))))

(deftest index-of
  (testing "Get index from random collections using 'index-of'"
    (is (= (t/index-of [1 2 3 4] 3) 2))
    (is (= (t/index-of #{:a :b :c} :b) 1))
    (is (= (t/index-of {:a 1 :b 2 :c 3} :a) 0))
    ))
