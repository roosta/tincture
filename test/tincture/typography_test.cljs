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
    (let [f #'tincture.typography/variants]
      (rf-test/run-test-sync
       (core/init!)
       (is (= (f :h2 :light)
              {:color #garden.types.CSSFunction{:f "rgb", :args [0 0 0 0.87]},
               :font-family ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"],
               :font-size #garden.types.CSSUnit{:unit :rem, :magnitude 3.75}, :font-weight 300, :line-height 1}))
       (is (= (f :subtitle1 :dark)
              {:color "#fff",
               :font-family ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"],
               :font-size #garden.types.CSSUnit{:unit :rem, :magnitude 1},
               :font-weight 400,
               :line-height 1.75}))
       (is (= (f :body1 :secondary-light)
              {:color #garden.types.CSSFunction{:f "rgb", :args [0 0 0 0.54]},
               :font-family ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"],
               :font-size #garden.types.CSSUnit{:unit :rem, :magnitude 1},
               :font-weight 400,
               :line-height 1.5}))
       (is (= (f :button :light)
              {:color #garden.types.CSSFunction{:f "rgb", :args [0 0 0 0.87]},
               :font-family ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"],
               :font-size #garden.types.CSSUnit{:unit :rem, :magnitude 0.875},
               :font-weight 500,
               :text-transform :uppercase,
               :line-height 1.75}))
       (is (= (f :sr-only :light)
              {:position "absolute",
               :height #garden.types.CSSUnit{:unit :px, :magnitude 1},
               :width #garden.types.CSSUnit{:unit :px, :magnitude 1},
               :overflow "hidden"}))))))

(deftest typography-style
  (testing "typography style function"
    (let [f #'tincture.typography/typography-style]
      ))
  )
