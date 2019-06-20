(ns tincture.slide-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tincture.test-utils :as utils]
            [dommy.core :as dommy :refer-macros [sel1 sel]]
            [garden.core :refer [css]]
            [reagent.core :as r]
            [garden.units :refer [px percent]]
            [tincture.slide :as s]))

(deftest get-style
  (testing "getting styles for transition animations"
    (let [f #'tincture.slide/get-style]
      ;; (.log js/console (f))


      )))

