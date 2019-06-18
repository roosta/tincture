(ns tincture.grid-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [garden.core :refer [css]]
            [tincture.grid :as g]))

(deftest up
  (testing "Up breakpoint function"
    (is (= (css {:pretty-print? false} [:.test (g/up :xs)])
           ".test{min-width:0px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :sm)])
           ".test{min-width:600px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :md)])
           ".test{min-width:960px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :md)])
           ".test{min-width:960px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :lg)])
           ".test{min-width:1280px}"))
    (is (= (css {:pretty-print? false} [:.test (g/up :xl)])
           ".test{min-width:1920px}"))))

(deftest down
  (testing "Down breakpoint function"
    (is (= (css {:pretty-print? false} [:.test (g/down :xs)])
           ".test{max-width:599.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :sm)])
           ".test{max-width:959.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :md)])
           ".test{max-width:1279.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :lg)])
           ".test{max-width:1919.95px}"))
    (is (= (css {:pretty-print? false} [:.test (g/down :xl)])
           ".test{min-width:0px}"))
    (is (= (g/down :xl) (g/up :xs)))))
