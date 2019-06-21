(ns site.grid
  (:require
   [tincture.container :refer [container]]
   [tincture.typography :refer [typography]]
   [debux.cs.core :refer [clog]]
   [tincture.grid :refer [Grid gutters justify direction align-items]]
   [tincture.paper :refer [Paper]]
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
   :padding (px 16)})

(defgroup main-style
  {:headline {:margin "32px !important"}})

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
       [Grid {:container true
              :spacing 16
              :class (<class root-style)}
        [Grid {:item true
               :xs 12}
         [Grid {:container true
                :class (<class spacing-styles :grid-container)
                :spacing @state
                :justify :center}
          [Grid {:item true}
           [Paper {:class (<class paper-style)}]]
          [Grid {:item true}
           [Paper {:class (<class paper-style)}]]
          [Grid {:item true}
           [Paper {:class (<class paper-style)}]]]]
        [Grid {:item true
               :xs 12}
         [Paper {:class (<class control-paper)}
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
  {:demo {:height (px 240)}
   :block {:margin-bottom (px 16)}})

(defn interactive []
  (let [state (r/atom {:direction :row
                       :justify :center
                       :align-items :center})]
    (fn []
      [Grid {:container true
             :spacing 16
             :class (<class root-style)}
       [Grid {:item true
              :xs 12}
        [Grid {:container true
               :class (<class interactive-styles :demo)
               :direction (:direction @state)
               :justify (:justify @state)
               :align-items (:align-items @state)
               :spacing 16}
         (for [n (range 3)]
           ^{:key n}
           [Grid {:item true}
            [Paper {:class (<class paper-interactive-style n)}
             (str "Cell " (inc n))
             ]])]]
       [Grid {:item true
              :xs 12}
        [Paper {:class (<class control-paper)}
         [:div {:class (<class interactive-styles :block)}
          [typography {:class (<class spacing-styles :heading)
                       :gutter-bottom true
                       :variant :subtitle1}
           "direction"]
          [radio-group
           #(= (:direction @state) %)
           (deref #'tincture.grid/direction)
           #(swap! state assoc :direction (keyword %))]]
         [:div {:class (<class interactive-styles :block)}
          [typography {:class (<class spacing-styles :heading)
                       :gutter-bottom true
                       :variant :subtitle1}
           "justify"]
          [radio-group
           #(= (:justify @state) %)
           (deref #'tincture.grid/justify)
           #(swap! state assoc :justify (keyword %))]]

         [:div {:class (<class interactive-styles :block)}
          [typography {:class (<class spacing-styles :heading)
                       :gutter-bottom true
                       :variant :subtitle1}
           "align-items"]
          [radio-group #(= (:align-items @state) %)
           (deref #'tincture.grid/align-items)
           #(swap! state assoc :align-items (keyword %))]]]]])))

(defn basic-grid []
   [Grid {:container true
          :class (<class grid-style :root)
          :spacing 24}
    [Grid {:item true
           :xs 12}
     [Paper {:class (<class grid-style :paper)}
      "xs=12"]]
    [Grid {:item true
           :xs 6}
     [Paper {:class (<class grid-style :paper)}
      "xs=6"]]
    [Grid {:item true
           :xs 6}
     [Paper {:class (<class grid-style :paper)}
      "xs=6"]]
    [Grid {:item true
           :xs 3}
     [Paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [Grid {:item true
           :xs 3}
     [Paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [Grid {:item true
           :xs 3}
     [Paper {:class (<class grid-style :paper)}
      "xs=3"]]
    [Grid {:item true
           :xs 3}
     [Paper {:class (<class grid-style :paper)}
      "xs=3"]]])


(defn grid-with-breakpoints []
  [Grid {:container true
         :class (<class grid-style :root)
         :spacing 24}
   [Grid {:item true
          :xs 12}
    [Paper {:class (<class grid-style :paper)}
     "xs=12"]]
   [Grid {:item true
          :xs 12
          :sm 6}
    [Paper {:class (<class grid-style :paper)}
     "xs=12 sm=6"]]
   [Grid {:item true
          :xs 12
          :sm 6}
    [Paper {:class (<class grid-style :paper)}
     "xs=12 sm=6"]]
   [Grid {:item true
          :xs 6
          :sm 3}
    [Paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [Grid {:item true
          :xs 6
          :sm 3}
    [Paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [Grid {:item true
          :xs 6
          :sm 3}
    [Paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]
   [Grid {:item true
          :xs 6
          :sm 3}
    [Paper {:class (<class grid-style :paper)}
     "xs=6 sm=3"]]])

(defn main []
  [container
   [Grid {:container true
          :spacing 32}
    [Grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :class (<class main-style :headline)
                  :gutter-bottom true
                  :align :center}
      "Spacing"]
     [spacing]]
    [Grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :class (<class main-style :headline)
                  :gutter-bottom true
                  :align :center}
      "Basic grid"]
     [basic-grid]]
    [Grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :class (<class main-style :headline)
                  :gutter-bottom true
                  :align :center}
      "Grid with breakpoints"]
     [grid-with-breakpoints]]
    [Grid {:item true
           :xs 12}
     [typography {:variant :h4
                  :class (<class main-style :headline)
                  :gutter-bottom true
                  :align :center}
      "Interactive"]
     [interactive]]]])
