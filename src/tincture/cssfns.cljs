(ns tincture.cssfns
  (:require-macros [garden.def :refer [defcssfn]]))

(defcssfn linear-gradient
  [dir c1 p1 c2 p2]
  [dir [c1 p1] [c2 p2]])

(defcssfn rgb
  ([r g b] [r g b])
  ([r g b a] [r g b a]))

(defcssfn url)

(defcssfn calc
  [& args]
  [args])
