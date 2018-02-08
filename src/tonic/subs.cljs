(ns flora-ui.subs
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [goog.labs.userAgent.device :as device]
            [reagent.debug :as d]))

;; get viewport size
(rf/reg-sub
 :flora-ui/viewport-size
 (fn [db _]
   (:flora-ui/viewport-size db)))

(rf/reg-sub
 :flora-ui/device
 (fn []
   (cond
     (device/isDesktop) :desktop
     (device/isTablet)  :tablet
     (device/isMobile)  :mobile)))

;; get viewport size
(rf/reg-sub
 :flora-ui/viewport-width
 :<- [:flora-ui/viewport-size]
 (fn [[width _]]
   width))

;; get viewport size
(rf/reg-sub
 :flora-ui/viewport-height
 :<- [:flora-ui/viewport-size]
 (fn [[_ height]]
   height))


;; get viewport size
(rf/reg-sub
 :flora-ui/theme
 (fn [db]
   (:flora-ui/theme db)))
