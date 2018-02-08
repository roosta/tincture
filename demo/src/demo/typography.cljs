(ns demo.typography
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [tonic.typography :refer [typography]]
            [reagent.debug :as d]
            [herb.macro :refer-macros [with-style]]))

(defn main
  []
  [:div [typography {:kind :headline}
         "Typography demo"]
   [typography {:kind :body1}
    "Hello demo"]

   [:div [:a {:href "/"} "go to the home page"]]
   ])
