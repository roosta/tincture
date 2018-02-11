(ns tonic.container
  (:require [herb.core :refer-macros [with-style]]
            [reagent.core :as r]))

(defn container-style
  []
  {:width "76rem"
   :margin-right "auto"
   :margin-left "auto"})

(defn container
  []
  (into [:div {:class (with-style container-style)}]
        (r/children (r/current-component))))
