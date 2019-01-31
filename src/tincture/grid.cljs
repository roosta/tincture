(ns tincture.grid
  (:require
   [garden.units :refer [percent]]
   [tincture.core :refer [breakpoints]]
   [tincture.cssfns :refer [rgb linear-gradient]]
   [reagent.core :as r]
   [herb.core :refer-macros [<class defgroup]]
   [clojure.string :as str]))

(def sizes #{"auto" true 1 2 3 4 5 6 7 8 9 10 11 12})
(def gutters #{0 8 16 24 32 40})

(def base-style
  {:box-sizing :border-box})

(def container-style
  {:display :flex
   :flex-wrap :wrap
   :width (percent 100)})

(def item-style
  {:margin 0})

(defn generate-grid []
  )

(defn generate-gutters [])

(defgroup styles
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
   :justify-xs-space-evenly {:justify-content :space-evenly}})

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
         xs false
         }}]
  (let [class* (<class container? item?)]
    (into
     [:div {:id id
            :class [class* class]}]
     (r/children (r/current-component))))
  )
