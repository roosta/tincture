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
  [theme elevation]
  {:box-shadow ((:shadows theme) elevation)
   :background-color (-> theme :palette :background :paper)})

(defui paper
  (fn [{:keys [theme class elevation]
        :or {elevation 0}}]
    (into
     [:div {:class (s/join " " [(with-style paper-root theme elevation) class])}]
     (r/children (r/current-component)))))
