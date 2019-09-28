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
                            :medium 500
                            :bold 700})

(def ^:private colors {:light (rgb 0 0 0 0.87)
                       :dark "#fff"
                       :inherit "inherit"
                       :secondary-light (rgb 0 0 0 0.54)
                       :secondary-dark (rgb 255 255 255 0.7)})

(s/def ::valid-variants
  #{:h1 :h2 :h3 :h4 :h5 :h6 :subtitle1 :subtitle2 :body1 :body2 :caption :button
   :overline :sr-only :inherit})

(s/def ::valid-colors #{:light :dark :secondary-light :secondary-dark :inherit})
(s/def ::id (s/nilable string?))

(def default-font-family ["Roboto" "Helvetica" "Arial" "sans-serif"])

(defn- variants
  [variant color]
  (let [color (get colors color)
        font (or @(rf/subscribe [:tincture.font/family])
                 default-font-family)]
    (variant {:h1 {:color color
                   :font-family font
                   :font-size (rem 6)
                   :font-weight (:light font-weight)
                   :line-height 1}

              :h2 {:color color
                   :font-family font
                   :font-size (rem 3.75)
                   :font-weight (:light font-weight),
                   :line-height 1}

              :h3 {:color color
                   :font-family font
                   :font-size (rem 3)
                   :font-weight (:regular font-weight)
                   :line-height 1.04}

              :h4 {:color color
                   :font-family font
                   :font-size (rem 2.125)
                   :font-weight (:regular font-weight)
                   :line-height 1.17}

              :h5 {:color color
                   :font-family font
                   :font-size (rem 1.5)
                   :font-weight (:regular font-weight)
                   :line-height 1.33}

              :h6 {:color color
                   :font-family font
                   :font-size (rem 1.25)
                   :font-weight (:medium font-weight)
                   :line-height 1.6}

              :subtitle1 {:color color
                          :font-family font
                          :font-size (rem 1)
                          :font-weight (:regular font-weight)
                          :line-height 1.75}

              :subtitle2 {:color color
                          :font-family font
                          :font-size (rem 0.875)
                          :font-weight (:medium font-weight)
                          :line-height 1.57}

              :body1 {:color color
                      :font-family font
                      :font-size (rem 1)
                      :font-weight (:regular font-weight)
                      :line-height 1.5}

              :body2 {:color color
                      :font-family font
                      :font-size (rem 0.875)
                      :font-weight (:regular font-weight)
                      :line-height 1.5}

              :caption {:color color
                        :font-family font
                        :font-size (rem 0.75)
                        :font-weight (:regular font-weight)
                        :line-height 1.66}

              :button {:color color
                       :font-family font
                       :font-size (rem 0.875)
                       :font-weight (:medium font-weight)
                       :text-transform :uppercase
                       :line-height 1.75}

              :overline {:color color
                         :font-family font
                         :font-size (rem 0.75)
                         :font-weight (:regular font-weight)
                         :text-transform :uppercase
                         :line-height 2.66}

              :sr-only {:position "absolute"
                        :height (px 1)
                        :width (px 1)
                        :overflow "hidden"}})))


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
(s/def ::paragraph boolean?)
(s/def ::gutter-bottom boolean?)
(s/def ::no-wrap boolean?)
(s/def ::component (s/or :str string? :fn fn? :kw keyword?))

(defn- typography-style
  [variant align font-style direction
   elevation gutter-bottom paragraph
   color no-wrap]
  (let [root {:margin 0}

        directions {:ltr {:direction "ltr"}
                    :rtl {:direction "rtl"}}

        no-wrap-style {:overflow :hidden
                       :text-overflow "ellipsis"
                       :white-space "nowrap"}

        font-styles {:italic {:font-style "italic"}}

        p-style {:margin-bottom (px 16)}

        gutter-bottom-style {:margin-bottom (em 0.35)}

        aligns {:left {:text-align "left"}
                :right {:text-align "right"}
                :center {:text-align "center"}
                :justify {:text-align "justify"}}]

    (with-meta
      (into {}
            (merge
             {:text-shadow (t/text-shadow elevation)}
             (variants variant color)
             (when paragraph p-style)
             (when gutter-bottom gutter-bottom-style)
             (when no-wrap no-wrap-style)
             (direction directions)
             (font-style font-styles)
             (align aligns)
             root))
      {:hint (name variant)})))

(defn Typography
  "Typography component. It contains reusable categories of text, each with an
  intended application and meaning according to material design guidelines.

  **Properties**

  * `:variant`. Pred: `#{:h1 :h2 :h3 :h4 :h5 :h6 :subtitle1 :subtitle2 :body1 :body2 :caption :button
   :overline :sr-only :inherit}`. Default `:body1`. Apply chosen variant style.

  * `:align`. Pred: `#{:inherit :left :right :center :justify}`. Default:
  `:left`. Set text alignment.

  * `:class`. Pred: `string?`. Default: `nil`. Classname to be applied to the
  typography component.

  * `:elevation`. Pred: `#{0 1 2 3}`. Default: `0`. Height of text shadow.

  * `:font-style`. Pred: `#{:normal :italic}`. Default: `:normal`. Sets the CSS
  property `font-style`.

  * `:on-click`. Pred: `fn?`. Default: `nil`. On click handler to be attached to
  the typography component.

  * `:direction`. Pred: `#{:ltr :rtl}`. Default: `:ltr`. Sets the CSS property
  direction.

  * `:component`. Pred: `(or string? fn? keyword?)`. Default `:div`. The
  component used for the root node. It can either be hiccup keywords or string,
  or another component like `tincture.paper/Paper`

  * `:gutter-bottom`. Pred `boolean?`. Default `false`. Toggles bottom margin.

  * `:paragraph`. Pred `boolean?`. Default `false`. Toggles bottom margin and
  changes root node to a &lt;p&gt; element.

  * `:color`. Pred: `#{:light :dark :secondary-light :secondary-dark :inherit}`.
  Default: `:light`. Choose a color matching either a `dark` theme or a `light`
  theme. Shortcut to setting a sensible color based on light or dark. If any
  other color is required just add a class and don't set this.

  * `:no-wrap`. Pred: `boolean?`. Default: `false`. If `true`, the text will not
  wrap, but instead will truncate with an ellipsis.

  Example usage:
  ```clojure
  [:div
    [Typography {:variant :h3
                 :gutter-bottom true}
      \"my headline\"]
    [Typography
      \"My body text\"]]
  ```
  "
  [{:keys [variant align class elevation font-style
           on-click direction component gutter-bottom
           paragraph color no-wrap id]
    :or {variant :body2
         font-style :normal
         align :left
         direction :ltr
         paragraph false
         component :div
         gutter-bottom false
         elevation 0
         color :light
         no-wrap false}}]
  (let [variant (check-spec variant ::valid-variants)
        align (check-spec align ::valid-aligns)
        class (check-spec class ::class)
        component (check-spec component ::component)
        elevation (check-spec elevation :tincture.core/valid-text-shadow-elevation)
        direction (check-spec direction ::valid-directions)
        font-style (check-spec font-style ::valid-font-styles)
        on-click (check-spec on-click ::on-click)
        gutter-bottom (check-spec gutter-bottom ::gutter-bottom)
        color (check-spec color ::valid-colors)
        paragraph (check-spec paragraph ::paragraph)
        no-wrap (check-spec no-wrap ::no-wrap)
        id (check-spec id ::id)]
    (into
     [(if paragraph :p (or component (variant mapping) :span))
      {:id id
       :on-click on-click
       :class [class (<class typography-style variant align font-style
                             direction elevation gutter-bottom paragraph
                             color no-wrap)]}]
     (r/children (r/current-component)))))

(def ^{:deprecated "0.3.0"} typography Typography)
