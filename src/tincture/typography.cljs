(ns tincture.typography
  "Typography component implementing [Material Design](https://material.io/design/typography/the-type-system.html#) guidelines.

  **Inspiration**:

  - [Material-ui Typography](https://github.com/mui-org/material-ui/blob/next/packages/material-ui/src/Typography/Typography.js)"
  (:require
   [garden.units :refer [px percent rem em]]
   [reagent.debug :as d]
   [clojure.string :as str]
   [tincture.cssfns :refer [rgb]]
   [tincture.spec :refer [check-spec]]
   [herb.core :refer-macros [<class]]
   [cljs.spec.alpha :as s :include-macros true]
   [reagent.core :as r]
   [tincture.core :as t]
   [re-frame.core :as rf]))

(def ^:private font-weight {:light 300
                            :regular 400
                            :medium 500})

(s/def ::valid-variants
  #{:h1 :h2 :h3 :h4 :h5 :h6 :subtitle1 :subtitle2 :body1 :body2 :caption :button
   :overline :sr-only :inherit})

(defn- variants
  [variant]
  (let [font-families @(rf/subscribe [:tincture/font-families])]
    (variant {:h1 {:color (rgb 0 0 0 0.87)
                   :font-family (:headline font-families)
                   :font-size (rem 6)
                   :font-weight (:light font-weight)
                   :line-height 1}

              :h2 {:color (rgb 0 0 0 0.87)
                   :font-family (:headline font-families)
                   :font-size (rem 3.75)
                   :font-weight (:light font-weight),
                   :line-height 1}

              :h3 {:color (rgb 0 0 0 0.87)
                   :font-family (:headline font-families)
                   :font-size (rem 3)
                   :font-weight (:regular font-weight)
                   :line-height 1.04}

              :h4 {:color (rgb 0 0 0 0.87)
                   :font-family (:headline font-families)
                   :font-size (rem 2.125)
                   :font-weight (:regular font-weight)
                   :line-height 1.17}

              :h5 {:color (rgb 0 0 0 0.87)
                   :font-family (:headline font-families)
                   :font-size (rem 1.5)
                   :font-weight (:regular font-weight)
                   :line-height 1.33}

              :h6 {:color (rgb 0 0 0 0.87)
                   :font-family (:headline font-families)
                   :font-size (rem 1.25)
                   :font-weight (:medium font-weight)
                   :line-height 1.6}

              :subtitle1 {:color (rgb 0 0 0 0.87)
                          :font-family (:body font-families)
                          :font-size (rem 1)
                          :font-weight (:regular font-weight)
                          :line-height 1.75}

              :subtitle2 {:color (rgb 0 0 0 0.87)
                          :font-family (:body font-families)
                          :font-size (rem 0.875)
                          :font-weight (:medium font-weight)
                          :line-height 1.57}

              :body1 {:color (rgb 0 0 0 0.87)
                      :font-family (:body font-families)
                      :font-size (rem 1)
                      :font-weight (:regular font-weight)
                      :line-height 1.5}

              :body2 {:color (rgb 0 0 0 0.87)
                      :font-family (:body font-families)
                      :font-size (rem 0.875)
                      :font-weight (:regular font-weight)
                      :line-height 1.5}

              :caption {:color (rgb 0 0 0 0.87)
                        :font-family (:body font-families)
                        :font-size (rem 0.75)
                        :font-weight (:regular font-weight)
                        :line-height 1.66}

              :button {:color (rgb 0 0 0 0.87)
                       :font-family (:body font-families)
                       :font-size (rem 0.875)
                       :font-weight (:medium font-weight)
                       :text-transform :uppercase
                       :line-height 1.75}

              :overline {:color (rgb 0 0 0 0.87)
                         :font-family (:body font-families)
                         :font-size (rem 0.75)
                         :font-weight (:regular font-weight)
                         :text-transform :uppercase
                         :line-height 2.66}})))


(def ^:private mapping
  {:h1 :h1
   :h2 :h2
   :h3 :h3
   :h4 :h4
   :h5 :h5
   :h6 :h6
   :subtitle1 :h6
   :subtitle2 :h6
   :body1 :p
   :body2 :p})

(s/def ::valid-aligns #{:inherit :left :right :center :justify})
(s/def ::valid-font-styles #{:normal :italic})
(s/def ::valid-directions #{:ltr :rtl})
(s/def ::class (s/nilable string?))
(s/def ::id (s/nilable string?))
(s/def ::on-click (s/nilable fn?))

(defn- typography-style
  [variant align font-style direction elevation]
  (let [k (str/join "-" [(name variant) (name align) (name font-style) (name direction) elevation])

        directions {:ltr {:direction "ltr"}
                    :rtl {:direction "rtl"}}

        font-styles {:italic {:font-style "italic"}}

        aligns {:left {:text-align "left"}
                :right {:text-align "right"}
                :center {:text-align "center"}
                :justify {:text-align "justify"}}]

    (with-meta
      (into {}
            (merge
             {:text-shadow (t/text-shadow elevation)}
             (variants variant)
             (direction directions)
             (font-style font-styles)
             (align aligns)))
      {:key k})))

(defn typography
  [{:keys [variant align class elevation font-style on-click direction component]
    :or {variant :body2
         font-style :normal
         align :left
         direction :ltr
         elevation 0}}]
  (let [variant (check-spec variant ::valid-variants)
        align (check-spec align ::valid-aligns)
        class (check-spec class ::class)
        elevation (check-spec elevation :tincture.core/valid-text-shadow-elevation)
        direction (check-spec direction ::valid-directions)
        font-style (check-spec font-style ::valid-font-styles)
        on-click (check-spec on-click ::on-click)]
    (into
     [(or component (variant mapping))
      {:on-click on-click
       :class [class (<class typography-style variant align font-style direction elevation)]}]
     (r/children (r/current-component)))))
