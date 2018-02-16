(ns tonic.subs
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [goog.labs.userAgent.device :as device]
            [reagent.debug :as d]))

;; get viewport size
(rf/reg-sub
 :tonic/viewport-size
 (fn [db _]
   (:tonic/viewport-size db)))

(rf/reg-sub
 :tonic/font-families
 (fn [db _]
   (:tonic/font-families db)))

(rf/reg-sub
 :tonic/device
 (fn []
   (cond
     (device/isDesktop) :desktop
     (device/isTablet)  :tablet
     (device/isMobile)  :mobile)))

;; get viewport size
(rf/reg-sub
 :tonic/viewport-width
 :<- [:tonic/viewport-size]
 (fn [[width _]]
   width))

;; get viewport size
(rf/reg-sub
 :tonic/viewport-height
 :<- [:tonic/viewport-size]
 (fn [[_ height]]
   height))
