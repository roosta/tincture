(ns flora-ui.macro
  (:require [re-frame.core :as rf]
            [reagent.core :as r]))

(defmacro defui
  [name render-fn]
  `(defn ~name []
     (let [this# (r/current-component)
           props# (r/props this#)
           children# (r/children this#)
           theme# (rf/subscribe [:flora-ui/theme])]
       (into [~render-fn (merge {:theme @theme#} props#)]
             children#))))
