(ns tincture.cssfns
  (:require-macros [garden.def :refer [defcssfn]]))

(defcssfn linear-gradient
  [dir color1 position1 color2 position2]
  [dir [color1 position1] [color2 position2]])

(defcssfn
  ^{:doc "Create a RGB(A) color string by passing red green blue and optionally alpha

Example usage:
```clojure
(garden.core/css [:.my-class {:color (rgb 255 255 255 0.2)}])

;;=> \".my-class {color: rgb(255, 255, 255, 0.2);}\"
```
"}
  rgb
  ([r g b] [r g b])
  ([r g b a] [r g b a]))

(defcssfn url)

(defcssfn calc
  [& args]
  [args])
