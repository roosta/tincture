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
                  "test message"))))))

(deftest email-spec
  (testing "Passing emails through the :tincture.spec/email spec"
    (is (s/valid? :tincture.spec/email "test@example.com"))
    (is (s/valid? :tincture.spec/email "test.user@example.com"))
    (is (not (s/valid? :tincture.spec/email "test")))
    (is (not (s/valid? :tincture.spec/email "test@example")))
    (is (not (s/valid? :tincture.spec/email "example.com")))
    (is (not (s/valid? :tincture.spec/email "test.example.com")))
    )
  )
