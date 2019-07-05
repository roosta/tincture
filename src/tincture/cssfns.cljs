(ns tincture.cssfns
  (:require-macros [garden.def :refer [defcssfn]])
  (:require
   [tincture.spec :refer [check-spec]]
   [cljs.spec.alpha :as s :include-macros true]))

(defcssfn linear-gradient
  [dir color1 position1 color2 position2]
  [dir [color1 position1] [color2 position2]])

(s/def ::percent
  (letfn [(pred [s]
            (re-matches #"^(\d\d?|100?)%$" (str s)))]
    (s/spec pred)))

(s/def ::color (s/or :percent ::percent :int (s/int-in 0 256)))
(def ^:private color-msg "Invalid color passed to rgb cssfn, 0-255 or percent 0%-100%")
(def ^:private alpha-msg "Invalid alpha value, has to be a float between 0 and 1 inclusive")

(s/def ::alpha
  (letfn [(pred [s]
            (and (float? s)
                 (<= s 1)
                 (>= s 0)))]
    (s/spec pred)))

(defcssfn
  ^{:doc "Create a RGB(A) color string by passing red green blue and optionally alpha

Example usage:
```clojure
(garden.core/css [:.my-class {:color (rgb 255 255 255 0.2)}])

;;=> \".my-class {color: rgb(255, 255, 255, 0.2);}\"
```
"}
  rgb
  ([r g b]
   (let [r (check-spec r ::color color-msg)
         g (check-spec g ::color color-msg)
         b (check-spec b ::color color-msg)]
     [r g b]))
  ([r g b a]
   (let [r (check-spec r ::color color-msg)
         g (check-spec g ::color color-msg)
         b (check-spec b ::color color-msg)
         a (check-spec a ::alpha alpha-msg)
         ]
     [r g b a])))

(defcssfn url)

(defcssfn calc
  [& args]
  [args])
