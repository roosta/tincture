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
    (is (= (t/index-of '(:a :b :c) :c) 2))))

(deftest pad
  (testing "Padding out a collection"
    (is (= (#'tincture.core/pad 5 :test)
           '(:test :test :test :test :test)))
    (is (= (#'tincture.core/pad 5 [:test :test])
           '(:test :test :test :test :test)))
    (is (= (#'tincture.core/pad 5 '(:test :test))
           '(:test :test :test :test :test)))))

(deftest create-transition
  (testing "Creating a CSS ready transition"
    (is (= (t/create-transition {:property [:opacity :transform]
                                 :duration 500
                                 :easing [:ease-in-cubic :ease-out-cubic]})
           "opacity 500ms cubic-bezier(.550, .055, .675, .19) 0ms, transform 500ms cubic-bezier(.215, .61, .355, 1) 0ms"))
    (is (= (t/create-transition {:property ["opacity" :transform]
                                 :delay 300
                                 :duration 500
                                 :easing [:ease-in-cubic :ease-out-cubic]})
           "opacity 500ms cubic-bezier(.550, .055, .675, .19) 300ms, transform 500ms cubic-bezier(.215, .61, .355, 1) 300ms"))
    (is (= (t/create-transition {:property :opacity
                                 :duration 300
                                 :delay [300 500]
                                 :easing :ease-in-cubic})
           "opacity 300ms cubic-bezier(.550, .055, .675, .19) 300ms"))
    (is (= (t/create-transition {:property :opacity
                                 :duration [300 500]
                                 :delay 200
                                 :easing [:ease-in-cubic :ease-out-cubic]})
           "opacity 300ms cubic-bezier(.550, .055, .675, .19) 200ms"))
    (is (= (t/create-transition {:property [:opacity "transform"]
                                 :duration [300 500]
                                 :delay 200
                                 :easing [:ease-in-cubic :ease-out-cubic]})
           "opacity 300ms cubic-bezier(.550, .055, .675, .19) 200ms, transform 500ms cubic-bezier(.215, .61, .355, 1) 200ms"))
    (is (= (t/create-transition {:property [:opacity :transform :width]
                                 :duration 500
                                 :easing [:ease-in-cubic :ease-out-cubic]})
           "opacity 500ms cubic-bezier(.550, .055, .675, .19) 0ms, transform 500ms cubic-bezier(.215, .61, .355, 1) 0ms, width 500ms cubic-bezier(.215, .61, .355, 1) 0ms"))
    (is (= (t/create-transition {:property [:opacity :transform :width]
                                 :duration [500 300]
                                 :easing [:ease-in-cubic :ease-out-cubic]})
           "opacity 500ms cubic-bezier(.550, .055, .675, .19) 0ms, transform 300ms cubic-bezier(.215, .61, .355, 1) 0ms, width 300ms cubic-bezier(.215, .61, .355, 1) 0ms"))))

(deftest box-shadow
  (testing "Creating box-shadow"
    (is (= (t/box-shadow 0)
           "none"))
    (is (= (t/box-shadow 5)
           "0px 3px 5px -1px rgba(0, 0, 0, 0.2),0px 5px 8px 0px rgba(0, 0, 0, 0.14),0px 1px 14px 0px rgba(0, 0, 0, 0.12)"))
    (is (= (t/box-shadow 24)
           "0px 11px 15px -7px rgba(0, 0, 0, 0.2),0px 24px 38px 3px rgba(0, 0, 0, 0.14),0px 9px 46px 8px rgba(0, 0, 0, 0.12)"))))
