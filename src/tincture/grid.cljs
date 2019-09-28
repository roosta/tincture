(ns tincture.grid
  "Grid component implementing flexbox grid 12 columns responsive layout.

  **Inspiration**: \n
  * [Material-ui Grid](https://github.com/mui-org/material-ui/blob/next/packages/material-ui/src/Grid/Grid.js)
  * [Flexboxgrid](https://github.com/kristoferjoseph/flexboxgrid/blob/master/src/css/flexboxgrid.css)
  "
  (:require
   [garden.units :refer [percent px]]
   [tincture.cssfns :refer [rgb linear-gradient calc]]
   [garden.core :refer [css]]
   [goog.object :as gobj]
   [goog.dom :as dom]
   [clojure.spec.alpha :as s]
   [tincture.spec :refer [check-spec]]
   [reagent.core :as r]
   [garden.stylesheet :refer [at-media]]
   [herb.core :refer-macros [<class defgroup defglobal]]
   [clojure.string :as str]
   [re-frame.core :as rf]))

(def ^:private sizes #{:auto true 1 2 3 4 5 6 7 8 9 10 11 12})
(def ^:private direction #{:row :row-reverse :column :column-reverse})
(def ^:private justify #{:flex-start :center :flex-end :space-between :space-around :space-evenly})
(def ^:private align-items #{:flex-start :center :flex-end :stretch :baseline})
(def ^:private align-content #{:stretch :center :flex-start :flex-end :space-between :space-around})
(def ^:private wrap #{:nowrap :wrap :wrap-reverse})
(def ^:private gutters #{0 8 16 24 32 40})
(def ^:private step 5)
(def
  ^{:doc "Unit used when generating media queries, defaults to px"}
  unit px)
(def
  ^{:doc
    "Map of breakpoints to be used in media queries"}
  breakpoints
  {:xs 0 :sm 600 :md 960 :lg 1280 :xl 1920})

(s/def ::xs (s/or :size sizes :false false?))
(s/def ::sm (s/or :size sizes :false false?))
(s/def ::md (s/or :size sizes :false false?))
(s/def ::lg (s/or :size sizes :false false?))
(s/def ::xl (s/or :size sizes :false false?))
(s/def ::direction direction)
(s/def ::justify justify)
(s/def ::align-items align-items)
(s/def ::align-content align-content)
(s/def ::item boolean?)
(s/def ::container boolean?)
(s/def ::zero-min-width boolean?)
(s/def ::spacing gutters)
(s/def ::class (s/nilable (s/or :str string? :vector vector?)))
(s/def ::wrap wrap)
(s/def ::id (s/nilable string?))
(s/def ::style (s/nilable map?))
(s/def ::on-click (s/nilable fn?))

(defn up
  "
  Takes a breakpoint key (:xs :sm :md :lg :xl). Returns a media query that
  applies to everything above given breakpoint
  ```clojure
  (up :md)
  ;;=> {:min-width {:unit :px, :magnitude 960}}
  ```
  To be used in garden.stylesheet/at-media:
  ```clojure
  (at-media (up :md) {:color \"red\"})
  ```
  "
  [breakpoint-key]
  {:min-width (unit (get breakpoints breakpoint-key))})

(defn down
  "
  Takes a breakpoint key (:xs :sm :md :lg :xl). Returns a media query that applies
  to everything below given breakpoint.
  ```clojure
  (down :md)
  ;;=> {:max-width {:unit :px, :magnitude 1279.95}}
  ```
  To be used in garden.stylesheet/at-media:
  ```clojure
  (at-media (down :md) {:color \"red\"})
  ```
  "
  [breakpoint-key]
  (if (= breakpoint-key :xl)
    (up :xs)
    (let [end-index (+ (.indexOf (keys breakpoints) breakpoint-key) 1)
          upper-bound (get breakpoints (nth (keys breakpoints) end-index))]
      {:max-width (unit (- upper-bound (/ step 100)))})))


(defn- generate-grid
  "Generate a global grid, using breakpoints to achieve responsiveness"
  [breakpoint]
  (let [styles
        (mapv (fn [size]
                (let [kw (keyword (str "grid-" (name breakpoint) "-" (if (keyword? size) (name size) size)))
                      style (case size
                              true {:flex-basis 0
                                    :flex-grow 1
                                    :max-width "100%"}
                              :auto {:flex-basis "auto"
                                     :flex-grow 0
                                     :max-width "none"}
                              (let [width (/ (.round js/Math (* (/ size 12) 10e7) 10e5) 10e5)]
                                {:flex-basis (str width "%")
                                 :flex-grow 0
                                 :max-width (str width "%")}))]
                  [(keyword (str "." (name kw))) style]))
              sizes)]
    (if (= breakpoint :xs)
      styles
      (at-media (up breakpoint) styles))))

(defn- generate-gutter []
  (into {} (map
            (fn [spacing]
              [(keyword (str "spacing-" spacing))
               {:margin  (px (/ (- spacing) 2))
                :width (calc (percent 100) '+ (px spacing))}])
            (rest gutters))))

(defglobal ^:private flexbox-grid
  (mapv generate-grid (keys breakpoints)))

#_(defn attach-grid! []
  (let [el (dom/getElement "flexboxgrid")
        head (.-head js/document)
        css-str (css (mapv generate-grid (keys breakpoints)))]
    (if el
      (set! (.-innerHTML el) css-str)
      (let [el (.createElement js/document "style")]
        (set! (.-innerHTML el) css-str)
        (set! (.-id el) "flexboxgrid")
        (.setAttribute el "type" "text/css")
        (.appendChild head el)))))

#_(rf/reg-fx
 :tincture.grid/attach
 (fn []
   (attach-grid!)))

#_(rf/reg-event-fx
 :tincture.grid/initialize
 (fn [{:keys [db]}]
   {:db db
    :tincture.grid/attach nil}))

(def
  ^:private
  styles {:container {:box-sizing :border-box
                      :display :flex
                      :flex-wrap :wrap
                      :width (percent 100)}
          :item {:box-sizing :border-box
                 :margin 0}
          :zero-min-width {:min-width 0}
          :direction-column {:flex-direction :column}
          :direction-column-reverse {:flex-direction :column-reverse}
          :direction-row-reverse {:flex-direction :row-reverse}
          :wrap-nowrap {:flex-wrap :nowrap}
          :wrap-wrap-reverse {:flex-wrap :wrap-reverse}
          :align-items-center {:align-items :center}
          :align-items-flex-start {:align-items :flex-start}
          :align-items-flex-end {:align-items :flex-end}
          :align-items-baseline {:align-items :baseline}
          :align-content-center {:align-content :center}
          :align-content-flex-start {:align-content :flex-start}
          :align-content-flex-end {:align-content :flex-end}
          :align-content-space-between {:align-content :space-between}
          :align-content-space-around {:align-content :space-around}
          :justify-center {:justify-content :center}
          :justify-flex-end {:justify-content :flex-end}
          :justify-space-between {:justify-content :space-between}
          :justify-space-around{:justify-content :space-around}
          :justify-space-evenly {:justify-content :space-evenly}})

(defn- grid-style
  [align-content align-items container direction
   spacing item justify wrap zero-min-width]
  (let [gutter (generate-gutter)
        styles (merge
                {}
                (when container (:container styles))
                (when item (:item styles))
                (when zero-min-width (:zero-min-width styles))
                (when (not= spacing 0) (get gutter (keyword (str "spacing-" spacing))))
                (get styles (keyword (str "direction-" (name direction))))
                (get styles (keyword (str "wrap-" (name wrap))))
                (get styles (keyword (str "align-items-" (name align-items))))
                (get styles (keyword (str "align-content-" (name align-content))))
                (get styles (keyword (str "justify-" (name justify)))))]
    (with-meta
      styles
      (cond-> {}
        (not= spacing 0)
        (assoc :combinators {[:> :.flexbox-item]
                             {:padding (px (/ spacing 2)) }})))))

(defn- require-prop
  "Thow an exception if a propery doesn't have the required flags set (container, item)"
  [value props requires prop-name]
  (if (prop-name props)
    (if-not (requires props)
      (throw (ex-info (str "The property " prop-name " of tincture.grid must be used on " requires)
                      {:value value
                       :props props
                       :requires requires
                       :prop-name prop-name}))
      value)
    value))

(defn Grid
  "Responsive 12 column grid component that adapts to screen size, using the [CSS
  flexible box module](https://www.w3.org/TR/css-flexbox-1/).

  * There are two types of layouts,  `container` and `item`
  * Item widths are set in percentages, so they’re always fluid and sized relative to their parent element.
  * Items have padding to create the spacing between individual items.
  * There are five grid breakpoints: `xs`, `sm`, `md`, `lg`, and `xl`.

  **Properties**

  The grid component takes a map of properties, where each property can be one
  of a `set` of possible values:

  * `:align-content`, one of:
  `#{:stretch :center :flex-start :flex-end :space-between :space-around}`.
  Default: `:stretch`. Defines the `align-content` style property. It's applied for
  all screen sizes.

  * `:align-items`, one of:
  `#{:flex-start :center :flex-end :stretch :baseline}`. Default: `:stretch`
  Defines the `align-items` style property. It's applied for all screen sizes.

  * `:container`, one of: `#{true false}`. Default: `true`. If `true`, the
  component will have the flex container behavior. You should be wrapping *items*
  with a *container*.

  * `:direction`, one of: `#{:row :row-reverse :column :column-reverse}`.
  Default `:row`. Defines the `:flex-direction` style property. It is applied
  for all screen sizes.

  * `:spacing`, one of: `#{0 8 16 24 32 40}`. Default: `0`. Defines the space
  between the type `item` component. It can only be used on a type `container`
  component.

  * `:item`, one of: `#{true false}`. Default `false`. If `true`, the component
  will have the flex *item* behavior. You should be wrapping *items* with a
  *container*.

  * `:justify`, one of:
  `#{:flex-start :center :flex-end :space-between :space-around :space-evenly}`.
  Default `:flex-start`. Defines the `justify-content` style property. It is
  applied for all screen sizes.

  * `:wrap`, one of: `#{:nowrap :wrap :wrap-reverse}`. Default `:wrap`. Defines
  the `flex-wrap` style property. It's applied for all screen sizes.

  * `:zero-min-width`, one of: `#{true false}`. Default `false`, If `true`, it
  sets `min-width: 0` on the item.

  * `:xs`, one of: `#{:auto true false 1 2 3 4 5 6 7 8 9 10 11 12}`. Default
  `false`. Defines the number of grids the component is going to use. It's
  applied for all the screen sizes with the lowest priority.

  * `:sm`, one of: `#{:auto true false 1 2 3 4 5 6 7 8 9 10 11 12}`. Default
  `false`. Defines the number of grids the component is going to use. It's
  applied for the `sm` breakpoint and wider screens.

  * `:md`, one of: `#{:auto true false 1 2 3 4 5 6 7 8 9 10 11 12}`. Default
  `false`. Defines the number of grids the component is going to use. It's
  applied for the `md` breakpoint and wider screens.

  * `:lg`, one of: `#{:auto true false 1 2 3 4 5 6 7 8 9 10 11 12}`. Default
  `false`. Defines the number of grids the component is going to use. It's
  applied for the `lg` breakpoint and wider screens.

  * `:xl`, one of: `#{:auto true false 1 2 3 4 5 6 7 8 9 10 11 12}`. Default
  `false`. Defines the number of grids the component is going to use. It's
  applied for the `xl` breakpoint and wider screens.

  Additionally:
  * `:class` a string class name to be applied to the grid component
  * `:id` a string id to be applied to the grid component
  * `:component` the component to be used, default `:div`

  Example usage:
  ```clojure
  [Grid {:container true
         :class \"my-class-name\"
         :spacing 16
         :justify :center}
   [Grid {:item true
          :xs 12
          :sm 6}
    [:span \"column 1\"]]
   [Grid {:item true
          :xs 12
          :sm 6}
    [:span \"column 2\"]]
   [Grid {:item true
          :xs 12
          :sm 6}
    [:span \"column 3\"]]]
  ```
  "
  [{:keys [align-content
           align-items
           class
           container
           id
           direction
           spacing
           item
           justify
           wrap
           zero-min-width
           component
           on-click
           style
           lg md sm xl xs]
    :or   {align-content  :stretch
           align-items    :stretch
           container      false
           direction      :row
           spacing        0
           item           false
           justify        :flex-start
           wrap           :wrap
           zero-min-width false
           component      :div
           on-click       #()
           xl             false
           lg             false
           md             false
           sm             false
           xs             false}
    :as   props}]
  (let [align-content  (-> align-content (check-spec ::align-content) (require-prop props :container :align-content))
        align-items    (-> align-items (check-spec ::align-items) (require-prop props :container :align-items))
        class          (check-spec class ::class)
        container      (check-spec container ::container)
        id             (check-spec id ::id)
        direction      (-> direction (check-spec ::direction) (require-prop props :container :direction))
        spacing        (-> spacing (check-spec ::spacing) (require-prop props :container :spacing))
        item           (check-spec item ::item)
        justify        (-> justify (check-spec ::justify) (require-prop props :container :justify))
        wrap           (-> wrap (check-spec ::wrap) (require-prop props :container :justify))
        zero-min-width (check-spec zero-min-width ::zero-min-width)
        on-click       (check-spec on-click ::on-click)
        style          (check-spec style ::style)
        lg             (-> lg (check-spec ::lg) (require-prop props :item :lg))
        md             (-> md (check-spec ::md) (require-prop props :item :md))
        sm             (-> sm (check-spec ::sm) (require-prop props :item :sm))
        xl             (-> xl (check-spec ::xl) (require-prop props :item :xl))
        xs             (-> xs (check-spec ::xs) (require-prop props :item :xs))
        class* (<class
                grid-style
                align-content
                align-items
                container
                direction
                spacing
                item
                justify
                wrap
                zero-min-width)]
      (into
       [component {:id    id
                   :on-click on-click
                   :style style
                   :class (vec
                           (flatten
                           [class*
                            class
                            (when item "flexbox-item")
                            (when xs (str "grid-xs-" (if (keyword? xs) (name xs) xs)))
                            (when sm (str "grid-sm-" (if (keyword? sm) (name sm) sm)))
                            (when md (str "grid-md-" (if (keyword? md) (name md) md)))
                            (when lg (str "grid-lg-" (if (keyword? lg) (name lg) lg)))
                            (when xl (str "grid-xl-" (if (keyword? xl) (name xl) xl)))]))}]
       (r/children (r/current-component)))))

(def ^{:deprecated "0.3.0"} grid Grid)
