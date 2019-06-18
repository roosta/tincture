(ns tincture.gradient-test
  (:require [cljs.test :refer-macros [deftest testing is async]]
            [tincture.gradient :as g]))

(deftest css
  (testing "Output from gradient CSS function"
    (is (= (g/css :windy)
           #{"#acb6e5" "linear-gradient(to left, #acb6e5, #86fde8)" "-webkit-linear-gradient(to left, #acb6e5, #86fde8)"}))
    (is (= (g/css :dirty-fog :right)
           #{"#B993D6" "linear-gradient(to right, #B993D6, #8CA6DB)" "-webkit-linear-gradient(to right, #B993D6, #8CA6DB)"}))
    (is (= (g/css :fresh-turboscent :up)
           #{"#F1F2B5" "linear-gradient(to up, #F1F2B5, #135058)" "-webkit-linear-gradient(to up, #F1F2B5, #135058)"}))
    (is (= (g/css :solid-vault :down)
           #{"#3a7bd5" "linear-gradient(to down, #3a7bd5, #3a6073)" "-webkit-linear-gradient(to down, #3a7bd5, #3a6073)"}))
    (is (= (g/css :ed-s-sunset-gradient)
           #{"#ff7e5f" "linear-gradient(to left, #ff7e5f, #feb47b)" "-webkit-linear-gradient(to left, #ff7e5f, #feb47b)"}))
    (is (= (g/css :pure-lust)
           #{"#333333" "linear-gradient(to left, #333333, #dd1818)" "-webkit-linear-gradient(to left, #333333, #dd1818)"}))
    (try (g/css :test)
         (catch js/Error e
           (is (= (.-message e) "Invalid value"))))
    (try (g/css :vice-city :test)
         (catch js/Error e
           (is (= (.-message e) "Invalid value"))))))
