(ns site.typography
  (:require [reagent.core :as r]
            [garden.units :refer [px]]
            [tincture.typography :refer [Typography]]
            [garden.units :refer [px]]
            [tincture.paper :refer [Paper]]
            [tincture.grid :refer [Grid]]
            [tincture.container :refer [Container]]
            [tincture.core :refer [clamp]]
            [clojure.string :as str]
            [reagent.debug :refer [log]]
            [herb.core :refer-macros [<class defgroup]]))

(defn lorum-ipsum
  [cnt]
  (let [lorem ["Lorem ipsum dolor sit amet, consectetur adipiscing elit."
               "Donec non justo lectus."
               "Aenean non blandit turpis, posuere feugiat ipsum."
               "Duis consectetur eu tortor ut vestibulum."
               "Morbi vitae dui augue."
               "Quisque eget neque ac diam dictum iaculis vitae id ipsum."
               "Pellentesque commodo purus eleifend ornare tincidunt."
               "Etiam aliquet eros sed scelerisque commodo."]]
    (str/join " " (take (clamp cnt 0 (count lorem)) lorem))))

(def text
  {:h1        "h1. Heading"
   :h2        "h2. Heading"
   :h3        "h3. Heading"
   :h4        "h4. Heading"
   :h5        "h5. Heading"
   :h6        "h6. Heading"
   :subtitle1 (str "subtitle1. " (lorum-ipsum 2))
   :subtitle2 (str "subtitle2. " (lorum-ipsum 2))
   :body1     (str "body1. " (lorum-ipsum 8))
   :body2     (str "body2. " (lorum-ipsum 8))
   :button    "button text"
   :caption   "caption text"
   :overline  "overline text"})

(defgroup styles
  {:root {}
   :container {:height "100vh"}
   :grid {:padding (px 16)}}
  )

(defn main
  []
  [Container {:class (<class styles :root)}
   [Grid {:container true
          :class (<class styles :container)
          :justify :center
          :align-items :center}
    [Grid {:item true
           :component Paper
           :class (<class styles :grid)
           :md 10
           :xs 12}
     (for [variant
           [:h1 :h2 :h3 :h4 :h5 :h6 :subtitle1 :subtitle2 :body1 :body2 :button
            :caption :overline]]
       ^{:key variant}
       [:div
        [Typography {:variant variant}
         (variant text)]])]]])
