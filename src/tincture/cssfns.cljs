(ns tincture.cssfns
  (:require-macros [garden.def :refer [defcssfn]]))

(defcssfn linear-gradient
  [dir color1 position1 color2 position2]
  [dir [color1 position1] [color2 position2]])

(defcssfn rgb
  ([r g b] [r g b])
  ([r g b a] [r g b a]))

(defcssfn url)

(defcssfn calc
  [& args]
  [args])
