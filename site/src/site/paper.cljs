(ns site.paper
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [tincture.typography :refer [typography]]
            [tincture.paper :refer [paper]]
            [herb.core :refer-macros [<class]]
            [tincture.container :refer [container]]
            [clojure.string :as str]
            [reagent.debug :refer [log]]
            [herb.core :refer-macros [<class]]))

(defn paper-style []
  {:padding "16px"})

(defn container-style []
  {:display :flex
   :justify-content :center
   :align-items :center
   :height "100vh"})

(defn main []
  [container {:class (<class container-style)}
   [paper {:class (<class paper-style)}
    [typography {:variant :h5}
     "This is a paper component"]]
   ])
