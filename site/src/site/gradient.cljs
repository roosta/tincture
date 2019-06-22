(ns site.gradient
  (:require
   [tincture.container :refer [container]]
   [tincture.typography :refer [typography]]
   [tincture.cssfns :refer [rgb]]
   [herb.core :refer [<class defgroup]]
   [tincture.grid :refer [Grid]]
   [reagent.debug :refer [log]]
   [garden.units :refer [px]]
   [tincture.gradient :as gradient]))

(defn gradient-box
  [gradient kw]
  ^{:key kw}
  {:background gradient
   :height (px 200)})

(defgroup styles
  {:headline {}
   :container {:padding (px 16)}
   :palette-name {:background (rgb 0 0 0 0.53)
                  :width "100%"}
   :text {:margin-bottom (px 20)}})

(defn gradient-demo
  []
  [container {:class (<class styles :container)}
   [typography {:class (<class styles :headline)
                :gutter-bottom true
                :align :center
                :variant :h4}
    "CSS Gradients"]
   [typography {:gutter-bottom true
                :align :center
                :class (<class styles :text)}
    "Tincture provides a gradient tool, it uses gradients defined at " [:a {:href "https://uigradients.com"} "uigradients.com"]]
   [Grid {:container true
          :spacing 16}
    (for [g (deref #'tincture.gradient/collection)]
      (let [kw (key g)]
        ^{:key kw}
        [Grid {:item true
               :xs 12
               :sm 6
               :md 3}
         [Grid {:container true
                :align-items :center
                :class (<class gradient-box (gradient/css (key g)) kw)}
           [typography {:class (<class styles :palette-name)
                        :align :center
                        :color :dark
                        :variant :subtitle1}
            (name kw)]]]))]])
