(ns tincture.paper-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tincture.test-utils :as utils]
            [dommy.core :as dommy :refer-macros [sel1 sel]]
            [herb.core :refer-macros [<class]]
            [reagent.core :as r]
            [tincture.paper :refer [Paper]]))

(def ^:dynamic c)

(deftest paper-component
  (testing "Mounting paper component and checking classname for validity"
    (binding [c (utils/new-container!)]
      (r/render [Paper {:id "test-paper" :square true :elevation 4}] c)
      (is (= (.-className (sel1 c :#test-paper))
             "tincture_paper_paper-style_4-true"))
      (r/render [Paper {:id "test-paper" :component :span}] c)
      (is (= (.-tagName (sel1 c :#test-paper))
             "SPAN"))
      (is (= (.-className (sel1 c :#test-paper))
             "tincture_paper_paper-style_2-false")))))
