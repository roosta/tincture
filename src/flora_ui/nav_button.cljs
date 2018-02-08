(ns flora-ui.nav-button
  (:require-macros [flora-ui.macro :refer [defui]])
  (:require
   [re-frame.core :as rf]
   [flora-ui.utils :as utils]
   [herb.macro :refer-macros [with-style]]
   [flora-ui.typography :refer [typography]]
   [flora-ui.paper :refer [paper]]
   [flora-ui.icons :as icons]
   [garden.units :refer [px percent]]
   [reagent.core :as r]))

(defn root-style
  []
  {:position "relative"
   :cursor "pointer"})

(defn text-color
  [color]
  {:color color})

(defn typo-style
  [color]
  ^{:extend [text-color color]}
  {:margin 0})

(defn underline-style
  []
  {:position "absolute"
   :left 0
   :right 0
   :width 0
   :bottom "-5px"
   :opacity 0
   :margin "0 auto"
   :transition "width 400ms ease, opacity 400ms ease"
   :height (px 1)
   :background-color "white"})

(defui nav-button
  (let [hover? (r/atom false)]
    (fn [{:keys [theme class color]}]
      [:a {:class (if class
                    (str class " " (with-style root-style))
                    (with-style root-style))
           :on-mouse-enter #(reset! hover? true)
           :on-mouse-leave #(reset! hover? false)}
       (into [typography {:class (with-style typo-style color)
                          :kind :subheading}]
             (r/children (r/current-component)))])))
