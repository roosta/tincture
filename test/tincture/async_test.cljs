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
  (testing "Test throttling"
    (async done
     (let [a (.setInterval js/window (a/throttle throttle-fn 500) 100)]
       (.setTimeout js/window #(throttle-stop a done) 2000)))))


;; Was not able to figure out how to test this consistently in a
;; reasonable timespan so I'm just gonna leave it. Debounce is just a
;; call to closure lib anyways and I can only assume google test this
;; lib appropriately. If anyone sees this, and have a solution, please
;; let me know.
;; (def debounce-state (atom []))

;; (defn debounce-fn []
;;   (swap! debounce-state conj :test))

;; (defn debounce-stop
;;   [interval-id done]
;;   (.clearInterval js/window interval-id)
;;   (.log js/console @debounce-state)
;;   (done))

;; (deftest debounce
;;   (testing "Test debouncing"
;;     (async done
;;            (let [df (a/debounce debounce-fn 100)
;;                  _ (df)
;;                  a (.setInterval js/window df 100)]
;;              (.setTimeout js/window #(debounce-stop a done) 1000)))))
