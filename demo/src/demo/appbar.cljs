(ns demo.appbar
  (:require-macros [flora-ui.macro :refer [defui]])
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [herb.macro :refer-macros [with-style]]
            [flora-ui.appbar :refer [appbar]]
            [flora-ui.icons :as icons]
            [flora-ui.typography :refer [typography]]
            [reagent.debug :as d]
            [herb.macro :refer-macros [with-style]]))

(defn title-style
  []
  {:color "white"
   :flex 1})

(defn button-style
  []
  {:color "white"
   :margin-right (px 20)})

(defn main
  []
  [:div
   [appbar
    [icons/menu {:class (with-style button-style)}]
    [typography {:class (with-style title-style)
                 :kind :title}
     "Title"]]
   [:div [:a {:href "/"} "go to the home page"]]])
