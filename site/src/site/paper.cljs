(ns site.paper
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [tincture.typography :refer [Typography]]
            [tincture.paper :refer [Paper]]
            [herb.core :refer-macros [<class]]
            [tincture.container :refer [Container]]
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
  [Container {:class (<class container-style)}
   [Paper {:class (<class paper-style)}
    [Typography {:variant :h5}
     "This is a paper component"]]
   ])
