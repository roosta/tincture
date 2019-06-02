(ns tincture.core
  (:require
   [cljs.spec.alpha :as s :include-macros true]
   [re-frame.core :as rf]
   [tincture.events]
   [debux.cs.core :refer-macros [clog dbg]]
   [tincture.utils :as utils]
   [tincture.grid :as grid]
   [tincture.subs]
   [clojure.string :as str])
  (:require-macros
   [tincture.macros :as macros]))

(def
  ^{:doc "Map of easing preset functions to be used in CSS transitions
         Source: https://gist.github.com/bendc/ac03faac0bf2aee25b49e5fd260a727d"}
  easing-presets
  {:ease-in-quad "cubic-bezier(.55, .085, .68, .53)"
   :ease-in-cubic "cubic-bezier(.550, .055, .675, .19)"
   :ease-in-quart "cubic-bezier(.895, .03, .685, .22)"
   :ease-in-quint "cubic-bezier(.755, .05, .855, .06)"
   :ease-in-expo "cubic-bezier(.95, .05, .795, .035)"
   :ease-in-circ "cubic-bezier(.6, .04, .98, .335)"

   :ease-out-quad "cubic-bezier(.25, .46, .45, .94)"
   :ease-out-cubic "cubic-bezier(.215, .61, .355, 1)"
   :ease-out-quart "cubic-bezier(.165, .84, .44, 1)"
   :ease-out-quint "cubic-bezier(.23, 1, .32, 1)"
   :ease-out-expo "cubic-bezier(.19, 1, .22, 1)"
   :ease-out-circ "cubic-bezier(.075, .82, .165, 1)"

   :ease-in-out-quad "cubic-bezier(.455, .03, .515, .955)"
   :ease-in-out-cubic "cubic-bezier(.645, .045, .355, 1)"
   :ease-in-out-quart "cubic-bezier(.77, 0, .175, 1)"
   :ease-in-out-quint "cubic-bezier(.86, 0, .07, 1)"
   :ease-in-out-expo "cubic-bezier(1, 0, 0, 1)"
   :ease-in-out-circ "cubic-bezier(.785, .135, .15, .86)"}
  )

(s/def ::valid-easings
  #{:ease-in-quad :ease-in-cubic :ease-in-quart :ease-in-quint
    :ease-in-expo :ease-in-circ :ease-out-quad :ease-out-cubic :ease-out-quart
    :ease-out-quint :ease-out-expo :ease-out-circ :ease-in-out-quad
    :ease-in-out-cubic :ease-in-out-quart :ease-in-out-quint :ease-in-out-expo
    :ease-in-out-circ})

(defn clamp
  "Clamps a value between a pair of boundary values. Takes a value and
  boundries max-value, min-value, and returns the a value within those bounds.
  Example:
  ```clojure
  (clamp 200 100 150)
  ;=> 150
  ```"
  [value min-value max-value]
  (max (min value max-value) min-value))

;; TODO deal with function values and multiples.
(s/def ::valid-timing-fns #{"ease" "ease-in" "ease-out" "ease-in-out" "linear" "step-start" "step-end"})

(defn index-of
  "Takes a collection and a value and returns the value's index in the given
  collection"
  [coll value]
  (some (fn [[idx item]]
          (when (= value item)
            idx))
        (map-indexed vector coll)))

(defn css-wrap-url
  "Wrap a url string in the url css function. Deprecated, use tincture.cssfns/url
  instead"
  {:deprecated "0.2.0"}
  [prop]
  (str "url(" prop ")"))

(s/def ::ct-durations (s/nilable (s/coll-of pos-int? :kind vector?)))
(s/def ::ct-properties (s/coll-of string? :kind vector? :distinct true))
(s/def ::ct-delays (s/nilable (s/coll-of pos-int? :kind vector?)))
(s/def ::ct-easings (s/nilable ::valid-easings))

(defn- pad
  "Pad out a collection with `n` size and input `value` that can either
  be a sequential collection (set/list/vector) or single value. The
  padding is the last value in collection or whatever single value was
  passed in"
  [n val]
  (if (sequential? val)
    (take n (concat val (repeat (last val))))
    (take n (concat [val] (repeat val)))))

(defn create-transition
  "Helper function that generates a transition string for multiple properties.

  **Options**

  create-transition takes a map of options:

  * `:property` CSS property/ies to transition. Can be single prop
  either string or keyword, a sequental collection of string or keyword.

  * `:duration` Duration applied to each property. A single positive
  int in milliseconds, or sequential collection of positive integers
  in milliseconds. Does not have to match the property count, it will
  get padded out to match the property count.

  * `:delay`, delay applied to each property. A single positive int in
  milliseconds, or a sequential collection of positive integers in
  milliseconds. Does not have to match property count, will get padded
  out based on the last value in collection, or if single value it is
  used for each property.

  * `:easing`, the easing function to be used on each property. Can be one of:
  `#{:ease-in-quad :ease-in-cubic :ease-in-quart :ease-in-quint :ease-in-expo
  :ease-in-circ :ease-out-quad :ease-out-cubic :ease-out-quart :ease-out-quint
  :ease-out-expo :ease-out-circ :ease-in-out-quad :ease-in-out-cubic
  :ease-in-out-quart :ease-in-out-quint :ease-in-out-expo :ease-in-out-circ}`
  or a sequential collection the same. Does not have to match property
  count, will get padded out based on either last value in collection.

  **Example usage**
  ```clojure
  {:transition (create-transition {:property [:transform :opacity]
                                   :duration [300 500]
                                   :easing [:ease-in-cubic :ease-out-cubic]})}
  ```
  Result:
  ```clojure
  {:transition \"transform 300ms cubic-bezier(.550, .055, .675, .19) 0ms, opacity 300ms cubic-bezier(.550, .055, .675, .19) 0ms\"}
  ```
  "
  [{:keys [property duration delay easing]
    :or {property :all 
         duration 300
         easing :ease-in-cubic
         delay 0}}]
  ;; transition: <property> || <duration> || <timing-function> || <delay> [, ...];
  (let [n (if (sequential? property) (count property) 1) 
        properties (map name (flatten [property]))  
        durations (pad n duration)
        easings (pad n easing)
        delays (pad n delay)
        transition (map (fn [p d dl e]
                          (str/join " " [p d dl e]))
                        properties
                        (map #(str % "ms") durations)
                        (map #(get easing-presets %) easings)
                        (map #(str % "ms") delays))]
    (str/join ", " transition)))

(s/def ::valid-box-shadow-elevation (set (range 25)))

(defn box-shadow
  "Takes a elevation and retuns a CSS box-shadow string Elevation can be one of
  `#{0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24}`"
  [elevation]
  {:pre [(s/valid? ::valid-box-shadow-elevation elevation)]}
  (get ["none"
        "0px 1px 3px 0px rgba(0, 0, 0, 0.2),0px 1px 1px 0px rgba(0, 0, 0, 0.14),0px 2px 1px -1px rgba(0, 0, 0, 0.12)"
        "0px 1px 5px 0px rgba(0, 0, 0, 0.2),0px 2px 2px 0px rgba(0, 0, 0, 0.14),0px 3px 1px -2px rgba(0, 0, 0, 0.12)"
        "0px 1px 8px 0px rgba(0, 0, 0, 0.2),0px 3px 4px 0px rgba(0, 0, 0, 0.14),0px 3px 3px -2px rgba(0, 0, 0, 0.12)"
        "0px 2px 4px -1px rgba(0, 0, 0, 0.2),0px 4px 5px 0px rgba(0, 0, 0, 0.14),0px 1px 10px 0px rgba(0, 0, 0, 0.12)"
        "0px 3px 5px -1px rgba(0, 0, 0, 0.2),0px 5px 8px 0px rgba(0, 0, 0, 0.14),0px 1px 14px 0px rgba(0, 0, 0, 0.12)"
        "0px 3px 5px -1px rgba(0, 0, 0, 0.2),0px 6px 10px 0px rgba(0, 0, 0, 0.14),0px 1px 18px 0px rgba(0, 0, 0, 0.12)"
        "0px 4px 5px -2px rgba(0, 0, 0, 0.2),0px 7px 10px 1px rgba(0, 0, 0, 0.14),0px 2px 16px 1px rgba(0, 0, 0, 0.12)"
        "0px 5px 5px -3px rgba(0, 0, 0, 0.2),0px 8px 10px 1px rgba(0, 0, 0, 0.14),0px 3px 14px 2px rgba(0, 0, 0, 0.12)"
        "0px 5px 6px -3px rgba(0, 0, 0, 0.2),0px 9px 12px 1px rgba(0, 0, 0, 0.14),0px 3px 16px 2px rgba(0, 0, 0, 0.12)"
        "0px 6px 6px -3px rgba(0, 0, 0, 0.2),0px 10px 14px 1px rgba(0, 0, 0, 0.14),0px 4px 18px 3px rgba(0, 0, 0, 0.12)"
        "0px 6px 7px -4px rgba(0, 0, 0, 0.2),0px 11px 15px 1px rgba(0, 0, 0, 0.14),0px 4px 20px 3px rgba(0, 0, 0, 0.12)"
        "0px 7px 8px -4px rgba(0, 0, 0, 0.2),0px 12px 17px 2px rgba(0, 0, 0, 0.14),0px 5px 22px 4px rgba(0, 0, 0, 0.12)"
        "0px 7px 8px -4px rgba(0, 0, 0, 0.2),0px 13px 19px 2px rgba(0, 0, 0, 0.14),0px 5px 24px 4px rgba(0, 0, 0, 0.12)"
        "0px 7px 9px -4px rgba(0, 0, 0, 0.2),0px 14px 21px 2px rgba(0, 0, 0, 0.14),0px 5px 26px 4px rgba(0, 0, 0, 0.12)"
        "0px 8px 9px -5px rgba(0, 0, 0, 0.2),0px 15px 22px 2px rgba(0, 0, 0, 0.14),0px 6px 28px 5px rgba(0, 0, 0, 0.12)"
        "0px 8px 10px -5px rgba(0, 0, 0, 0.2),0px 16px 24px 2px rgba(0, 0, 0, 0.14),0px 6px 30px 5px rgba(0, 0, 0, 0.12)"
        "0px 8px 11px -5px rgba(0, 0, 0, 0.2),0px 17px 26px 2px rgba(0, 0, 0, 0.14),0px 6px 32px 5px rgba(0, 0, 0, 0.12)"
        "0px 9px 11px -5px rgba(0, 0, 0, 0.2),0px 18px 28px 2px rgba(0, 0, 0, 0.14),0px 7px 34px 6px rgba(0, 0, 0, 0.12)"
        "0px 9px 12px -6px rgba(0, 0, 0, 0.2),0px 19px 29px 2px rgba(0, 0, 0, 0.14),0px 7px 36px 6px rgba(0, 0, 0, 0.12)"
        "0px 10px 13px -6px rgba(0, 0, 0, 0.2),0px 20px 31px 3px rgba(0, 0, 0, 0.14),0px 8px 38px 7px rgba(0, 0, 0, 0.12)"
        "0px 10px 13px -6px rgba(0, 0, 0, 0.2),0px 21px 33px 3px rgba(0, 0, 0, 0.14),0px 8px 40px 7px rgba(0, 0, 0, 0.12)"
        "0px 10px 14px -6px rgba(0, 0, 0, 0.2),0px 22px 35px 3px rgba(0, 0, 0, 0.14),0px 8px 42px 7px rgba(0, 0, 0, 0.12)"
        "0px 11px 14px -7px rgba(0, 0, 0, 0.2),0px 23px 36px 3px rgba(0, 0, 0, 0.14),0px 9px 44px 8px rgba(0, 0, 0, 0.12)"
        "0px 11px 15px -7px rgba(0, 0, 0, 0.2),0px 24px 38px 3px rgba(0, 0, 0, 0.14),0px 9px 46px 8px rgba(0, 0, 0, 0.12)"]
       elevation))

(s/def ::valid-text-shadow-elevation (set (range 4)))

(defn text-shadow
  "Takes an elevation and returns a CSS string to be used in text-shadow property
  Elevation can be one of `#{0 1 2 3}`"
  [elevation]
  {:pre [(s/valid? ::valid-text-shadow-elevation elevation)]}
  (case elevation
    0 "none"
    ;; offset-x | offset-y | blur-radius | color
    1 "1px 1px 2px rgba(0, 0, 0, 0.3)"
    2 "2px 2px 4px rgba(0, 0, 0, 0.3)"
    3 "4px 4px 8px rgba(0, 0, 0, 0.3)"))

(def
  ^{:doc "Map of breakpoints to be used in media queries, this is just an alias to
         the breakpoints defined in `tincture.core/grid`"}
  breakpoints
  grid/breakpoints)

(defn init!
  "Initialize tincture. Dispatches `:tincture/initialize` which takes a map of options:

  **Options**

  `:font-family` a vector of font-families to be used in
  `tincture.typography/Typography` component. Default: `[\"'Raleway'\" \"'Helvetica Neue'\" \"Arial\" \"Helvetica\" \"sans-serif\"]`

  `:font-url` a string representing a font url.
   The font url is injected as a &lt;link&gt; element in &lt;head&gt; on initialize. Default:
  \"https://fonts.googleapis.com/css?family=Raleway:300,400,500\"
   "
  ([] (rf/dispatch [:tincture/initialize]))
  ([options] (rf/dispatch-sync [:tincture/initialize options])))

(defn -main [& args]
  (.log js/console "started!"))
