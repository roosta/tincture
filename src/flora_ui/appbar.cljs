(ns flora-ui.appbar
  (:require-macros [flora-ui.macro :refer [defui]])
  (:require
   [re-frame.core :as rf]
   [flora-ui.utils :as utils]
   [herb.macro :refer-macros [with-style]]
   [flora-ui.typography :refer [typography]]
   [flora-ui.paper :refer [paper]]
   [flora-ui.icons :as icons]
   [garden.units :refer [px percent]]
   [reagent.core :as r]))

(defn appbar-style
  [theme pos color]
  (let [root {:display "flex"
              :flex-direction "column"
              :background-color color
              :width "100%"
              :box-sizing "border-box"
              :z-index (-> theme :z-index :app-bar)
              :flex-shrink 0}
        fixed {:position "fixed"
               :top 0
               :left "auto"
               :right 0}
        absolute {:position "absolute"
                  :top 0
                  :left "auto"
                  :right 0}
        sticky {:position "sticky"
                :top 0
                :left "auto"
                :right 0}
        static {:position "static"}]

    (with-meta
      (merge root (case pos
                  :fixed fixed
                  :absolute absolute
                  :sticky sticky
                  :static static))
      {:key pos})))

;; TODO Add mobile scale
(defn toolbar-style
  [theme]
  {:min-height (px 56)
   :display "flex"
   :align-items "center"
   :padding-left (px (* (-> theme :spacing :unit) 2))
   :padding-right (px (* (-> theme :spacing :unit) 2))})

(defui appbar
  (fn [{:keys [theme position elevation color]
        :or {position :static
             elevation 4
             color (-> theme :palette :primary :main)}}]
    [paper {:class (with-style appbar-style theme position color)
            :component :header
            :elevation elevation}
     (into [:div {:class (with-style toolbar-style theme)}]
           (r/children (r/current-component)))]))
