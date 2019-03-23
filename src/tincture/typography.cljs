(ns tincture.typography
  "Typography component implementing [Material Design](https://material.io/design/typography/the-type-system.html#) guidelines.

  **Inspiration**: \n
  - [Material-ui Typography](https://github.com/mui-org/material-ui/blob/next/packages/material-ui/src/Typography/Typography.js)"
  (:require
   [garden.units :refer [px percent]]
   [reagent.debug :as d]
   [clojure.string :as str]
   [herb.core :refer-macros [<class]]
   [cljs.spec.alpha :as s :include-macros true]
   [reagent.core :as r]
   [tincture.core :as t]
   [re-frame.core :as rf]))

(def font-weight {:light 300
                  :regular 400
                  :medium 500})

(s/def ::valid-variants #{:title :display1 :display2
                          :display3 :display4 :subheading
                          :headline :button :body1 :body2})

(defn variants
  [variant]
  (let [font-families @(rf/subscribe [:tincture/font-families])]
    (variant {:display1 {:font-size (px 34)
                         :font-weight (:regular font-weight)
                         :font-family (:headline font-families)
                         :line-height "40px"}

              :display2 {:font-size (px 45)
                         :font-weight (:regular font-weight),
                         :font-family (:headline font-families)
                         :line-height (px 48)}

              :display3 {:font-size (px 56)
                         :font-weight (:regular font-weight)
                         :font-family (:headline font-families)
                         :letter-spacing "-.02em"
                         :line-height 1.35}

              :display4 {:font-size (px 112)
                         :font-weight (:light font-weight)
                         :font-family (:headline font-families)
                         :letter-spacing "-.04em"
                         :line-height 1}

              :subheading {:font-size (px 16)
                           :font-weight (:regular font-weight)
                           :font-family (:body font-families)
                           :line-height (px 24)}

              :headline {:font-size (px 24)
                         :font-weight (:regular font-weight)
                         :font-family (:headline font-families)
                         :line-height (px 32)}

              :title {:font-size (px 21)
                      :font-family (:headline font-families)
                      :line-height 1
                      :font-weight (:medium font-weight)}

              :button {:font-size 14
                       :text-transform "uppercase"
                       :font-weight (:medium font-weight)
                       :font-family (:body font-families)}

              :body1 {:font-size (px 14)
                      :font-weight (:regular font-weight)
                      :font-family (:body font-families)
                      :line-height (px 20)}

              :body2 {:font-size (px 14)
                      :font-weight (:regular font-weight)
                      :font-family (:body font-families)
                      :line-height (px 20)}})))

(def mapping
  {:display4 :h1
   :display3 :h1
   :display2 :h1
   :display1 :h1
   :headline :h1
   :title :h2
   :subheading :h3
   :button :span
   :body2 :aside
   :body1 :p})

(s/def ::valid-aligns #{:left :right :center :justify})
(s/def ::valid-styles #{:normal :italic})
(s/def ::valid-directions #{:ltr :rtl})

(defn typography-style
  [variant align style direction elevation]
  (let [k (str/join "-" [(name variant) (name align) (name style) (name direction) elevation])
        variants {:display1 (variants :display1)
                  :display2 (variants :display2)
                  :display3 (variants :display3)
                  :display4 (variants :display4)
                  :subheading (variants :subheading)
                  :headline (variants :headline)
                  :title (variants :title)
                  :button (variants :button)
                  :body1 (variants :body1)
                  :body2 (variants :body2)}

        directions {:ltr {:direction "ltr"}
                    :rtl {:direction "rtl"}}

        styles {:italic {:font-style "italic"}}

        aligns {:left {:text-align "left"}
                :right {:text-align "right"}
                :center {:text-align "center"}
                :justify {:text-align "justify"}}]

    (with-meta
      (merge
       {:text-shadow (t/text-shadow elevation)}
       (variant variants)
       (direction directions)
       (style styles)
       (align aligns))
      {:key k})))

;; TODO Add color?
(defn typography
  [{:keys [variant align class elevation style on-click direction]
    :or {variant :body1
         style :normal
         align :left
         direction :ltr
         elevation 0}}]
  {:pre [(s/valid? ::valid-aligns align)
         (s/valid? ::valid-variants variant)
         (s/valid? ::valid-directions direction)
         (s/valid? ::valid-styles style)]}
  (let [class* (<class typography-style variant align style direction elevation)]
    (into
     [(variant mapping) {:on-click on-click
                         :class (if class (str class " " class*)
                                    class*)}]
     (r/children (r/current-component)))))
