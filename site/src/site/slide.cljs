(ns site.slide
  (:require
   [tincture.icons :refer [ChevronLeft
                           ChevronRight
                           ChevronUp
                           ChevronDown]]
   [reagent.debug :refer [log]]
   [herb.core :refer-macros [<class defgroup]]
   [tincture.core :as core]
   [goog.object :as gobj]
   [dommy.core :refer-macros [sel1 sel]]
   [tincture.typography :refer [Typography]]
   [cljsjs.hammer]
   [re-frame.core :as rf]
   [tincture.grid :refer [Grid]]
   [garden.units :refer [px percent]]
   [tincture.container :refer [Container]]
   [tincture.slide :refer [Slide]]
   [reagent.core :as r])
  )

(def colors ["#ef3e36" "#584b53" "#2e282a" "#9d5c63" "#4c5454"])

(defn slide-style []
  (let [md-down? @(rf/subscribe [:tincture/breakpoint-down :md])]
    {:width (if md-down? "100vw" (px 600))
     :height (if md-down? "100vh" (px 600))
     :box-shadow "inset 2px 2px 5px rgba(154, 147, 140, 0.5), 1px 1px 5px rgba(255, 255, 255, 1)"}))

(defgroup style
  {:type-container {:width "100%"
                    :height "100%"}
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

(defn on-up [state]
  (swap! state (on-click :up)))

(defn on-left [state]
  (swap! state (on-click :left)))

(defn on-right [state]
  (swap! state (on-click :right)))

(defn on-down [state]
  (swap! state (on-click :down)))

(defn slide-demo []
  (let [state (r/atom {:n 0
                       :dir :left})]
    (r/create-class
     {:component-did-mount
      (fn []
        (let [hammer (js/Hammer. (sel1 :#slide-root))]
          (.set (.get hammer "swipe") #js {:direction 30})
          (.on hammer "swiperight" #(on-right state))
          (.on hammer "swipeleft"  #(on-left state))
          (.on hammer "swipeup"    #(on-up state))
          (.on hammer "swipedown"  #(on-down state))))
      :reagent-render
      (fn []
        (let [md-up? @(rf/subscribe [:tincture/breakpoint-up :md])
              md-down? @(rf/subscribe [:tincture/breakpoint-down :md])]
          [Grid {:container true
                 :justify :center
                 :align-items :center
                 :class (<class style :container)}
           [Grid {:item true}
            (when md-up?
              [Grid {:container true
                     :justify :center
                     :on-click #(on-up state)}
               [ChevronUp {:class (<class style :chevron)}]])
            [Grid {:container true
                   :align-items :center}
             (when md-up?
               [:div {:on-click #(on-left state)}
                [ChevronLeft {:class (<class style :chevron)}]])
             (let [color (->> (count colors)
                              (mod (:n @state))
                              (nth colors))]
               [:div
                [Slide {:id "slide-root"
                        :class (<class slide-style)
                        :classes {:child-container (<class style :child-container)}
                        :direction (:dir @state)}
                 ^{:key color}
                 [:div {:style {:background-color color}
                        :class (<class style :child)}
                  (when md-down?
                    [Grid {:container true
                           :justify :center
                           :class (<class style :type-container)
                           :align-items :center}
                     [Typography {:variant :h6
                                  :color :dark}
                      "Swipe left, right, up, or down"]])]]])
             (when md-up?
               [:div {:on-click #(on-right state)}
                [ChevronRight {:class (<class style :chevron)}]])]
            (when md-up?
              [Grid {:container true
                     :justify :center
                     :on-click #(on-down state)}
               [ChevronDown {:class (<class style :chevron)}]])]]))})))
