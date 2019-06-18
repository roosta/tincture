(ns tincture.grid-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [garden.core :refer [css]]
            [tincture.grid :as g]))

(deftest up
  (testing "Up breakpoint function"
    (is (= (css {:pretty-print? false} [:.test (g/up :xs)])
           ".test{min-width:0px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :sm)])
           ".test{min-width:600px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :md)])
           ".test{min-width:960px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :md)])
           ".test{min-width:960px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :lg)])
           ".test{min-width:1280px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :xl)])
           ".test{min-width:1920px}"))))

(deftest down
  (testing "Down breakpoint function"
    (is (= (css {:pretty-print? false} [:.test (g/down :xs)])
           ".test{max-width:599.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :sm)])
           ".test{max-width:959.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :md)])
           ".test{max-width:1279.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :lg)])
           ".test{max-width:1919.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :xl)])
           ".test{min-width:0px}"))
    (is (= (g/down :xl) (g/up :xs)))))

(deftest xs-grid
  (testing "Generating grid by passing sample breakpoints"
    (let [grid (#'tincture.grid/generate-grid :xs)
          [_ xs-1] (first (filter (fn [[kw _]] (= kw :.grid-xs-1)) grid))
          [_ xs-2] (first (filter (fn [[kw _]] (= kw :.grid-xs-2)) grid))
          [_ xs-3] (first (filter (fn [[kw _]] (= kw :.grid-xs-3)) grid))
          [_ xs-4] (first (filter (fn [[kw _]] (= kw :.grid-xs-4)) grid))
          [_ xs-5] (first (filter (fn [[kw _]] (= kw :.grid-xs-5)) grid))
          [_ xs-6] (first (filter (fn [[kw _]] (= kw :.grid-xs-6)) grid))
          [_ xs-7] (first (filter (fn [[kw _]] (= kw :.grid-xs-7)) grid))
          [_ xs-8] (first (filter (fn [[kw _]] (= kw :.grid-xs-8)) grid))
          [_ xs-9] (first (filter (fn [[kw _]] (= kw :.grid-xs-9)) grid))
          [_ xs-10] (first (filter (fn [[kw _]] (= kw :.grid-xs-10)) grid))
          [_ xs-11] (first (filter (fn [[kw _]] (= kw :.grid-xs-11)) grid))
          [_ xs-12] (first (filter (fn [[kw _]] (= kw :.grid-xs-12)) grid))
          [_ xs-auto] (first (filter (fn [[kw _]] (= kw :.grid-xs-auto)) grid))
          [_ xs-true] (first (filter (fn [[kw _]] (= kw :.grid-xs-true)) grid))]

      ;; xs-1
      (is (= (:flex-basis xs-1) "8.333333%"))
      (is (= (:flex-grow xs-1) 0))
      (is (= (:max-width xs-1) "8.333333%"))

      ;; xs-2
      (is (= (:flex-basis xs-2) "16.666667%"))
      (is (= (:flex-grow xs-2) 0))
      (is (= (:max-width xs-2) "16.666667%"))

      ;; xs-3
      (is (= (:flex-basis xs-3) "25%"))
      (is (= (:flex-grow xs-3) 0))
      (is (= (:max-width xs-3) "25%"))

      ;; xs-4
      (is (= (:flex-basis xs-4) "33.333333%"))
      (is (= (:flex-grow xs-4) 0))
      (is (= (:max-width xs-4) "33.333333%"))

      ;; xs-5
      (is (= (:flex-basis xs-5) "41.666667%"))
      (is (= (:flex-grow xs-5) 0))
      (is (= (:max-width xs-5) "41.666667%"))

      ;; xs-6
      (is (= (:flex-basis xs-6) "50%"))
      (is (= (:flex-grow xs-6) 0))
      (is (= (:max-width xs-6) "50%"))

      ;; xs-7
      (is (= (:flex-basis xs-7) "58.333333%"))
      (is (= (:flex-grow xs-7) 0))
      (is (= (:max-width xs-7) "58.333333%"))

      ;; xs-8
      (is (= (:flex-basis xs-8) "66.666667%"))
      (is (= (:flex-grow xs-8) 0))
      (is (= (:max-width xs-8) "66.666667%"))

      ;; xs-9
      (is (= (:flex-basis xs-9) "75%"))
      (is (= (:flex-grow xs-9) 0))
      (is (= (:max-width xs-9) "75%"))

      ;; xs-10
      (is (= (:flex-basis xs-10) "83.333333%"))
      (is (= (:flex-grow xs-10) 0))
      (is (= (:max-width xs-10) "83.333333%"))

      ;; xs-11
      (is (= (:flex-basis xs-11) "91.666667%"))
      (is (= (:flex-grow xs-11) 0))
      (is (= (:max-width xs-11) "91.666667%"))

      ;; xs-12
      (is (= (:flex-basis xs-12) "100%"))
      (is (= (:flex-grow xs-12) 0))
      (is (= (:max-width xs-12) "100%"))

      ;; xs-auto
      (is (= (:flex-basis xs-auto) "auto"))
      (is (= (:flex-grow xs-auto) 0))
      (is (= (:max-width xs-auto) "none"))

      ;; xs-true
      (is (= (:flex-basis xs-true) "0"))
      (is (= (:flex-grow xs-true) 0))
      (is (= (:max-width xs-true) "100%")))))
