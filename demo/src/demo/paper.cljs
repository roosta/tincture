(ns demo.paper
  (:require-macros [flora-ui.macro :refer [defui]])
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [reagent.debug :as d]
            [herb.macro :refer-macros [with-style]]
            [flora-ui.paper :refer [paper]]))

(defn paper-style
  []
  {:width (px 300)
   :height (px 200)})

(defui test-comp
  (fn [{:keys [theme prop]}]
    [:div (str "hello " prop)]))

(defn main
  []
  [:div [:h2 "Paper demo"]
   [:div [:a {:href "/"} "go to the home page"]]
   [paper {:class (with-style paper-style)
           :elevation 1}]
   [test-comp {:prop "world"}]])
