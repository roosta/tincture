(ns tincture.container
  "Simple container component"
  (:require [herb.core
             :refer-macros [<class]
             :refer [join]]
            [garden.units :refer [em rem]]
            [reagent.core :as r]
            [tincture.spec :refer [check-spec]]
            [clojure.spec.alpha :as s]))

(defn- container-style
  []
  ^{:media {{:screen :only :min-width (em 48)} {:width (rem 49)}
            {:screen :only :min-width (em 64)} {:width (rem 65)}
            {:screen :only :min-width (em 75)} {:width (rem 76)}}}
  {:margin-right "auto"
   :margin-left "auto"})

(defn- container-fluid-style
  []
  {:padding-right (rem 2)
   :padding-left (rem 2)
   :margin-right "auto"
   :margin-left "auto"})

(s/def ::class string?)

(defn Container
  "Container component that scales with viewport by setting width and auto
  margins.

  **Properties***

  * `:class`. Pred `string?`. Default `nil`. Classname string to be applied to
  the container component
  "
  [{:keys [class]}]
  (into [:div {:class [class (<class container-style)]}]
        (r/children (r/current-component))))

(defn ContainerFluid
  "Container component that stays the width of the viewport, width a sensible
  padding applied to both sides.

  ** Properties**

  ContainerFluid takes a map of properties:

  `:class`. Pred `string?`. Default `nil`. Classname string to be applied to
  container component.
  "
  [{:keys [class]}]
  (let [class (check-spec class ::class)]
    (into [:div {:class [class (<class container-fluid-style)]}]
          (r/children (r/current-component)))))

(def ^{:deprecated "0.3.0"} container Container)
(def ^{:deprecated "0.3.0"} container-fluid ContainerFluid)
