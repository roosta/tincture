(ns tincture.slide
  (:require [cljsjs.react-transition-group]
            [clojure.spec.alpha :as s]
            [tincture.core :refer [easing create-transition]]
            [clojure.string :as str]
            [reagent.core :as r]))

(def Transition (r/adapt-react-class (.-Transition js/ReactTransitionGroup)))
(def TransitionGroup (r/adapt-react-class (.-TransitionGroup js/ReactTransitionGroup)))
(def CSSTransition (r/adapt-react-class (.-CSSTransition js/ReactTransitionGroup)))

;; TODO Fix this, use Herb
(defn- get-style
  [state direction easing duration]
  (let [duration (str duration "ms")]
    (merge
     (case state
       "entering" {:transform (direction {:left "translate(100%, 0)"
                                          :right "translate(-100%, 0)"
                                          :up "translate(0, 100%)"
                                          :down "translate(0, -100%)"})
                   :opacity 0.01}
       "entered" {:transform "translate(0, 0)"
                  :opacity 1}
       "exiting" {:transform (direction {:left "translate(-100%, 0)"
                                         :right "translate(100%, 0)"
                                         :up "translate(0, -100%)"
                                         :down "translate(0, 100%)"})
                  :opacity 0.01}
       "exited" {:opacity 0})
     {:left 0
      :top 0
      :width "100%"
      :height "100%"
      :position "absolute"
      :transition (create-transition {:properties ["transform" "opacity"]
                                      :durations [duration duration]
                                      :easings [easing easing]})})))

(defn- slide-child
  [{:keys
    [duration timeout on-exit on-exited on-enter on-entered unmount-on-exit
     mount-on-enter easing appear direction children in]}]
   [Transition {:in in
                :timeout timeout
                :unmountOnExit unmount-on-exit
                :mountOnEnter mount-on-enter
                :appear appear
                :onExit on-exit
                :onEnter on-enter
                :onExited on-exited
                :onEntered on-entered}
    (fn [state]
        (r/as-element
         (into [:div {:style (get-style state direction easing duration)}]
               children)))])

(s/def ::direction #{:up :down :left :right})

(defn slide
  [{:keys [direction class duration timeout unmount-on-exit mount-on-enter
           easing appear enter exit on-exit on-exited on-enter on-entered]
    :or {direction :left duration 500 timeout 500 mount-on-enter false
         unmount-on-exit true easing :ease-in-out-cubic appear? false
         enter? true exit? true on-enter #() on-exit #() on-exited #() on-entered #()}}]
  {:pre [(s/valid? ::direction direction)]}
  (let [children (r/children (r/current-component))
        k (-> children first meta :key)]
    [TransitionGroup {:class class
                      :enter enter
                      :exit exit
                      :style {:position "relative"
                              :height "100%"
                              :width "100%"
                              :overflow "hidden"}

                      ;; Since the direction should change for exiting children
                      ;; as well, we need to reactivly update them
                      :childFactory (fn [child]
                                      (js/React.cloneElement child #js {:direction direction}))}

     ;; to access the passed props of transition group we need to create a react
     ;; component from the carousel-child transition.
     (let [child (r/reactify-component slide-child)]
       (r/create-element child #js {:key k
                                    :duration duration
                                    :timeout timeout
                                    :on-exit on-exit
                                    :on-exited on-exited
                                    :on-enter on-enter
                                    :on-entered on-entered
                                    :unmount-on-exit unmount-on-exit
                                    :mount-on-enter mount-on-enter
                                    :easing easing
                                    :appear appear
                                    :direction direction
                                    :children children}))]))
