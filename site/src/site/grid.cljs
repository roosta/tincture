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
          :background "#eee"
          :padding (px 16)}
   :grid-container {}
   :heading {:color (rgb 0 0 0 0.54)}})

(defgroup radio-group-styles
  {:label {:margin-right (px 8)}})

(defn radio-group [state coll]
  [:form
   [:div
    (doall
     (for [g (sort coll)]
       ^{:key g}
       [:label {:class (<class radio-group-styles :label)}
        [:input {:type "radio"
                 :value g
                 :on-change (fn [e]
                              (let [v (js/parseInt (.. e -target -value))]
                                (reset! state v)))
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
          [radio-group state gutters]]]
        ])))

(defgroup grid-style
  {:root {:flex-grow 1
          :background "#eee"
          :padding (px 16)}
   :paper {:padding (px 16)
           :background "white"
           :color (rgb 0 0 0 0.54)
           :text-align :center}})

(defn basic-grid []
   [grid {:container? true
          :class (<class grid-style :root)
          :spacing 24}
    [grid {:item? true
           :xs 12}
     [paper {:class (<class grid-style :paper)}
      "xs=12"]]
    [grid {:item? true
           :xs 6}
     [paper {:class (<class grid-style :paper)}
      "xs=6"]]
    [grid {:item? true
           :xs 6}
     [paper {:class (<class grid-style :paper)}
      "xs=6"]]
    [grid {:item? true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [grid {:item? true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [grid {:item? true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [grid {:item? true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]])


(defn grid-with-breakpoints []
  [grid {:container? true
         :class (<class grid-style :root)
         :spacing 24}
   [grid {:item? true
          :xs 12}
    [paper {:class (<class grid-style :paper)}
     "xs=12"]]
   [grid {:item? true
          :xs 12
          :sm 6}
    [paper {:class (<class grid-style :paper)}
     "xs=12 sm=6"]]
   [grid {:item? true
          :xs 12
          :sm 6}
    [paper {:class (<class grid-style :paper)}
     "xs=12 sm=6"]]
   [grid {:item? true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [grid {:item? true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [grid {:item? true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [grid {:item? true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]])

(defn main []
  [container
   [grid {:container? true
          :spacing 32}
    [grid {:item? true
           :xs 12}
     [typography {:variant :headline
                  :align :center}
      "Spacing"]
     [spacing]]
    [grid {:item? true
           :xs 12}
     [typography {:variant :headline
                  :align :center}
      "Basic grid"]
     [basic-grid]]
    [grid {:item? true
           :xs 12}
     [typography {:variant :headline
                  :align :center}
      "Grid with breakpoints"]
     [grid-with-breakpoints]]]
   ])
