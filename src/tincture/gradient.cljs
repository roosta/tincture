(ns tincture.gradient
  "Generate CSS ready gradients from [uigradients.com](https://uigradients.com/)"
  (:require
   [cljs.spec.alpha :as s :include-macros true]
   [clojure.string :as str]
   [tincture.spec :refer [check-spec]]
   [reagent.debug :refer [log]]
   [inkspot.util :as util])
  (:require-macros
   [inkspot.macros :as macros]))

(def ^:private collection (macros/ui-gradients "gradients.json"))

(s/def ::ui-gradient-keyword (->> collection (map key) set))
(s/def ::direction #{:left :right :up :down})
(s/def ::hex-color (s/and string? #(re-matches #"^#(?:[0-9a-fA-F]{3}){1,2}$" %)))
(s/def ::palette-name (s/or :keyword ::ui-gradient-keyword
                            :palette-name (s/and string? #(s/valid? ::ui-gradient-keyword (util/name->kword %)))))

(defn css
  "Takes a name, that should correspond with gradients listed
  at [uigradients.com](uigradients.com) and can be either string or
  keyword, and a direction: #{:left :right :up :down} Returns a set of
  CSS strings used with a :background property passed to Garden. The
  return value contains browser fallbacks.

  **Example**

  ```clojure
  (garden.core/css [:h2 {:background (gradient/css :vice-city :left)}])
  ```
  "
  ([palette-name]
   (css palette-name :left))
  ([palette-name direction]
   (let [palette-name (check-spec palette-name ::palette-name) 
         direction (check-spec direction ::direction)
         kw (util/name->kword palette-name)
         colors (kw collection)
         gradient-str (str "linear-gradient(to " (name direction) ", " (str/join ", " colors) ")")]
     #{(first colors)
       (str "-webkit-" gradient-str)
       gradient-str})))

#_(s/fdef css
  :args (s/alt :single-arg ::palette-name
               :double-arg (s/cat :name ::palette-name
                                  :direction ::direction))
  :ret (s/coll-of string? :kind set? :min-count 3))
