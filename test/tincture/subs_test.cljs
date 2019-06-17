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
