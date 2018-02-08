(ns flora-ui.paper
  (:require
   [clojure.string :as s]
   [reagent.core :as r]
   [herb.macro :refer-macros [with-style]]
   [garden.units :refer [px percent]]
   [flora-ui.utils :as utils]))

(defn paper-root
  []
  {:background-color "white"}

  )

#_(defstyle paper-style
  [:.root {:background (:paper-background copy/common)}])

(defn paper
  [{:keys [class elevation]
    :or {elevation 0}}]
  (into
   [:div {:style {:box-shadow (utils/box-shadow elevation)}
          :class (s/join " " [(with-style paper-root) class])}]
   (r/children (r/current-component))))
