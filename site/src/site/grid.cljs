(ns site.grid
  (:require
   [tincture.container :refer [container]]
   [tincture.typography :refer [typography]]
   [tincture.grid :refer [grid]]
   [herb.core :refer [<class defgroup]]
   [reagent.debug :refer [log]]
   [garden.units :refer [px]]))


(defn main []
  [container
   [grid {:container? true
          :align-content :center
          :spacing 8
          :justify :center}
    [grid {:item? true
           :xs 12}
     "hello world"]]])
