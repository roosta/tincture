(ns demo.slide
  (:require
   [tincture.icons :refer [chevron-left
                       chevron-right
                       chevron-up
                       chevron-down]]
   [reagent.debug :refer [log]]
   [herb.core :refer-macros [<class defgroup]]
   [tincture.core :as core]
   [garden.units :refer [px]]
   [tincture.container :refer [container]]
   [tincture.slide :refer [slide]]
   [reagent.core :as r])
  )

(def colors ["#ef3e36" "#584b53" "#2e282a" "#9d5c63" "#4c5454"])

(defgroup style
  {:slide {:width (px 600)
           :height (px 600)
           :box-shadow "inset 2px 2px 5px rgba(154, 147, 140, 0.5), 1px 1px 5px rgba(255, 255, 255, 1)"}
   :container {:height "100vh"
               :display "flex"
               :justify-content "center"
               :align-items "center"}
   :chevron {:width "100px"
             :cursor "pointer"
             :fill "#333"
             :height "100px"}
   :child {:height "100%"
           :width "100%"}
   :child-container {:height "100%"
                     :width "100%"}
   :row {:display "flex"
         :width "100%"
         :flex "0 1 auto"
         :justify-content "center"
         :align-items "center"}})

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
      [container {:class (<class style :container)}
       [:div
        [:div {:class (<class style :row)
               :on-click #(swap! state (on-click :up))}
         [chevron-up {:class (<class style :chevron)}]]
        [:div {:class (<class style :row)}
         [:div {:on-click #(swap! state (on-click :left))}
          [chevron-left {:class (<class style :chevron)}]]
         (let [color (->> (count colors)
                          (mod (:n @state))
                          (nth colors))]
           [:div
            [slide {:class (<class style :slide)
                    :classes {:child-container (<class style :child-container)}
                    :direction (:dir @state)}
             ^{:key color}
             [:div {:style {:background-color color}
                    :class (<class style :child)}]]])
         [:div {:on-click #(swap! state (on-click :right))}
          [chevron-right {:class (<class style :chevron)}]]]
        [:div {:class (<class style :row)
               :on-click #(swap! state (on-click :down))}
         [chevron-down {:class (<class style :chevron)}]]]])))
