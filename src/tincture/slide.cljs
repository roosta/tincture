; ----------------------
; Slide component
; ----------------------
; Each child must have a unique key for css-transition-group to work.
; Also each child needs a predefined height and width. Dynamic stuff won't work.
; I might spec this component, to ensure this someday
(ns flora-ui.slide
  #_(:require [goog.object :as gobj]
            [reagent.core :as r]
            [cljs-css-modules.macro :refer-macros [defstyle]]
            [audioskop.components.transition :refer [css-transition transition-group]]
            [reagent.debug :as d]))

;; NOTE: An element that is to be slided needs to have a height defined on the
;; root slide container. Originally the height value in root below is
;; suppose to be a fallback, but the way JSS works its hard to control
;; precedence in two different classes
#_(defstyle slide-style
  [:.root {:position "relative"
           :height "100%"
           :width    "100%"
           :overflow "hidden"}]
  [:.enter-left         {:transform "translate(100%, 0)"}]
  [:.enter-right        {:transform "translate(-100%, 0)"}]
  [:.enter-up           {:transform "translate(0, 100%)"}]
  [:.enter-down         {:transform "translate(0, -100%)"}]
  [:.enter-active       {:transform "translate(0, 0)"}]
  [:.exit               {:transform "translate(0, 0)"}]
  [:.exit-active-up     {:transform "translate(0, -100%)"}]
  [:.exit-active-down   {:transform "translate(0, 100%)"}]
  [:.exit-active-left   {:transform "translate(-100%, 0)"}]
  [:.exit-active-right  {:transform "translate(100%, 0)"}]
  [:.child {:left       0
            :top        0
            :width      "100%"
            :height     "100%"
            :position   "absolute"
            :transition "opacity 400ms ease-out, transform 400ms ease-out"}])

#_(defn slide
  [{:keys [direction]
    :or {direction "left"}}]
  (into
   [transition-group {:class (:root slide-style)}]
   (map
    (fn [child]
      (let [k (or (:key (second child))
                  (:key (meta child)))]
        (assert k "You need to provide a key for child elements")
        [css-transition
         {:timeout 500
          :key k
          :class (:child slide-style)
          :class-names {:enter ((keyword (str "enter-" direction)) slide-style)
                        :enter-active (:enter-active slide-style)
                        :exit (:exit slide-style)
                        :exit-active ((keyword (str "exit-active-" direction)) slide-style)}}
         [:div
          child]]))
    (r/children (r/current-component)))))
