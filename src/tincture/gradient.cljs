(ns tincture.gradient
  (:require
   [cljs.spec.alpha :as s :include-macros true]
   [clojure.string :as str]
   [orchestra-cljs.spec.test :as st]
   [tincture.utils :as utils])
  (:require-macros
   [tincture.macros :as macros]))

(defn- ui-gradient
  [name]
  "Loads gradients from a JSON source as per format here:
   https://github.com/Ghosh/uiGradients/blob/master/gradients.json
  source: https://github.com/rm-hull/inkspot"
  (let [gradients (macros/ui-gradients "gradients.json")
        k (utils/name->kword name)]
    (k gradients)))

(defn- css-gradient
  "Takes a direction and a vector of colors and returns a set with CSS strings"
  [direction colors]
  (let [color-str (str/join ", " colors)]
    #{(first colors)
      (str "-webkit-linear-gradient(to right, " color-str ")")
      (str "linear-gradient(to right, " color-str ")")}))

(defn gradient
  "Takes a name, that should correspond with gradients listed at uigradients.com
  and can be either string or keyword, and a direction: #{:left :right :up :down}
  Returns a set of CSS strings used with a :background property passed to
  Garden.

  Example
  `(garden.core/css [:h2 {:background (gradent :vice-city :left)}])`"
  [name direction]
  (let [colors (ui-gradient name)]
    (css-gradient direction colors)))

(s/def ::valid-directions #{:left :right :up :down})

(s/fdef css-gradient
  :args (s/cat :direction ::valid-directions
               :colors (s/coll-of string? :kind vector? :min-count 2 :distinct true)))


(st/instrument)
