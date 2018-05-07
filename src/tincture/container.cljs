(ns tincture.container
  (:require [herb.core
             :refer-macros [<class]
             :refer [join-classes]]
            [garden.units :refer [em rem]]
            [reagent.core :as r]))

(defn container-style
  []
  ^{:media {{:screen :only :min-width (em 48)} {:width (rem 49)}
            {:screen :only :min-width (em 64)} {:width (rem 65)}
            {:screen :only :min-width (em 75)} {:width (rem 76)}}}
  {:margin-right "auto"
   :margin-left "auto"})

(defn container-fluid-style
  []
  {:padding-right (rem 2)
   :padding-left (rem 2)
   :margin-right "auto"
   :margin-left "auto"})

(defn container
  [{:keys [class]}]
  (into [:div {:class (join-classes class (<class container-style))}]
        (r/children (r/current-component))))

(defn container-fluid
  []
  (into [:div {:class (<class container-fluid-style)}]
        (r/children (r/current-component))))
