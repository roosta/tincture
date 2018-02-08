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
  [theme pos]
  (let [root {:display "flex"
              :flex-direction "column"
              :background "red"
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

(defui appbar
  (fn [{:keys [theme position]
        :or {position :static}}]
    (into [paper {:class (with-style appbar-style theme position)
                  :component :header
                  :elevation 4}]
          (r/children (r/current-component)))))
