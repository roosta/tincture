(ns flora-ui.card
  #_(:require
   [cljs-css-modules.macro :refer-macros [defstyle]]
   [re-frame.core :as rf]
   [audioskop.components.typography :refer [typography]]
   [audioskop.utils :as utils]
   [audioskop.copy :as copy]
   [clojure.string :as s]
   [garden.units :refer [px percent]]
   [reagent.core :as r]
   [audioskop.utils :as utils]))

#_(defstyle card-style
  [:.root {:width (px 350)
           ;; :margin-bottom (px 70)
           :margin (px 30)
           :background-size "cover"
           :position "relative"
           :box-shadow (utils/box-shadow 1)
           :color (:white copy/colors)
           :transition (utils/transition {:prop "all"
                                          :duration "300ms"})
           :height (px 350)}
   [:&:hover {:box-shadow (utils/box-shadow 10)
              :cursor "pointer"
              :transform "scale(1.05)"}]]
  [:.container {:top "30%"
                :overflow "auto"
                :position "absolute"
                :background (:white copy/colors)
                :color (:black copy/colors)
                :width "100%"}]
  (at-media {:max-width "320px"}
            [:.root {:width (px 300)
                     :height (px 300)}]))

#_(defn card
  [{:keys [text img href class title-el]}]
  [:div {:class (str (:root card-style) " " class)
         :style {:background-image (str "url(" img ")")}}
   (if title-el
     title-el
     [:div {:class (:container card-style)}
      [typography {:kind :headline
                   :align :center}
       text]])])
