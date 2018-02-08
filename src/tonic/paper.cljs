(ns flora-ui.paper
  (:require
   [clojure.string :as s]
   [reagent.debug :as d]
   [reagent.core :as r]
   [herb.macro :refer-macros [with-style]]
   [garden.units :refer [px percent]]
   [flora-ui.utils :as utils])
  (:require-macros [flora-ui.macro :refer [defui]]))

(defn paper-root
  [theme elevation square]
  {:box-shadow ((:shadows theme) elevation)
   :border-radius (if square 0 (px 2))
   :background-color (-> theme :palette :background :paper)})

(defui paper
  (fn [{:keys [theme class elevation component square]
        :or {elevation 0
             component :div
             square false}}]
    (into
     [component {:class (s/join " " [(with-style paper-root theme elevation square) class])}]
     (r/children (r/current-component)))))
