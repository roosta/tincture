(ns tincture.subs-test
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [re-frame.core :as rf]
            [goog.labs.userAgent.device :as device]
            [day8.re-frame.test :as rf-test]
            [tincture.subs :as e]
            ))


(deftest width-height
  (testing "layer-3 width height subscriptions"
    (rf-test/run-test-sync
     (let [width (rf/subscribe [:tincture/viewport-width])
           height (rf/subscribe [:tincture/viewport-height])]
       (rf/dispatch [:tincture/set-viewport-size [20 30]])
       (is (= @width 20))
       (is (= @height 30))))))

(deftest device
  (testing "Fetching device subscription"
    (let [device (rf/subscribe [:tincture/device])]
      (is (= @device :desktop))
      (with-redefs [device/isDesktop (fn [] false) 
                    device/isTablet (fn [] true)]
        (is (= @device :tablet)))
      (with-redefs [device/isDesktop (fn [] false)
                    device/isMobile (fn [] true)]
        (is (= @device :mobile))))))

(deftest breakpoints
  (testing "Breakpoint down and up subs"
    (rf-test/run-test-sync
     (let [xs-down (rf/subscribe [:tincture/breakpoint-down :xs])
           sm-down (rf/subscribe [:tincture/breakpoint-down :sm])
           md-down (rf/subscribe [:tincture/breakpoint-down :md])
           lg-down (rf/subscribe [:tincture/breakpoint-down :lg])
           xl-down (rf/subscribe [:tincture/breakpoint-down :xl])

           xs-up (rf/subscribe [:tincture/breakpoint-up :xs])
           sm-up (rf/subscribe [:tincture/breakpoint-up :sm])
           md-up (rf/subscribe [:tincture/breakpoint-up :md])
           lg-up (rf/subscribe [:tincture/breakpoint-up :lg])
           xl-up (rf/subscribe [:tincture/breakpoint-up :xl])]
       (rf/dispatch [:tincture/set-viewport-size [0 0]])
       (is @xs-down)
       (rf/dispatch [:tincture/set-viewport-size [1 0]])
       (is @xs-up)
       (is @sm-down)
       (rf/dispatch [:tincture/set-viewport-size [650 0]])
       (is @sm-up)
       (is @md-down)
       (rf/dispatch [:tincture/set-viewport-size [1000 0]])
       (is @md-up)
       (is @lg-down)
       (rf/dispatch [:tincture/set-viewport-size [1300 0]])
       (is @lg-up)
       (is @xl-down)
       (rf/dispatch [:tincture/set-viewport-size [2000 0]])
       (is @xl-up)
       (try @(rf/subscribe [:tincture/breakpoint-down :ad])
            (catch js/Error e
              (is (.-message e) "Invalid breakpoint")))
       (try @(rf/subscribe [:tincture/breakpoint-up :xc])
            (catch js/Error e
              (is (.-message e) "Invalid breakpoint")))))))
