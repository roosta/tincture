(ns tincture.grid
  (:require
   [garden.units :refer [percent]]
   [tincture.core :refer [breakpoints]]
   [tincture.cssfns :refer [rgb linear-gradient]]
   [reagent.core :as r]
   [herb.core :refer-macros [<class]]
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
