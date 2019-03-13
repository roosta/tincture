(ns tincture.grid
  (:require
   [garden.units :refer [percent px]]
   [tincture.cssfns :refer [rgb linear-gradient calc]]
   [debux.cs.core :refer-macros [clog]]
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
                                   :max-width (str width "px")}))]
                    [kw style]))
                sizes)))

(defn generate-gutter [breakpoint]
  (into {} (map
            (fn [spacing]
              [(keyword (str "spacing-" (name breakpoint) "-" spacing))
               {:margin (str (/ (- spacing) 2) "px") #_(px (/ (- spacing) 2))
                :width #_(calc (percent 100) '+ (px spacing)) (str "calc(100% + " spacing "px)")}])
               (rest gutters))))

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
   spacing item? justify-content wrap xl lg md sm xs]
  (let [gutters (zipmap (keys breakpoints) (map generate-gutter (keys breakpoints)))
        k (str/join "-" [(name align-content) (name align-items) (str container?) (name direction)
                         (str spacing) (str item?) (name justify-content) (name wrap)
                         (str lg) (str md) (str sm) (str xl) (str xs)])]

    (merge (get styles (keyword (str "align-content-" (name align-content))))
           (get styles (keyword (str "align-items-" (name align-items))))
           (when container? (:container styles))
           (get styles (keyword (str "direction-" (name direction))))
           )
    gutters

    #_(with-meta
      {}
      {:key k
       :group true})))

(defn grid
  [{:keys [align-content
           align-items
           class
           container?
           id
           direction
           spacing
           item?
           justify-content
           wrap
           lg md sm xl xs
           ]
    :or {align-content :stretch
         align-items :stretch
         container? false
         direction :row
         spacing 0
         item false
         justify-content :flex-start
         wrap :wrap
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
                       justify-content
                       wrap
                       xl lg md sm xs)]
    (into
     [:div {:id id
            :class [class* class]}]
     (r/children (r/current-component))))
  )
