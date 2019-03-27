(ns site.grid
  (:require
   [tincture.container :refer [container]]
   [tincture.typography :refer [typography]]
   [debux.cs.core :refer [clog]]
   [tincture.grid :refer [grid gutters justify direction align-items]]
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

(defn paper-interactive-style [number]
  ^{:key number}
  {:background "white"
   :height "100%"
   :padding (px 16)
   :padding-top (px (* (inc number) 10))
   :padding-bottom (px (* (inc number) 10))
   :color (rgb 0 0 0 0.54)})

(defn control-paper []
  ^{:extend paper-style}
  {:margin-top (px 16)
   :padding (px 16)
   :height "auto"
   :width "auto"})

(defgroup spacing-styles
  {:grid-container {}
   :heading {:color (rgb 0 0 0 0.54)}})

(defn root-style []
  {:flex-grow 1
   :background "#eee"
   :padding (px 16)}
  )

(defgroup radio-group-styles
  {:label {:margin-right (px 8)}})

(defn radio-group [checked? coll on-change]
  [:form
   [:div
    (doall
     (for [g (sort coll)]
       ^{:key g}
       [:label {:class (<class radio-group-styles :label)}
        [:input {:type "radio"
                 :value g
                 :on-change (fn [e]
                              (let [v (.. e -target -value)]
                                (on-change v)))
                 :name "spacing"
                 :checked (checked? g)}]
       g]))]])

(defn spacing []
  (let [state (r/atom 8)]
    (fn []
       [grid {:container true
              :spacing 16
              :class (<class root-style)}
        [grid {:item true
               :xs 12}
         [grid {:container true
                :class (<class spacing-styles :grid-container)
                :spacing @state
                :justify :center}
          [grid {:item true}
           [paper {:class (<class paper-style)}]]
          [grid {:item true}
           [paper {:class (<class paper-style)}]]
          [grid {:item true}
           [paper {:class (<class paper-style)}]]]]
        [grid {:item true
               :xs 12}
         [paper {:class (<class control-paper)}
          [typography {:class (<class spacing-styles :heading)
                       :variant :subtitle1}
           "Spacing"]
          [radio-group #(= @state %) (deref #'tincture.grid/gutters) #(reset! state (js/parseInt %))]]]
        ])))

(defgroup grid-style
  {:root {:flex-grow 1
          :background "#eee"
          :padding (px 16)}
   :paper {:padding (px 16)
           :background "white"
           :color (rgb 0 0 0 0.54)
           :text-align :center}})

(defgroup interactive-styles
  {:demo {:height (px 240)}})

(defn interactive []
  (let [state (r/atom {:direction :row
                       :justify :center
                       :align-items :center})]
    (fn []
      [grid {:container true
             :spacing 16
             :class (<class root-style)}
       [grid {:item true
              :xs 12}
        [grid {:container true
               :class (<class interactive-styles :demo)
               :direction (:direction @state)
               :justify (:justify @state)
               :align-items (:align-items @state)
               :spacing 16}
         (for [n (range 3)]
           ^{:key n}
           [grid {:item true}
            [paper {:class (<class paper-interactive-style n)}
             (str "Cell " (inc n))
             ]])]]
       [grid {:item true
              :xs 12}
        [paper {:class (<class control-paper)}
         [typography {:class (<class spacing-styles :heading)
                      :variant :subtitle1}
          "direction"]
         [radio-group
          #(= (:direction @state) %)
          (deref #'tincture.grid/direction)
          #(swap! state assoc :direction (keyword %))]
         [typography {:class (<class spacing-styles :heading)
                      :variant :subtitle1}
          "justify"]
         [radio-group
          #(= (:justify @state) %)
          (deref #'tincture.grid/justify)
          #(swap! state assoc :justify (keyword %))]
         [typography {:class (<class spacing-styles :heading)
                      :variant :subtitle1}
          "align-items"]
         [radio-group #(= (:align-items @state) %)
          (deref #'tincture.grid/align-items)
          #(swap! state assoc :align-items (keyword %))]]]])))

(defn basic-grid []
   [grid {:container true
          :class (<class grid-style :root)
          :spacing 24}
    [grid {:item true
           :xs 12}
     [paper {:class (<class grid-style :paper)}
      "xs=12"]]
    [grid {:item true
           :xs 6}
     [paper {:class (<class grid-style :paper)}
      "xs=6"]]
    [grid {:item true
           :xs 6}
     [paper {:class (<class grid-style :paper)}
      "xs=6"]]
    [grid {:item true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [grid {:item true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [grid {:item true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [grid {:item true
           :xs 3}
     [paper {:class (<class grid-style :paper)}
      "xs=3"]]])


(defn grid-with-breakpoints []
  [grid {:container true
         :class (<class grid-style :root)
         :spacing 24}
   [grid {:item true
          :xs 12}
    [paper {:class (<class grid-style :paper)}
     "xs=12"]]
   [grid {:item true
          :xs 12
          :sm 6}
    [paper {:class (<class grid-style :paper)}
     "xs=12 sm=6"]]
   [grid {:item true
          :xs 12
          :sm 6}
    [paper {:class (<class grid-style :paper)}
     "xs=12 sm=6"]]
   [grid {:item true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [grid {:item true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [grid {:item true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [grid {:item true
          :xs 6
          :sm 3}
    [paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]])

(defn main []
  [container
   [grid {:container true
          :spacing 32}
    [grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :align :center}
      "Spacing"]
     [spacing]]
    [grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :align :center}
      "Basic grid"]
     [basic-grid]]
    [grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :align :center}
      "Grid with breakpoints"]
     [grid-with-breakpoints]]
    [grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :align :center}
      "Interactive"]
     [interactive]]]])
