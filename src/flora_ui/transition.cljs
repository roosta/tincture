(ns flora-ui.transition
  (:require [goog.object :as gobj]
            [cljsjs.react-transition-group]
            [reagent.core :as r]))

(def css-transition (r/adapt-react-class (gobj/get js/ReactTransitionGroup "CSSTransition")))
(def transition (r/adapt-react-class (gobj/get js/ReactTransitionGroup "Transition")))
(def transition-group (r/adapt-react-class (gobj/get js/ReactTransitionGroup "TransitionGroup")))
