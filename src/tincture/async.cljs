(ns tincture.async
  "Adaptions of google closure library async Throttle and Debounce.

  Source: https://medium.com/@alehatsman/clojurescript-throttle-debounce-a651dfb66ac"

  (:import [goog.async Throttle Debouncer]))

(defn- disposable->function [disposable listener interval]
  (let [disposable-instance (disposable. listener interval)]
    (fn [& args]
      (.apply (.-fire disposable-instance) disposable-instance (to-array args)))))

(defn throttle
  "Takes a listener function and interval and creates an instance of
  `goog.async.Throttle`.
  Example usage:
  ```clojure
  (require '[goog.events :as gevents])
  (gevents/listen
   js/document
   \"scroll\"
   (throttle on-scroll 200))
  ```
  "
  [listener interval]
  (disposable->function Throttle listener interval))

(defn debounce
  "Takes a listener function and interval and creates an instance of
  `goog.async.Debounce`
  Example usage:
  ```clojure
  (require '[goog.events :as gevents])
  (gevents/listen
   js/window
   \"resize\"
   (debounce on-resize 200))
  ```
  "

  [listener interval]
  (disposable->function Debouncer listener interval))
