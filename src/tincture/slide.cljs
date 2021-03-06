(ns tincture.slide
  (:require [tincture.transitions :refer [Transition TransitionGroup CSSTransition]]
            [clojure.spec.alpha :as s]
            [tincture.core :refer [easing-presets create-transition]]
            [tincture.spec :refer [check-spec]]
            [herb.core :refer [defgroup <class]]
            [reagent.debug :refer [log]]
            [clojure.string :as str]
            [reagent.core :as r]))

(defn- base-style [duration easing]
  {:left 0
   :top 0
   :position "absolute"
   :transition (create-transition {:property [:transform :opacity]
                                   :duration duration
                                   :easing easing})})

(defn- enter-style [direction opacity]
  (cond->
      {:transform (direction {:left "translate(100%, 0)"
                              :right "translate(-100%, 0)"
                              :up "translate(0, 100%)"
                              :down "translate(0, -100%)"})}
    opacity (assoc :opacity 0)))

(defn- enter-active-style [duration easing opacity]
  (with-meta
    (cond-> {:transform "translate(0, 0)"}
      opacity (assoc :opacity 1))
    {:extend [base-style duration easing]}))

(defn- exit-style [opacity]
  (cond-> {:transform "translate(0, 0)"}
    opacity (assoc :opacity 1)))

(defn- exit-active-style [duration easing direction opacity]
  (with-meta
    (cond-> {:transform (direction {:left "translate(-100%, 0)"
                                    :right "translate(100%, 0)"
                                    :up "translate(0, -100%)"
                                    :down "translate(0, 100%)"})}
      opacity (assoc :opacity 0))
    {:extend [base-style duration easing]}))

(defn exit-done-style []
  {:display "none"})

(defn- slide-child
  [{:keys
    [duration style timeout on-exit on-exited on-enter on-entered
     unmount-on-exit mount-on-enter easing appear direction children in
     child-container-class transition-class opacity]}]
  [CSSTransition {:in in
                  :style style
                  :timeout timeout
                  :class transition-class
                  :classNames {:enter (<class enter-style direction opacity)
                               :enter-active (<class enter-active-style duration easing opacity)
                               :exit (<class exit-style opacity)
                               :exit-active (<class exit-active-style duration easing direction opacity)
                               :exit-done (<class exit-done-style)}
                  :unmountOnExit unmount-on-exit
                  :mountOnEnter mount-on-enter
                  :appear appear
                  :onExit on-exit
                  :onEnter on-enter
                  :onExited on-exited
                  :onEntered on-entered}
   (fn [state]
     (r/as-element
      (into [:div {:class child-container-class}]
            children)))])

(s/def ::direction #{:up :down :left :right})
(s/def ::duration pos-int?)
(s/def ::timeout pos-int?)
(s/def ::unmount-on-exit boolean?)
(s/def ::mount-on-enter boolean?)
(s/def ::class (s/nilable (s/or :str string? :vector vector?)))
(s/def ::easing :tincture.core/valid-easings)
(s/def ::appear boolean?)
(s/def ::on-entered fn?)
(s/def ::on-enter fn?)
(s/def ::on-exit fn?)
(s/def ::on-exited fn?)
(s/def ::enter boolean?)
(s/def ::exit boolean?)
(s/def ::transition string?)
(s/def ::child-container string?)
(s/def ::classes (s/keys :opt [::transition ::child-container]))
(s/def ::id (s/nilable string?))
(s/def ::opacity boolean?)

(defn Slide
  "Slide components in and out based on mount status
  using [react-transition-group](https://github.com/reactjs/react-transition-group)

  **Properties**

  Slide takes a map of properties, many properties passed to the slide component
  are passed through to TransitionGroup (container) or Transition (child).

  * `:direction`. Pred: `#{:up :down :left :right}`. Default: `:left`.
  Which direction the component should be animated.

  * `:duration`. Pred: `pos-int?`. Default `500`. The duration used for the slide
  animation in milliseconds.

  * `:timeout`. Pred `pos-int?`. Default `500`. The duration of the transition,
  in milliseconds. This prop is passed to the `Transition` component.

  * `:unmount-on-exit`. Pred `boolean?`. Default `false`. By default the child
  component stays mounted after it reaches the `exited` state. Set this if you'd
  prefer to unmount the component after it finishes exiting. This prop is passed
  to the `Transition` component.

  * `:mount-on-enter`. Pred `boolean?`. Default `false`. By default the child
  component is mounted immediately along with the parent `Transition` component.
  If you want to `lazy mount` the component on the first `(= :in true)` you can
  set mount-on-enter. After the first enter transition the component will stay
  mounted, even on `exited` , unless you also specify unmount-on-exit. This prop
  is passed to the Transition component.

  * `:class`. Pred `string?`. Default `nil`. String classname to be applied to
  the outer `TransitionGroup` component.

  * `:easing`. Pred `#{:ease-in-quad :ease-in-cubic :ease-in-quart
  :ease-in-quint :ease-in-expo :ease-in-circ :ease-out-quad :ease-out-cubic
  :ease-out-quart :ease-out-quint :ease-out-expo :ease-out-circ
  :ease-in-out-quad :ease-in-out-cubic :ease-in-out-quart :ease-in-out-quint
  :ease-in-out-expo :ease-in-out-circ}`. Default `:ease-in-out-cubic`. Which
  easing function to use for the CSS transitions.

  * `:appear`. Pred `boolean?`. Default `false`. Normally a component is not
  transitioned if it is shown when the `Transition` component mounts. If you
  want to transition on the first mount set appear to `true`, and the component
  will transition in as soon as the `Transition` mounts.

  * `:enter`. Pred `boolean`. Default `true`. Enable or disable enter
  transitions.

  * `:exit`. Pred `boolean`. Default `true`. Enable or disable enter
  transitions.

  * `:on-exit`. Pred `fn?`. Default `noop`. Callback fired before the `exiting`
  status is applied.

  * `:on-exited`. Pred `fn?`. Default `noop`. Callback fired after the `exited`
  status is applied.

  * `:on-enter`. Pred `fn?`

  * `:on-entered`. Pred `fn?`. Default `noop`. Callback fired after the
  `entered` status is applied. This prop is passed to the inner `Transition`
  component.

  * `:classes`. Pred: keys `#{:transition :child-container}`, vals `string?`.
  Default `nil`. A map of override classes, one for the `Transition` component,
  and one for the `child-container`, which is the innermost child.

  * `:opacity`. Pred: `boolean`. Default `false` If you want opacity added to
  the slide animation

  **Important note** It is important that height is set with CSS on the
  `TransitionGroup`, by passing a string with the `:class` key value pair. It is
  deliberately not set by this component since the height can be one of any
  number of things like 100%, 100vh or a hardcoded size like 200px. This
  component will not work unless a height it set.

  It is also important that you set a `:key` on the children of this component
  since that's whats used to differentiate between children when animating.

  **Example usage**
  ```clojure
  (let [images [\"url1\" \"url2\" \"url3\"]
        image (r/atom (first images))]
    (fn []
      [Slide {:direction :left
              :classes {:child-container \"my-child-class\"}
              :class \"my-main-class-with-included-height\"}
       [:div {:on-click #(swap! image (rand-nth images))
              :key @image}
        [:img {:src @image}]]]))
  ```
  "
  [{:keys [direction duration timeout unmount-on-exit mount-on-enter
           class easing appear enter exit on-exit on-exited on-enter
           on-entered classes id opacity]
    :or   {direction       :left
           duration        500
           timeout         500
           mount-on-enter  false
           unmount-on-exit false
           easing          :ease-in-out-cubic
           appear          false
           enter           true
           exit            true
           on-enter        #()
           on-exit         #()
           on-exited       #()
           on-entered      #()
           classes         {}
           opacity         false}}]
  (let [direction      (check-spec direction ::direction)
        duration       (check-spec duration ::duration)
        timeout        (check-spec timeout ::timeout)
        mount-on-enter (check-spec mount-on-enter ::mount-on-enter)
        class          (check-spec class ::class)
        id             (check-spec id ::id)
        easing         (check-spec easing ::easing)
        appear         (check-spec appear ::appear)
        enter          (check-spec enter ::enter)
        exit           (check-spec exit ::exit)
        on-exit        (check-spec on-exit ::on-exit)
        on-exited      (check-spec on-exited ::on-exited)
        on-enter       (check-spec on-enter ::on-enter)
        on-entered     (check-spec on-entered ::on-entered)
        {transition-class :transition child-container-class :child-container} (check-spec classes ::classes)
        opacity        (check-spec opacity ::opacity)
        children       (r/children (r/current-component))
        k              (or (-> children first meta :key)
                           (-> children first second :key))]
    [TransitionGroup {:class class
                      :id id
                      :enter enter
                      :exit  exit
                      :style {:position "relative"
                              :overflow "hidden"}

                      ;; Since the direction should change for exiting children
                      ;; as well, we need to reactivly update them
                      :childFactory (fn [child]
                                      (js/React.cloneElement child #js {:direction direction}))}

     ;; to access the passed props of transition group we need to create a react
     ;; component from the carousel-child transition.
     (let [child (r/reactify-component slide-child)]
       (r/create-element child #js {:key                   k
                                    :duration              duration
                                    :child-container-class child-container-class
                                    :transition-class      transition-class
                                    :timeout               timeout
                                    :on-exit               on-exit
                                    :on-exited             on-exited
                                    :on-enter              on-enter
                                    :on-entered            on-entered
                                    :unmount-on-exit       unmount-on-exit
                                    :mount-on-enter        mount-on-enter
                                    :easing                easing
                                    :appear                appear
                                    :direction             direction
                                    :children              children
                                    :opacity               opacity}))]))

(def ^{:deprecated "0.3.0"} slide Slide)
