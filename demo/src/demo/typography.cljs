(ns demo.typography
  (:require-macros [flora-ui.macro :refer [defui]])
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [flora-ui.typography :refer [typography]]
            [reagent.debug :as d]
            [herb.macro :refer-macros [with-style]]))

(defn main
  []
  [:div [typography {:kind :headline}
         "Typography demo"]
   [typography {:kind :body1}
    "Hello demo"]])
