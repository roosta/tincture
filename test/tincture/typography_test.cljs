(ns tincture.typography-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tincture.test-utils :as utils]
            [dommy.core :as dommy :refer-macros [sel1 sel]]
            [reagent.core :as r]
            [day8.re-frame.test :as rf-test]
            [tincture.core :as core]
            [tincture.events]
            [tincture.typography]))

(deftest variants
  (testing "Sample some variants and ensure output"
    (rf-test/run-test-sync
     (core/init!)
     (let [variant (#'tincture.typography/variants :h2 :light)]
       (.log js/console variant)
       (is (= (-> variant :color :f) "rgb"))
       (is (= (-> variant :color :args) [0 0 0 0.87]))
       (is (= (-> variant :font-family) ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"]))
       (is (= (-> variant :font-size :unit) :rem))
       (is (= (-> variant :font-size :magnitude) 3.75))
       (is (= (-> variant :font-weight) 300))
       (is (= (-> variant :line-height) 1)))

     (let [variant (#'tincture.typography/variants :subtitle1 :dark)]
       (is (= (-> variant :color) "#fff"))
       (is (= (-> variant :font-family) ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"]))
       (is (= (-> variant :font-size :unit) :rem))
       (is (= (-> variant :font-size :magnitude) 1))
       (is (= (-> variant :font-weight) 400))
       (is (= (-> variant :line-height) 1.75)))

     (let [variant (#'tincture.typography/variants :body1 :secondary-light)]
       (is (= (-> variant :color :f) "rgb"))
       (is (= (-> variant :color :args) [0 0 0 0.54]))
       (is (= (-> variant :font-family) ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"]))
       (is (= (-> variant :font-size :unit) :rem))
       (is (= (-> variant :font-size :magnitude) 1))
       (is (= (-> variant :font-weight) 400))
       (is (= (-> variant :line-height) 1.5)))

     (let [variant (#'tincture.typography/variants :button :light)]
       (is (= (-> variant :color :f) "rgb"))
       (is (= (-> variant :color :args) [0 0 0 0.87]))
       (is (= (-> variant :font-family) ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"]))
       (is (= (-> variant :font-size :unit) :rem))
       (is (= (-> variant :font-size :magnitude) 0.875))
       (is (= (-> variant :font-weight) 500))
       (is (= (-> variant :line-height) 1.75)))

     (let [variant (#'tincture.typography/variants :sr-only :light)]
       (is (= (-> variant :position) "absolute"))
       (is (= (-> variant :height :unit) :px))
       (is (= (-> variant :height :magnitude) 1))
       (is (= (-> variant :width :unit) :px))
       (is (= (-> variant :width :magnitude) 1))
       (is (= (-> variant :overflow) "hidden"))))))

(deftest typography-style
  (testing "typography style function"
    (rf-test/run-test-sync
     (core/init!)
       (let [style (#'tincture.typography/typography-style
                    :body2 :left :normal :ltr 0 false false :light false)]
         (is (= (:line-height style) 1.5))
         (is (= (-> style :color :f) "rgb"))
         (is (= (-> style :color :args) [0 0 0 0.87]))
         (is (= (:text-align style) "left"))
         (is (= (-> style :font-size :unit) :rem))
         (is (= (-> style :font-size :magnitude) 0.875))
         (is (= (:text-shadow style) "none"))
         (is (= (:direction style) "ltr"))
         (is (= (:font-family style) ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"]))
         (is (= (-> style meta :key) "body2-left-normal-ltr-0-false-false-light-false")))

       (let [style (#'tincture.typography/typography-style
                    :h3 :right :italic :rtl 3 true true :dark true)]
         (is (= (:line-height style) 1.04))
         (is (= (:text-overflow style) "ellipsis"))
         (is (= (:color style) "#fff"))
         (is (= (:text-align style) "right"))
         (is (= (:white-space style) "nowrap"))
         (is (= (:overflow style) :hidden))
         (is (= (:text-shadow style) "4px 4px 8px rgba(0, 0, 0, 0.3)"))
         (is (= (-> style :margin-bottom :unit) :em))
         (is (= (-> style :margin-bottom :magnitude) 0.35))
         (is (= (:direction style) "rtl"))
         (is (= (:margin style) 0))
         (is (= (-> style meta :key) "h3-right-italic-rtl-3-true-true-dark-true"))))))
