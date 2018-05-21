(ns demo.gradient
  (:require
   [tincture.container :refer [container]]
   [tincture.gradient :refer [gradient]]))


(defn gradient-demo
  []
  [container
   [:div "Hello gradient"]])
