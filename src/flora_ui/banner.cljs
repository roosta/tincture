(ns flora-ui.banner
  #_(:require
   [cljs-css-modules.macro :refer-macros [defstyle]]
   [re-frame.core :as rf]
   [audioskop.components.typography :refer [typography]]
   [audioskop.copy :as copy]
   [clojure.string :as s]
   [garden.units :refer [px percent]]
   [reagent.core :as r]
   [audioskop.utils :as utils]))

#_(defstyle banner-style
  [:.root {:display "flex"
           :justify-content "center"
           :align-items "center"
           :width "100%"
           :background-size "cover"}]
  [:.title {:margin-bottom (px 40)
            :margin-top 0}]
  [:.subtitle {:max-width (percent 60)
               :margin "0 auto"}])

#_(defn banner
  [{:keys [img title subtitle title-kind subtitle-kind
           class color background-position title-elevation]
    :or {title ""
         title-kind :display3
         title-elevation 3
         background-position "0% 0%"
         subtitle-kind :subheading
         color "white"}}]
  ;; {:pre [(assert img "Please provide an image source for banner")]}
  [:section {:class (str (:root banner-style) " " class)
             :style {:background-image (str "url(" img ")")
                     :background-position background-position
                     :color color}}

   (when title
     [:div
      [typography {:kind title-kind
                   :align :center
                   :elevation title-elevation
                   :class (:title banner-style)}
       title]
      (when subtitle
        [typography {:kind subtitle-kind
                     :align :center
                     :elevation title-elevation
                     :class (:subtitle banner-style)}
         subtitle])])])
