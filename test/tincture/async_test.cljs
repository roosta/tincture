(ns tincture.async-test
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [tincture.async :as a]))



(def throttle-state (atom []))

(defn throttle-fn
  []
  (swap! throttle-state conj :test))

(defn throttle-stop
  [interval-id done]
  (.clearInterval js/window interval-id)
  (is (= @throttle-state [:test :test :test :test]))
  (done))


(deftest throttle
  (testing "Test that throttle function works"
    (async done
     (let [a (.setInterval js/window (a/throttle throttle-fn 500) 100)]
       (.setTimeout js/window #(throttle-stop a done) 2000)))
    )
  )
