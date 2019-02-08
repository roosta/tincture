(ns tincture.cssfns
  (:require-macros [garden.def :refer [defcssfn]]))

(defcssfn linear-gradient
  [dir c1 p1 c2 p2]
  [dir [c1 p1] [c2 p2]])

(defcssfn rgb
  ([c1 c2 c3] [c1 c2 c3])
  ([c1 c2 c3 a] [c1 c2 c3 a]))

(defcssfn url)

(defcssfn calc
  [& args]
  [args])
