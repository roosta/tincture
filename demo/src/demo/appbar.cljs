(ns demo.appbar
  (:require-macros [flora-ui.macro :refer [defui]])
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [flora-ui.appbar :refer [appbar]]
            [flora-ui.typography :refer [typography]]
            [reagent.debug :as d]
            [herb.macro :refer-macros [with-style]]))

(defn main
  []
  [:div
   [appbar
    [typography {:kind :title}
     "Hello"]]
   [:div [:a {:href "/"} "go to the home page"]]])
