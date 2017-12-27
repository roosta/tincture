(ns flora-ui.paper
  #_(:require
   [cljs-css-modules.macro :refer-macros [defstyle]]
   [re-frame.core :as rf]
   [audioskop.components.typography :refer [typography]]
   [audioskop.copy :as copy]
   [clojure.string :as s]
   [garden.units :refer [px percent]]
   [reagent.core :as r]
   [audioskop.utils :as utils]))

#_(defstyle paper-style
  [:.root {:background (:paper-background copy/common)}])

#_(defn paper
  [{:keys [class elevation]
    :or {elevation 0}}]
  (into
   [:div {:style {:box-shadow (utils/box-shadow elevation)}
          :class (s/join " " [(:root paper-style) class])}]
   (r/children (r/current-component))))
