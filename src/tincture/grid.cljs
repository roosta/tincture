(ns tincture.grid
  (:require
   [garden.units :refer [percent px]]
   [tincture.cssfns :refer [rgb linear-gradient calc]]
   [reagent.core :as r]
   [herb.core :refer-macros [<class defgroup]]
   [clojure.string :as str]))

(def sizes #{:auto true 1 2 3 4 5 6 7 8 9 10 11 12})
(def gutters #{0 8 16 24 32 40})
(def breakpoints {:xs 0
                  :sm 600
                  :md 960
                  :lg 1280
                  :xl 1920})

(def base-style
  {:box-sizing :border-box})

(def container-style
  {:display :flex
   :flex-wrap :wrap
   :width (percent 100)})

(def item-style
  {:margin 0})

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
  (into {} (map (fn [size]
                  [(keyword (str "grid-" (name breakpoint) "-" (if (keyword? size) (name size) size)))
                   (case size
                     true {:flex-basis 0
                           :flex-grow 1
                           :max-width "100%"}
                     :auto {:flex-basis "auto"
                            :flex-grow 0
                            :max-width "none"}
                     (let [width (/ (.round js/Math (* (/ size 12) 10e7) 10e5) 10e5)]
                       {:flex-basis (str width "%")
                        :flex-grow 0
                        :max-width (str width "px")}))])
                sizes)))

(defn generate-gutters [breakpoint]
  (into {} (map
            (fn [spacing]
              [(keyword (str "spacing-" (name breakpoint) "-" spacing))
               {:margin (str (/ (- spacing) 2) "px") #_(px (/ (- spacing) 2))
                :width #_(calc (percent 100) '+ (px spacing)) (str "calc(100% + " spacing "px)")}])
               (rest gutters))))

(defn styles []
  (let [variants
        {:container {:box-sizing :border-box
                     :display :flex
                     :flex-wrap :wrap
                     :width (percent 100)}
         :item {:box-sizing :border-box
                :margin 0}
         :zero-min-width {:min-width 0}
         :direction-xs-column {:flex-direction :column}
         :direction-xs-column-reverse {:flex-direction :column-reverse}
         :direction-xs-row-reverse {:flex-direction :row-reverse}
         :wrap-xs-nowrap {:flex-wrap :nowrap}
         :wrap-xs-wrap-reverse {:flex-wrap :wrap-reverse}
         :align-items-xs-center {:align-items :center}
         :align-items-xs-flex-start {:align-items :flex-start}
         :align-items-xs-flex-end {:align-items :flex-end}
         :align-items-xs-baseline {:align-items :baseline}
         :align-content-xs-center {:align-content :center}
         :align-content-xs-flex-start {:align-content :flex-start}
         :align-content-xs-flex-end {:align-content :flex-end}
         :align-content-xs-space-between {:align-content :space-between}
         :align-content-xs-space-around {:align-content :space-around}
         :justify-xs-center {:justify-content :center}
         :justify-xs-flex-end {:justify-content :flex-end}
         :justify-xs-space-between {:justify-content :space-between}
         :justify-xs-space-around{:justify-content :space-around}
         :justify-xs-space-evenly {:justify-content :space-evenly}}]
    ))

(defn grid-style
  [container? item?]
  (let [k (str/join "-" [(str "container-" container?) (str "item-" item?)])]
    (with-meta
      (merge base-style
             (cond
               container? container-style
               item? item-style))
      {:key k
       :group true}))
  )


(defn grid
  [{:keys [align-content
           align-items
           class
           container?
           id
           direction
           gutter
           item?
           justify-content
           wrap
           lg md sm xl xs]
    :or {align-content :stretch
         align-items :stretch
         container? false
         direction :row
         gutter 0
         item false
         justify-content :flex-start
         wrap :wrap
         lg false
         md false
         sm false
         xl false
         xs false}}]
  (let [class* (<class container? item?)]
    (into
     [:div {:id id
            :class [class* class]}]
     (r/children (r/current-component))))
  )
