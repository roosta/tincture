(ns site.typography
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [tincture.typography :refer [typography]]
            [tincture.container :refer [container]]
            [clojure.string :as str]
            [reagent.debug :refer [log]]
            [herb.core :refer-macros [<class]]))

(defn main
  []
  [container
   (for [variant
         [:display4 :display3 :display2 :display1 :headline :title :subheading
         :button :body1 :body2]]
     ^{:key variant}
     [typography {:variant variant}
      variant])])
