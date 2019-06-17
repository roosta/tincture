(ns tincture.events-test
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [re-frame.core :as rf]
            [dommy.core :as dommy :refer-macros [sel1 sel]]
            [day8.re-frame.test :as rf-test]
            [tincture.events :as e]
            ))

(rf/reg-sub
 :test/db
 (fn [db] db))

(deftest initialize-event
  (testing "Test initialize event"
    (rf-test/run-test-sync
     (let [db (rf/subscribe [:test/db])]
       (rf/dispatch [:tincture/initialize])
       (is (= (:tincture/font @db)
               {:font/family
                ["'Raleway'" "'Helvetica Neue'" "Arial" "Helvetica" "sans-serif"],
                :font/url
                "https://fonts.googleapis.com/css?family=Raleway:300,400,500"}))
       (is (= (.getAttribute (sel1 js/document :#tincture-font) "href")
              "https://fonts.googleapis.com/css?family=Raleway:300,400,500"))))))

(deftest set-viewport-size-event
  (testing "Setting viewport size"
    (rf-test/run-test-sync
     (let [viewport (rf/subscribe [:tincture/viewport-size])
           width (rf/subscribe [:tincture/viewport-width])
           height (rf/subscribe [:tincture/viewport-height])]
       (rf/dispatch [:tincture/set-viewport-size [1 2]])
       (is (=  @viewport [1 2]))
       (is (=  @width 1))
       (is (=  @height 2))))))

(deftest set-font-event
  (testing "Setting viewport size"
    (rf-test/run-test-sync
     (let [family (rf/subscribe [:tincture.font/family])
           url (rf/subscribe [:tincture.font/url])]
       (rf/dispatch [:tincture/set-font "test" "anothertest"])
       (is (=  @family "test"))
       (is (=  @url "anothertest"))))))

