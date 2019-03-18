(ns tincture.grid
  "Grid component implementing flexbox grid.
  Inspiration:
  - https://github.com/kristoferjoseph/flexboxgrid/blob/master/src/css/flexboxgrid.css
  - https://github.com/mui-org/material-ui/blob/next/packages/material-ui/src/Grid/Grid.js
  - https://github.com/roylee0704/react-flexbox-grid"
  (:require
   [garden.units :refer [percent px]]
   [tincture.cssfns :refer [rgb linear-gradient calc]]
   [garden.core :refer [css]]
   [debux.cs.core :refer-macros [clog]]
   [goog.object :as gobj]
   [goog.dom :as dom]
   [reagent.core :as r]
   [garden.stylesheet :refer [at-media]]
   [herb.core :refer-macros [<class defgroup defglobal]]
   [clojure.string :as str]
   [re-frame.core :as rf]))

(def sizes #{:auto true 1 2 3 4 5 6 7 8 9 10 11 12})
(def gutters #{0 8 16 24 32 40})
(def breakpoints {:xs 0
                  :sm 600
                  :md 960
                  :lg 1280
                  :xl 1920})
(def step 5)
(def unit px)

(defn up [k]
  {:min-width (unit (get breakpoints k))})

(defn down [k]
  (if (= k :xl)
    (up :xs)
    (let [end-index (+ (.indexOf (keys breakpoints) k) 1)
          upper-bound (get breakpoints (nth (keys breakpoints) end-index))]
      {:max-width (unit (- upper-bound (/ step 100)))})))


(defn generate-grid [breakpoint]
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

(defn generate-gutter []
  (into {} (map
            (fn [spacing]
              [(keyword (str "spacing-" spacing))
               {:margin (str (/ (- spacing) 2) "px") #_(px (/ (- spacing) 2))
                :width #_(calc (percent 100) '+ (px spacing)) (str "calc(100% + " spacing "px)")}])
            (rest gutters))))

(defglobal flexbox-grid
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

(def styles
  {:container {:box-sizing :border-box
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

(defn grid-style
  [align-content align-items container? direction
   spacing item? justify wrap zero-min-width?]
  (let [gutter (generate-gutter)
        k (str/join "-" [(name align-content) (name align-items) (str container?) (name direction)
                         (str spacing) (str item?) (name justify) (name wrap)])
        styles (merge
                (when container? (:container styles))
                (when item? (:item styles))
                (when zero-min-width? (:zero-min-width styles))
                (when (not= spacing 0) (get gutter (keyword (str "spacing-" spacing))))
                (get styles (keyword (str "direction-" (name direction))))
                (get styles (keyword (str "wrap-" (name wrap))))
                (get styles (keyword (str "align-items-" (name align-items))))
                (get styles (keyword (str "align-content-" (name align-content))))
                (get styles (keyword (str "justify-" (name justify)))))]
    (with-meta
      styles
      (cond-> {:key k
               :group true}
        (not= spacing 0)
        (assoc :combinators {[:> :.flexbox-item]
                             {:padding (px (/ spacing 2)) }})))))

(defn grid
  [{:keys [align-content
           align-items
           class
           container?
           id
           direction
           spacing
           item?
           justify
           wrap
           zero-min-width?
           lg md sm xl xs]
    :or {align-content :stretch
         align-items :stretch
         container? false
         direction :row
         spacing 0
         item? false
         justify :flex-start
         wrap :wrap
         zero-min-width? false
         xl false
         lg false
         md false
         sm false
         xs false}}]
  (let [class* (<class grid-style
                       align-content
                       align-items
                       container?
                       direction
                       spacing
                       item?
                       justify
                       wrap
                       zero-min-width?)]
    (into
     [:div {:id id
            :class [class*
                    class
                    (when item? "flexbox-item")
                    (when xs (str "grid-xs-" (if (keyword? xs) (name xs) xs)))
                    (when sm (str "grid-sm-" (if (keyword? sm) (name sm) sm)))
                    (when md (str "grid-md-" (if (keyword? md) (name md) md)))
                    (when lg (str "grid-lg-" (if (keyword? lg) (name lg) lg)))
                    (when xl (str "grid-xl-" (if (keyword? xl) (name xl) xl)))]}]
     (r/children (r/current-component))))
  )
