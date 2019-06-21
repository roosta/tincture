(ns tincture.paper
  (:require [herb.core :refer-macros [<class]]
            [garden.units :refer [rem em px]]
            [tincture.core :as core]
            [reagent.core :as r]
            [clojure.spec.alpha :as s]
            [tincture.spec :refer [check-spec]]))

(defn- box-shadow [elevation]
  {:box-shadow (core/box-shadow elevation)})


(defn- paper-style [elevation square]
  ^{:extend [box-shadow elevation]
    :key (str elevation "-" square)
    :group true}
  {:box-sizing "border-box"
   :border-radius (if square 0 (px 4))})

(s/def ::class (s/nilable (s/or :str string? :vector vector?)))
(s/def ::id (s/nilable string?))
(s/def ::elevation :tincture.core/valid-box-shadow-elevation)
(s/def ::square boolean?)
(s/def ::on-click fn?)
(s/def ::component (s/or :str string? :fn fn? :kw keyword?))
(s/def ::style (s/nilable map?))

(defn Paper
  "Paper component, made to resemble a flat sheet of paper that acts as a
  container.

  **Properties**

  Paper takes a map of properties:

  * `:class`. Pred `string?`. Default `nil`. Classname string to be applied to
  paper component.

  * `:id`. Pred `string?`. Default `nil`. ID string to be applied to the paper
  component.

  * `:elevation`. Pred `#{0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
  21 22 23 24}`. Default `2`. The depth of the box-shadow applied to paper.
  Higher numbers makes the effect more pronounced.

  * `:square`. Pred `boolean?`. Default false. If border-radius should be
  applied, making the corners more rounded.

  * `:component`. Pred `(or string? fn? keyword?)`. Default `:div`. The
  component used for the root node.
  "
  [{:keys [class id elevation square component on-click style]
    :or {square false
         component :div
         elevation 2
         on-click #()}}]
  (let [class (check-spec class ::class)
        id (check-spec id ::id)
        elevation (check-spec elevation ::elevation)
        square (check-spec square ::square)
        component (check-spec component ::component)
        on-click (check-spec on-click ::on-click)
        style (check-spec style ::style)]
    (into [component {:id id
                      :style style
                      :on-click on-click
                      :class (vec (flatten [(<class paper-style elevation square) class]))}]
          (r/children (r/current-component)))))

(def ^{:deprecated "0.3.0"} paper Paper)
