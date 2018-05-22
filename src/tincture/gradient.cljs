(ns tincture.gradient
  (:require
   [cljs.spec.alpha :as s :include-macros true]
   [clojure.string :as str]
   [reagent.debug :refer [log]]
   [tincture.utils :as utils])
  (:require-macros
   [tincture.macros :as macros]))

(def collection (macros/ui-gradients "gradients.json"))

(defn css
  "Takes a name, that should correspond with gradients listed at uigradients.com
  and can be either string or keyword, and a direction: #{:left :right :up :down}
  Returns a set of CSS strings used with a :background property passed to
  Garden. The return value contains browser fallbacks.

  Example
  `(garden.core/css [:h2 {:background (gradient/css :vice-city :left)}])`"
  ([palette-name]
   (css palette-name :left))
  ([palette-name direction]
   (let [kw (utils/name->kword palette-name)
         colors (kw collection)
         gradient-str (str "linear-gradient(to " (name direction) ", " (str/join ", " colors) ")")]
     #{(first colors)
       (str "-webkit-" gradient-str)
       gradient-str})))

(s/def ::ui-gradient-keyword (->> collection (map key) set))
(s/def ::directions #{:left :right :up :down})
(s/def ::hex-color (s/and string? #(re-matches #"^#(?:[0-9a-fA-F]{3}){1,2}$" %)))
(s/def ::palette-name (s/or :keyword ::ui-gradient-keyword
                            :palette-name (s/and string? #(s/valid? ::ui-gradient-keyword (utils/name->kword %)))))

(s/fdef css
  :args (s/alt :single-arg ::palette-name
               :double-arg (s/cat :name ::palette-name
                                  :direction ::directions))
  :ret (s/coll-of string? :kind set? :min-count 3))


