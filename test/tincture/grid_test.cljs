(ns tincture.grid-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [herb.core :refer-macros [<class]]
            [garden.core :refer [css]]
            [garden.units :refer [px percent]]
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
  (testing "xs sized grid"
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
      (is (= (:flex-basis xs-true) 0))
      (is (= (:flex-grow xs-true) 1))
      (is (= (:max-width xs-true) "100%"))
      )))

(deftest sm-grid
  (testing "sm sized grid"
    (let [query (-> (#'tincture.grid/generate-grid :sm) :value :media-queries)
          grid (-> (#'tincture.grid/generate-grid :sm) :value :rules first)
          [_ sm-1] (first (filter (fn [[kw _]] (= kw :.grid-sm-1)) grid))
          [_ sm-2] (first (filter (fn [[kw _]] (= kw :.grid-sm-2)) grid))
          [_ sm-3] (first (filter (fn [[kw _]] (= kw :.grid-sm-3)) grid))
          [_ sm-4] (first (filter (fn [[kw _]] (= kw :.grid-sm-4)) grid))
          [_ sm-5] (first (filter (fn [[kw _]] (= kw :.grid-sm-5)) grid))
          [_ sm-6] (first (filter (fn [[kw _]] (= kw :.grid-sm-6)) grid))
          [_ sm-7] (first (filter (fn [[kw _]] (= kw :.grid-sm-7)) grid))
          [_ sm-8] (first (filter (fn [[kw _]] (= kw :.grid-sm-8)) grid))
          [_ sm-9] (first (filter (fn [[kw _]] (= kw :.grid-sm-9)) grid))
          [_ sm-10] (first (filter (fn [[kw _]] (= kw :.grid-sm-10)) grid))
          [_ sm-11] (first (filter (fn [[kw _]] (= kw :.grid-sm-11)) grid))
          [_ sm-12] (first (filter (fn [[kw _]] (= kw :.grid-sm-12)) grid))
          [_ sm-auto] (first (filter (fn [[kw _]] (= kw :.grid-sm-auto)) grid))
          [_ sm-true] (first (filter (fn [[kw _]] (= kw :.grid-sm-true)) grid))]

      ;; media query
      (is (= (-> query :min-width :magnitude) 600))
      (is (= (-> query :min-width :unit) :px))

      ;; sm-1
      (is (= (:flex-basis sm-1) "8.333333%"))
      (is (= (:flex-grow sm-1) 0))
      (is (= (:max-width sm-1) "8.333333%"))

      ;; sm-2
      (is (= (:flex-basis sm-2) "16.666667%"))
      (is (= (:flex-grow sm-2) 0))
      (is (= (:max-width sm-2) "16.666667%"))

      ;; sm-3
      (is (= (:flex-basis sm-3) "25%"))
      (is (= (:flex-grow sm-3) 0))
      (is (= (:max-width sm-3) "25%"))

      ;; sm-4
      (is (= (:flex-basis sm-4) "33.333333%"))
      (is (= (:flex-grow sm-4) 0))
      (is (= (:max-width sm-4) "33.333333%"))

      ;; sm-5
      (is (= (:flex-basis sm-5) "41.666667%"))
      (is (= (:flex-grow sm-5) 0))
      (is (= (:max-width sm-5) "41.666667%"))

      ;; sm-6
      (is (= (:flex-basis sm-6) "50%"))
      (is (= (:flex-grow sm-6) 0))
      (is (= (:max-width sm-6) "50%"))

      ;; sm-7
      (is (= (:flex-basis sm-7) "58.333333%"))
      (is (= (:flex-grow sm-7) 0))
      (is (= (:max-width sm-7) "58.333333%"))

      ;; sm-8
      (is (= (:flex-basis sm-8) "66.666667%"))
      (is (= (:flex-grow sm-8) 0))
      (is (= (:max-width sm-8) "66.666667%"))

      ;; sm-9
      (is (= (:flex-basis sm-9) "75%"))
      (is (= (:flex-grow sm-9) 0))
      (is (= (:max-width sm-9) "75%"))

      ;; sm-10
      (is (= (:flex-basis sm-10) "83.333333%"))
      (is (= (:flex-grow sm-10) 0))
      (is (= (:max-width sm-10) "83.333333%"))

      ;; sm-11
      (is (= (:flex-basis sm-11) "91.666667%"))
      (is (= (:flex-grow sm-11) 0))
      (is (= (:max-width sm-11) "91.666667%"))

      ;; sm-12
      (is (= (:flex-basis sm-12) "100%"))
      (is (= (:flex-grow sm-12) 0))
      (is (= (:max-width sm-12) "100%"))

      ;; sm-auto
      (is (= (:flex-basis sm-auto) "auto"))
      (is (= (:flex-grow sm-auto) 0))
      (is (= (:max-width sm-auto) "none"))

      ;; sm-true
      (is (= (:flex-basis sm-true) 0))
      (is (= (:flex-grow sm-true) 1))
      (is (= (:max-width sm-true) "100%")))))

(deftest md-grid
  (testing "md sized grid"
    (let [query (-> (#'tincture.grid/generate-grid :md) :value :media-queries)
          grid (-> (#'tincture.grid/generate-grid :md) :value :rules first)
          [_ md-1] (first (filter (fn [[kw _]] (= kw :.grid-md-1)) grid))
          [_ md-2] (first (filter (fn [[kw _]] (= kw :.grid-md-2)) grid))
          [_ md-3] (first (filter (fn [[kw _]] (= kw :.grid-md-3)) grid))
          [_ md-4] (first (filter (fn [[kw _]] (= kw :.grid-md-4)) grid))
          [_ md-5] (first (filter (fn [[kw _]] (= kw :.grid-md-5)) grid))
          [_ md-6] (first (filter (fn [[kw _]] (= kw :.grid-md-6)) grid))
          [_ md-7] (first (filter (fn [[kw _]] (= kw :.grid-md-7)) grid))
          [_ md-8] (first (filter (fn [[kw _]] (= kw :.grid-md-8)) grid))
          [_ md-9] (first (filter (fn [[kw _]] (= kw :.grid-md-9)) grid))
          [_ md-10] (first (filter (fn [[kw _]] (= kw :.grid-md-10)) grid))
          [_ md-11] (first (filter (fn [[kw _]] (= kw :.grid-md-11)) grid))
          [_ md-12] (first (filter (fn [[kw _]] (= kw :.grid-md-12)) grid))
          [_ md-auto] (first (filter (fn [[kw _]] (= kw :.grid-md-auto)) grid))
          [_ md-true] (first (filter (fn [[kw _]] (= kw :.grid-md-true)) grid))]

      ;; media query
      (is (= (-> query :min-width :magnitude) 960))
      (is (= (-> query :min-width :unit) :px))

      ;; md-1
      (is (= (:flex-basis md-1) "8.333333%"))
      (is (= (:flex-grow md-1) 0))
      (is (= (:max-width md-1) "8.333333%"))

      ;; md-2
      (is (= (:flex-basis md-2) "16.666667%"))
      (is (= (:flex-grow md-2) 0))
      (is (= (:max-width md-2) "16.666667%"))

      ;; md-3
      (is (= (:flex-basis md-3) "25%"))
      (is (= (:flex-grow md-3) 0))
      (is (= (:max-width md-3) "25%"))

      ;; md-4
      (is (= (:flex-basis md-4) "33.333333%"))
      (is (= (:flex-grow md-4) 0))
      (is (= (:max-width md-4) "33.333333%"))

      ;; md-5
      (is (= (:flex-basis md-5) "41.666667%"))
      (is (= (:flex-grow md-5) 0))
      (is (= (:max-width md-5) "41.666667%"))

      ;; md-6
      (is (= (:flex-basis md-6) "50%"))
      (is (= (:flex-grow md-6) 0))
      (is (= (:max-width md-6) "50%"))

      ;; md-7
      (is (= (:flex-basis md-7) "58.333333%"))
      (is (= (:flex-grow md-7) 0))
      (is (= (:max-width md-7) "58.333333%"))

      ;; md-8
      (is (= (:flex-basis md-8) "66.666667%"))
      (is (= (:flex-grow md-8) 0))
      (is (= (:max-width md-8) "66.666667%"))

      ;; md-9
      (is (= (:flex-basis md-9) "75%"))
      (is (= (:flex-grow md-9) 0))
      (is (= (:max-width md-9) "75%"))

      ;; md-10
      (is (= (:flex-basis md-10) "83.333333%"))
      (is (= (:flex-grow md-10) 0))
      (is (= (:max-width md-10) "83.333333%"))

      ;; md-11
      (is (= (:flex-basis md-11) "91.666667%"))
      (is (= (:flex-grow md-11) 0))
      (is (= (:max-width md-11) "91.666667%"))

      ;; md-12
      (is (= (:flex-basis md-12) "100%"))
      (is (= (:flex-grow md-12) 0))
      (is (= (:max-width md-12) "100%"))

      ;; md-auto
      (is (= (:flex-basis md-auto) "auto"))
      (is (= (:flex-grow md-auto) 0))
      (is (= (:max-width md-auto) "none"))

      ;; md-true
      (is (= (:flex-basis md-true) 0))
      (is (= (:flex-grow md-true) 1))
      (is (= (:max-width md-true) "100%")))))

(deftest lg-grid
  (testing "lg sized grid"
    (let [query (-> (#'tincture.grid/generate-grid :lg) :value :media-queries)
          grid (-> (#'tincture.grid/generate-grid :lg) :value :rules first)
          [_ lg-1] (first (filter (fn [[kw _]] (= kw :.grid-lg-1)) grid))
          [_ lg-2] (first (filter (fn [[kw _]] (= kw :.grid-lg-2)) grid))
          [_ lg-3] (first (filter (fn [[kw _]] (= kw :.grid-lg-3)) grid))
          [_ lg-4] (first (filter (fn [[kw _]] (= kw :.grid-lg-4)) grid))
          [_ lg-5] (first (filter (fn [[kw _]] (= kw :.grid-lg-5)) grid))
          [_ lg-6] (first (filter (fn [[kw _]] (= kw :.grid-lg-6)) grid))
          [_ lg-7] (first (filter (fn [[kw _]] (= kw :.grid-lg-7)) grid))
          [_ lg-8] (first (filter (fn [[kw _]] (= kw :.grid-lg-8)) grid))
          [_ lg-9] (first (filter (fn [[kw _]] (= kw :.grid-lg-9)) grid))
          [_ lg-10] (first (filter (fn [[kw _]] (= kw :.grid-lg-10)) grid))
          [_ lg-11] (first (filter (fn [[kw _]] (= kw :.grid-lg-11)) grid))
          [_ lg-12] (first (filter (fn [[kw _]] (= kw :.grid-lg-12)) grid))
          [_ lg-auto] (first (filter (fn [[kw _]] (= kw :.grid-lg-auto)) grid))
          [_ lg-true] (first (filter (fn [[kw _]] (= kw :.grid-lg-true)) grid))]

      ;; media query
      (is (= (-> query :min-width :magnitude) 1280))
      (is (= (-> query :min-width :unit) :px))

      ;; lg-1
      (is (= (:flex-basis lg-1) "8.333333%"))
      (is (= (:flex-grow lg-1) 0))
      (is (= (:max-width lg-1) "8.333333%"))

      ;; lg-2
      (is (= (:flex-basis lg-2) "16.666667%"))
      (is (= (:flex-grow lg-2) 0))
      (is (= (:max-width lg-2) "16.666667%"))

      ;; lg-3
      (is (= (:flex-basis lg-3) "25%"))
      (is (= (:flex-grow lg-3) 0))
      (is (= (:max-width lg-3) "25%"))

      ;; lg-4
      (is (= (:flex-basis lg-4) "33.333333%"))
      (is (= (:flex-grow lg-4) 0))
      (is (= (:max-width lg-4) "33.333333%"))

      ;; lg-5
      (is (= (:flex-basis lg-5) "41.666667%"))
      (is (= (:flex-grow lg-5) 0))
      (is (= (:max-width lg-5) "41.666667%"))

      ;; lg-6
      (is (= (:flex-basis lg-6) "50%"))
      (is (= (:flex-grow lg-6) 0))
      (is (= (:max-width lg-6) "50%"))

      ;; lg-7
      (is (= (:flex-basis lg-7) "58.333333%"))
      (is (= (:flex-grow lg-7) 0))
      (is (= (:max-width lg-7) "58.333333%"))

      ;; lg-8
      (is (= (:flex-basis lg-8) "66.666667%"))
      (is (= (:flex-grow lg-8) 0))
      (is (= (:max-width lg-8) "66.666667%"))

      ;; lg-9
      (is (= (:flex-basis lg-9) "75%"))
      (is (= (:flex-grow lg-9) 0))
      (is (= (:max-width lg-9) "75%"))

      ;; lg-10
      (is (= (:flex-basis lg-10) "83.333333%"))
      (is (= (:flex-grow lg-10) 0))
      (is (= (:max-width lg-10) "83.333333%"))

      ;; lg-11
      (is (= (:flex-basis lg-11) "91.666667%"))
      (is (= (:flex-grow lg-11) 0))
      (is (= (:max-width lg-11) "91.666667%"))

      ;; lg-12
      (is (= (:flex-basis lg-12) "100%"))
      (is (= (:flex-grow lg-12) 0))
      (is (= (:max-width lg-12) "100%"))

      ;; lg-auto
      (is (= (:flex-basis lg-auto) "auto"))
      (is (= (:flex-grow lg-auto) 0))
      (is (= (:max-width lg-auto) "none"))

      ;; lg-true
      (is (= (:flex-basis lg-true) 0))
      (is (= (:flex-grow lg-true) 1))
      (is (= (:max-width lg-true) "100%")))))

(deftest xl-grid
  (testing "xl sized grid"
    (let [query (-> (#'tincture.grid/generate-grid :xl) :value :media-queries)
          grid (-> (#'tincture.grid/generate-grid :xl) :value :rules first)
          [_ xl-1] (first (filter (fn [[kw _]] (= kw :.grid-xl-1)) grid))
          [_ xl-2] (first (filter (fn [[kw _]] (= kw :.grid-xl-2)) grid))
          [_ xl-3] (first (filter (fn [[kw _]] (= kw :.grid-xl-3)) grid))
          [_ xl-4] (first (filter (fn [[kw _]] (= kw :.grid-xl-4)) grid))
          [_ xl-5] (first (filter (fn [[kw _]] (= kw :.grid-xl-5)) grid))
          [_ xl-6] (first (filter (fn [[kw _]] (= kw :.grid-xl-6)) grid))
          [_ xl-7] (first (filter (fn [[kw _]] (= kw :.grid-xl-7)) grid))
          [_ xl-8] (first (filter (fn [[kw _]] (= kw :.grid-xl-8)) grid))
          [_ xl-9] (first (filter (fn [[kw _]] (= kw :.grid-xl-9)) grid))
          [_ xl-10] (first (filter (fn [[kw _]] (= kw :.grid-xl-10)) grid))
          [_ xl-11] (first (filter (fn [[kw _]] (= kw :.grid-xl-11)) grid))
          [_ xl-12] (first (filter (fn [[kw _]] (= kw :.grid-xl-12)) grid))
          [_ xl-auto] (first (filter (fn [[kw _]] (= kw :.grid-xl-auto)) grid))
          [_ xl-true] (first (filter (fn [[kw _]] (= kw :.grid-xl-true)) grid))]

      ;; media query
      (is (= (-> query :min-width :magnitude) 1920))
      (is (= (-> query :min-width :unit) :px))

      ;; xl-1
      (is (= (:flex-basis xl-1) "8.333333%"))
      (is (= (:flex-grow xl-1) 0))
      (is (= (:max-width xl-1) "8.333333%"))

      ;; xl-2
      (is (= (:flex-basis xl-2) "16.666667%"))
      (is (= (:flex-grow xl-2) 0))
      (is (= (:max-width xl-2) "16.666667%"))

      ;; xl-3
      (is (= (:flex-basis xl-3) "25%"))
      (is (= (:flex-grow xl-3) 0))
      (is (= (:max-width xl-3) "25%"))

      ;; xl-4
      (is (= (:flex-basis xl-4) "33.333333%"))
      (is (= (:flex-grow xl-4) 0))
      (is (= (:max-width xl-4) "33.333333%"))

      ;; xl-5
      (is (= (:flex-basis xl-5) "41.666667%"))
      (is (= (:flex-grow xl-5) 0))
      (is (= (:max-width xl-5) "41.666667%"))

      ;; xl-6
      (is (= (:flex-basis xl-6) "50%"))
      (is (= (:flex-grow xl-6) 0))
      (is (= (:max-width xl-6) "50%"))

      ;; xl-7
      (is (= (:flex-basis xl-7) "58.333333%"))
      (is (= (:flex-grow xl-7) 0))
      (is (= (:max-width xl-7) "58.333333%"))

      ;; xl-8
      (is (= (:flex-basis xl-8) "66.666667%"))
      (is (= (:flex-grow xl-8) 0))
      (is (= (:max-width xl-8) "66.666667%"))

      ;; xl-9
      (is (= (:flex-basis xl-9) "75%"))
      (is (= (:flex-grow xl-9) 0))
      (is (= (:max-width xl-9) "75%"))

      ;; xl-10
      (is (= (:flex-basis xl-10) "83.333333%"))
      (is (= (:flex-grow xl-10) 0))
      (is (= (:max-width xl-10) "83.333333%"))

      ;; xl-11
      (is (= (:flex-basis xl-11) "91.666667%"))
      (is (= (:flex-grow xl-11) 0))
      (is (= (:max-width xl-11) "91.666667%"))

      ;; xl-12
      (is (= (:flex-basis xl-12) "100%"))
      (is (= (:flex-grow xl-12) 0))
      (is (= (:max-width xl-12) "100%"))

      ;; xl-auto
      (is (= (:flex-basis xl-auto) "auto"))
      (is (= (:flex-grow xl-auto) 0))
      (is (= (:max-width xl-auto) "none"))

      ;; xl-true
      (is (= (:flex-basis xl-true) 0))
      (is (= (:flex-grow xl-true) 1))
      (is (= (:max-width xl-true) "100%")))))

(deftest generate-gutter
  (testing "Generating gutter for grid component"
    (let [gutter (#'tincture.grid/generate-gutter)
          spacing-8 (:spacing-8 gutter)
          spacing-16 (:spacing-16 gutter)
          spacing-24 (:spacing-24 gutter)
          spacing-32 (:spacing-32 gutter)
          spacing-40 (:spacing-40 gutter)]

      ;; Spacing 8
      (is (= (-> spacing-8 :margin :unit) :px))
      (is (= (-> spacing-8 :margin :magnitude) -4))
      (is (= (-> spacing-8 :width :f) "calc"))
      (is (= (-> spacing-8 :width :args first first :unit) :%))
      (is (= (-> spacing-8 :width :args first first :magnitude) 100))
      (is (= (-> spacing-8 :width :args first second) '+))
      (is (= (-> spacing-8 :width :args first last :unit) :px))
      (is (= (-> spacing-8 :width :args first last :magnitude) 8))
      
      ;; Spacing 16
      (is (= (-> spacing-16 :margin :unit) :px))
      (is (= (-> spacing-16 :margin :magnitude) -8))
      (is (= (-> spacing-16 :width :f) "calc"))
      (is (= (-> spacing-16 :width :args first first :unit) :%))
      (is (= (-> spacing-16 :width :args first first :magnitude) 100))
      (is (= (-> spacing-16 :width :args first second) '+))
      (is (= (-> spacing-16 :width :args first last :unit) :px))
      (is (= (-> spacing-16 :width :args first last :magnitude) 16))

      ;; Spacing-24
      (is (= (-> spacing-24 :margin :unit) :px))
      (is (= (-> spacing-24 :margin :magnitude) -12))
      (is (= (-> spacing-24 :width :f) "calc"))
      (is (= (-> spacing-24 :width :args first first :unit) :%))
      (is (= (-> spacing-24 :width :args first first :magnitude) 100))
      (is (= (-> spacing-24 :width :args first second) '+))
      (is (= (-> spacing-24 :width :args first last :unit) :px))
      (is (= (-> spacing-24 :width :args first last :magnitude) 24))

      ;; Spacing-32
      (is (= (-> spacing-32 :margin :unit) :px))
      (is (= (-> spacing-32 :margin :magnitude) -16))
      (is (= (-> spacing-32 :width :f) "calc"))
      (is (= (-> spacing-32 :width :args first first :unit) :%))
      (is (= (-> spacing-32 :width :args first first :magnitude) 100))
      (is (= (-> spacing-32 :width :args first second) '+))
      (is (= (-> spacing-32 :width :args first last :unit) :px))
      (is (= (-> spacing-32 :width :args first last :magnitude) 32))

      ;; Spacing-40
      (is (= (-> spacing-40 :margin :unit) :px))
      (is (= (-> spacing-40 :margin :magnitude) -20))
      (is (= (-> spacing-40 :width :f) "calc"))
      (is (= (-> spacing-40 :width :args first first :unit) :%))
      (is (= (-> spacing-40 :width :args first first :magnitude) 100))
      (is (= (-> spacing-40 :width :args first second) '+))
      (is (= (-> spacing-40 :width :args first last :unit) :px))
      (is (= (-> spacing-40 :width :args first last :magnitude) 40)))))

(deftest grid-style
  (testing "Sample grid styles"
    (let [container-default (#'tincture.grid/grid-style :stretch :stretch true :row 0 false :flex-start :wrap false)
          item-default (#'tincture.grid/grid-style :stretch :stretch false :row 0 true :flex-start :wrap false)
          center (#'tincture.grid/grid-style :stretch :center true :row 0 false :center :wrap false)
          column-spacing (#'tincture.grid/grid-style :stretch :stretch true :row 16 false :flex-start :wrap false)
          nowrap-zero-min-width (#'tincture.grid/grid-style :stretch :stretch true :row 0 false :flex-start :nowrap true)]
      (is (= container-default {:box-sizing :border-box, :display :flex, :flex-wrap :wrap, :width (percent 100)}))
      (is (= item-default {:box-sizing :border-box, :margin 0}))
      (is (= center {:box-sizing :border-box, :display :flex, :flex-wrap :wrap, :width (percent 100), :align-items :center, :justify-content :center}))
      (is (= column-spacing {:box-sizing :border-box,
                             :display :flex,
                             :flex-wrap :wrap,
                             :width #garden.types.CSSFunction{:f "calc",
                                                              :args [(#garden.types.CSSUnit{:unit :%,:magnitude 100}
                                                                      +
                                                                      #garden.types.CSSUnit{:unit :px,:magnitude 16})]},
                             :margin #garden.types.CSSUnit{:unit :px,:magnitude -8}}))
      (is (= nowrap-zero-min-width {:box-sizing :border-box,
                                    :display :flex,
                                    :flex-wrap :nowrap,
                                    :width #garden.types.CSSUnit{:unit :%, :magnitude 100}, :min-width 0})))))
