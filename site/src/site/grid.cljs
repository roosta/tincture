(ns site.grid
  (:require
   [tincture.container :refer [container]]
   [tincture.typography :refer [typography]]
   [debux.cs.core :refer [clog]]
   [tincture.grid :refer [grid gutters]]
   [tincture.paper :refer [paper]]
   [tincture.cssfns :refer [rgb]]
   [herb.core :refer [<class defgroup]]
   [reagent.debug :refer [log]]
   [garden.units :refer [px]]
   [reagent.core :as r]))

(defn paper-style []
  {:background "white"
   :height "140px"
   :width "100px"})

(defn control-paper []
  ^{:extend paper-style}
  {:margin-top (px 16)
   :padding (px 16)
   :height "auto"
   :width "auto"})

(defgroup spacing-styles
  {:root {:flex-grow 1
          :padding (px 16)}
   :grid-container {}
   :heading {:color (rgb 0 0 0 0.54)}
   :label {:margin-left (px 8)}})

(defn radio-group [state]
  [:form {:on-change (fn [e]
                       (let [v (js/parseInt (.. e -target -value))]
                         (reset! state v)))}
   [:div
    (doall
     (for [g (sort gutters)]
       ^{:key g}
       [:label {:class (<class spacing-styles :label)}
        [:input {:type "radio"
                 :value g
                 :name "spacing"
                 :checked (= @state g)
                 }]
       g]))]])

(defn spacing []
  (let [state (r/atom 8)]
    (fn []
      [grid {:container? true
             :spacing 16
             :class (<class spacing-styles :root)}
       [grid {:item? true
              :xs 12}
        [grid {:container? true
               :class (<class spacing-styles :grid-container)
               :spacing @state
               :justify :center}
         [grid {:item? true}
          [paper {:class (<class paper-style)}]]
         [grid {:item? true}
          [paper {:class (<class paper-style)}]]
         [grid {:item? true}
          [paper {:class (<class paper-style)}]]]]
       [grid {:item? true
              :xs 12}
        [paper {:class (<class control-paper)}
         [typography {:class (<class spacing-styles :heading)
                      :variant :subheading}
          "Spacing"]
         [radio-group state]]]
       ])))

(defn container-style
  []
  {:background "#eee"})

(defn main []
  [container {:class (<class container-style)}
   [spacing]
   ])
