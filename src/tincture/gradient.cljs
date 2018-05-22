(ns tincture.gradient
  (:require
   [cljs.spec.alpha :as s :include-macros true]
   [clojure.string :as str]
   [tincture.utils :as utils])
  (:require-macros
   [tincture.macros :as macros]))

(def gradients (macros/ui-gradients "gradients.json"))

(defn- css-gradient
  "Takes a direction and a vector of colors and returns a set with CSS strings"
  [direction colors]
  (let [gradient-str (str "linear-gradient(to " (name direction) ", " (str/join ", " colors) ")")]
    #{(first colors)
      (str "-webkit-" gradient-str)
      gradient-str}))

(defn gradient
  "Takes a name, that should correspond with gradients listed at uigradients.com
  and can be either string or keyword, and a direction: #{:left :right :up :down}
  Returns a set of CSS strings used with a :background property passed to
  Garden.

  Example
  `(garden.core/css [:h2 {:background (gradent :vice-city :left)}])`"
  [name direction]
  (let [kw (utils/name->kword name)
        colors (kw gradients)]
    (css-gradient direction colors)))

(s/def ::ui-gradient-keyword (->> gradients (map key) set))
(s/def ::directions #{:left :right :up :down})
(s/def ::hex-color (s/and string? #(re-matches #"^#(?:[0-9a-fA-F]{3}){1,2}$" %)))
(s/def ::css-set (s/coll-of string? :kind set? :min-count 3))

(s/fdef gradient
  :args (s/cat :name (s/or :keyword ::ui-gradient-keyword
                           :name (s/and string? #(s/valid? ::ui-gradient-keyword (utils/name->kword %))))
               :direction ::directions)
  :ret ::css-set)

(s/fdef css-gradient
  :args (s/cat :direction ::directions
               :colors (s/coll-of ::hex-color :kind vector? :min-count 2 :distinct true))
  :ret ::css-set)

