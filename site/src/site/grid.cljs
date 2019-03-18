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
  {:width "100%"
   :background "white"
   :height "140px"})

(defn control-paper []
  ^{:extend paper-style}
  {:margin-top (px 16)
   :padding (px 16)
   :height "auto"})

(defgroup styles
  {:container {}
   :grid-container {:height "400px"
                    :background "#eee"}
   :heading {:color (rgb 0 0 0 0.54)}
   :label {:margin-left (px 8)}
   :paper {:width "100%"
           :background "white"
           :height "140px"}})

(defn radio-group [state]
  [:form {:on-change (fn [e]
                       (let [v (js/parseInt (.. e -target -value))]
                         (reset! state v)))}
   [:div
    (doall
     (for [g (sort gutters)]
       ^{:key g}
       [:label {:class (<class styles :label)}
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
             :class (<class styles :grid-container)
             :align-content :center
             :spacing @state
             :justify :center}
       [grid {:item? true
              :xs 2}
        [paper {:class (<class paper-style)}]]
       [grid {:item? true
              :xs 2}
        [paper {:class (<class paper-style)}]]
       [grid {:item? true
              :xs 2}
        [paper {:class (<class paper-style)}]]
       [grid {:item? true
              :xs 10}
        [paper {:class (<class control-paper)}
         [typography {:class (<class styles :heading)
                      :variant :subheading}
          "Spacing"]
         [radio-group state]]]])))

(defn main []
  [container {:class (<class styles :container)}
   [spacing]
   ])
