(ns tincture.subs
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [clojure.spec.alpha :as s]
            [goog.labs.userAgent.device :as device]
            [reagent.debug :as d]))

(s/def ::valid-breakpoints #{:xs :sm :md :lg :xl})

;; get viewport size
(rf/reg-sub
 :tincture/viewport-size
 (fn [db _]
   (:tincture/viewport-size db)))

(rf/reg-sub
 :tincture/font-families
 (fn [db _]
   (:tincture/font-families db)))

(rf/reg-sub
 :tincture/device
 (fn []
   (cond
     (device/isDesktop) :desktop
     (device/isTablet)  :tablet
     (device/isMobile)  :mobile)))

;; get viewport size
(rf/reg-sub
 :tincture/viewport-width
 :<- [:tincture/viewport-size]
 (fn [[width _]]
   width))

;; get viewport size
(rf/reg-sub
 :tincture/viewport-height
 :<- [:tincture/viewport-size]
 (fn [[_ height]]
   height))

(rf/reg-sub
 :tincture/breakpoint-down
 :<- [:tincture/viewport-width]
 (fn [width [_ breakpoint]]
   (let [parsed (s/conform ::valid-breakpoints breakpoint)]
     (if (= parsed ::s/invalid)
       (throw (ex-info "Invalid breakpoint" (s/explain-data ::valid-breakpoints breakpoint)))
       (let [b (get tincture.core/breakpoints breakpoint)]
         (<= width b))))))

(rf/reg-sub
 :tincture/breakpoint-up
 :<- [:tincture/viewport-width]
 (fn [width [_ breakpoint]]
   (let [parsed (s/conform ::valid-breakpoints breakpoint)]
     (if (= parsed ::s/invalid)
       (throw (ex-info "Invalid breakpoint" (s/explain-data ::valid-breakpoints breakpoint)))
       (let [b (get tincture.core/breakpoints breakpoint)]
         (> width b))))))
