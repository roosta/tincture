(ns tincture.cssfns-test
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [garden.core :refer [css]]
            [garden.units :refer [percent px]]
            [tincture.cssfns :as c]))

(deftest linear-gradient
  (testing "linear gradient CSS function"
    (is (= (css {:pretty-print? false} [:.test {:background-image (c/linear-gradient "to left" "black" "0%" "red" "100%")}])
           ".test{background-image:linear-gradient(to left,black 0%,red 100%)}"))
    (is (= (css {:pretty-print? false} [:.test {:background-image (c/linear-gradient "to bottom" (c/rgb 0 0 0 0) "0%" (c/rgb 38 38 38 1) "100%")}])
           ".test{background-image:linear-gradient(to bottom,rgb(0,0,0,0) 0%,rgb(38,38,38,1) 100%)}"))))

(deftest rgb
  (testing "rgb CSS function"
    (is (= (css {:pretty-print? false} [:.test {:color (c/rgb 0 0 0)}])
           ".test{color:rgb(0,0,0)}"))
    (is (= (css {:pretty-print? false} [:.test {:color (c/rgb 255 255 255 0.2)}])
           ".test{color:rgb(255,255,255,0.2)}"))))

(deftest url
  (testing "url CSS function"
    (is (= (css {:pretty-print? false} [:.test {:background (c/url "http://www.example.com/image.png")}])
           ".test{background:url(http://www.example.com/image.png)}"))))

(deftest calc
  (testing "Calc CSS function"
    (is (= (css {:pretty-print? false} [:.test {:height (c/calc (percent 100) '- (px 20))}])
           ".test{height:calc(100% - 20px)}"))))
