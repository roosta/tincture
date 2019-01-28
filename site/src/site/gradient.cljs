(ns site.gradient
  (:require
   [tincture.container :refer [container]]
   [tincture.typography :refer [typography]]
   [herb.core :refer [<class defgroup]]
   [reagent.debug :refer [log]]
   [garden.units :refer [px]]
   [tincture.gradient :as gradient]))

(defn gradient-box
  [gradient kw]
  ^{:key kw}
  {:display "flex"
   :align-items "center"
   :justify-content "center"
   :background gradient
   :height (px 200)})

(defgroup styles
  {:grid {:display "grid"
          :row-gap "10px"
          :column-gap "10px"
          :grid-template-columns "repeat(4, 1fr)"}
   :headline {:margin 0
              :margin-top (px 10)}
   :palette-name {}
   :text {:margin-bottom (px 20)}})

(defn gradient-demo
  []
  [container
   [typography {:class (<class styles :headline)
                :variant :display3}
    "CSS Gradients"]
   [typography {:class (<class styles :text)}
    "Tincture provides a gradient tool, it uses gradients defined at " [:a {:href "uigradients.com"} "uigradients.com"]]
   [:div {:class (<class styles :grid)}
    (for [g gradient/collection]
      (let [kw (key g)]
        ^{:key kw}
        [:div {:class (<class gradient-box (gradient/css (key g)) kw)}
         [typography {:class (<class styles :palette-name)
                      :variant :subheading}
          (name kw)]
         ]))]])
