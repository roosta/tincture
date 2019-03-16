(ns tincture.paper
  (:require [herb.core :refer-macros [<class]]
            [garden.units :refer [rem em px]]
            [tincture.core :as core]
            [reagent.core :as r]))

(defn box-shadow [elevation]
  {:box-shadow (core/box-shadow elevation)})

(defn paper-style [elevation square]
  ^{:extend [box-shadow elevation]
    :key (str elevation "-" square)
    :group true}
  {:box-sizing "border-box"
   :border-radius (if square 4 0)})

(defn paper
  [{:keys [class id elevation square component]
    :or {square false
         component :div
         elevation 2}}]
  (into [component {:id id
                   :class [(<class paper-style elevation square) class]}]
        (r/children (r/current-component))))
