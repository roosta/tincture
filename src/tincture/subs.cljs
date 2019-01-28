(ns tincture.subs
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [goog.labs.userAgent.device :as device]
            [reagent.debug :as d]))

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
 :<- [:utils/viewport-width]
 (fn [width [_ breakpoint]]
   (let [b (get tincture.core/breakpoints breakpoint)]
     (<= width b))))

(rf/reg-sub
 :tincture/breakpoint-up
 :<- [:utils/viewport-width]
 (fn [width [_ breakpoint]]
   (let [b (get tincture.core/breakpoints breakpoint)]
     (> width b))))
