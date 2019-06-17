(ns tincture.macros-test
  (:require [clojure.test :refer [deftest is testing]]
            [tincture.macros :as macros]))

(deftest load-json
  (testing "Loading arbitrary json via load-json fn"
    (is (= (#'tincture.macros/load-json "{\"a\":1,\"b\":2}")
           {:a 1 :b 2}))))

(deftest entry
  (testing "gradient entry function"
    (is (= (#'tincture.macros/entry {:name "test" :colors ["#fff" "#000"]})
           [:test ["#fff" "#000"]]))))

(deftest ui-gradients
  (testing "ui-gradient macro output by sampling"
    (let [gradients (macros/ui-gradients "gradients.json")]
      (is (= (:copper gradients)
             ["#B79891", "#94716B"]))
      (is (= (:noon-to-dusk gradients)
             ["#ff6e7f", "#bfe9ff"]))
      (is (= (:sunny-days gradients)
             ["#EDE574", "#E1F5C4"]))
      (is (= (:vanusa gradients)
             ["#DA4453", "#89216B"])))))
