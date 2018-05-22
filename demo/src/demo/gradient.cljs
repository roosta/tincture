(ns demo.gradient
  (:require
   [tincture.container :refer [container]]
   [reagent.debug :refer [log]]
   [tincture.gradient :refer [gradient]]))


(defn gradient-demo
  []
  (log (gradient :vice-city :left))
  [container
   [:div "Hello gradient"]])
