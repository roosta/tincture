(ns tincture.spec-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tincture.spec :as sp]
            [clojure.spec.alpha :as s]))

(s/def ::test-boolean boolean?)
(s/def ::test-pos-int pos-int?)

(deftest check-spec
  (testing "Checking a spec, and check return value"
    (is (sp/check-spec true ::test-boolean))
    (is (= (sp/check-spec 1 ::test-pos-int) 1))
    (try (sp/check-spec :test ::test-boolean)
         (catch js/Error e
           (is (= (.-message e)
                  "Invalid value"))))
    (try (sp/check-spec -1 ::test-pos-int "test message")
         (catch js/Error e
           (is (= (.-message e)
                  "test message"))))
    
    ))
