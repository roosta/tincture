(ns site.slide
  (:require
   [tincture.icons :refer [ChevronLeft
                           ChevronRight
                           ChevronUp
                           ChevronDown]]
   [reagent.debug :refer [log]]
   [herb.core :refer-macros [<class defgroup]]
   [tincture.core :as core]
   [tincture.grid :refer [Grid]]
   [garden.units :refer [px]]
   [tincture.container :refer [Container]]
   [tincture.slide :refer [Slide]]
   [reagent.core :as r])
  )

(def colors ["#ef3e36" "#584b53" "#2e282a" "#9d5c63" "#4c5454"])

(defgroup style
  {:slide {:width (px 600)
           :height (px 600)
           :box-shadow "inset 2px 2px 5px rgba(154, 147, 140, 0.5), 1px 1px 5px rgba(255, 255, 255, 1)"}
   :container {:height "100vh"}
   :chevron {:width "100px"
             :cursor "pointer"
             :fill "#333"
             :height "100px"}
   :child {:height "100%"
           :width "100%"}
   :child-container {:height "100%"
                     :width "100%"}
   :row {
         ;; :display "flex"
         :width "100%"
         ;; :flex "0 1 auto"
         ;; :justify-content "center"
         ;; :align-items "center"
         }})

(defn on-click
  [direction]
  (fn [{n :n}]
    {:n (case direction
          :left (dec n)
          :down (dec n)
          :up (inc n)
          :right (inc n))
     :dir direction}))

(defn slide-demo []
  (let [state (r/atom {:n 0
                       :dir :left})]
    (fn []
      [Grid {:container true
             :justify :center
             :align-items :center
             :class (<class style :container)}
       [Grid {:item true}
        [Grid {:container true
               :justify :center
               :on-click #(swap! state (on-click :up))}
         [ChevronUp {:class (<class style :chevron)}]]
        [Grid {:container true
               :align-items :center}
         [:div {:on-click #(swap! state (on-click :left))}
          [ChevronLeft {:class (<class style :chevron)}]]
         (let [color (->> (count colors)
                          (mod (:n @state))
                          (nth colors))]
           [:div
            [Slide {:class (<class style :slide)
                    :classes {:child-container (<class style :child-container)}
                    :direction (:dir @state)}
             ^{:key color}
             [:div {:style {:background-color color}
                    :class (<class style :child)}]]])
         [:div {:on-click #(swap! state (on-click :right))}
          [ChevronRight {:class (<class style :chevron)}]]]
        [Grid {:container true
               :justify :center
               :on-click #(swap! state (on-click :down))}
         [ChevronDown {:class (<class style :chevron)}]]]])))
