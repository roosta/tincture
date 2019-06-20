(ns tincture.slide-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tincture.slide :as s]))

(deftest get-style
  (testing "getting styles for transition animations"
    (let [f #'tincture.slide/get-style]
      (is (= (f "entering" :left :ease-in-out-cubic 500)
             {:transform "translate(100%, 0)",
              :opacity 0.01,
              :left 0,
              :top 0,
              :position "absolute",
              :transition "transform 500ms cubic-bezier(.645, .045, .355, 1) 0ms, opacity 500ms cubic-bezier(.645, .045, .355, 1) 0ms"}))
      (is (= (f "entered" :right :ease-in-out-quad 500)
             {:transform "translate(0, 0)",
              :opacity 1,
              :left 0,
              :top 0,
              :position "absolute",
              :transition "transform 500ms cubic-bezier(.455, .03, .515, .955) 0ms, opacity 500ms cubic-bezier(.455, .03, .515, .955) 0ms"}))
      (is (= (f "exiting" :up :ease-in-expo 1000)
             {:transform "translate(0, -100%)",
              :opacity 0.01,
              :left 0,
              :top 0,
              :position "absolute",
              :transition "transform 1000ms cubic-bezier(.95, .05, .795, .035) 0ms, opacity 1000ms cubic-bezier(.95, .05, .795, .035) 0ms"}
             ))
      (is (= (f "exited" :down :ease-out-circ 120)
             {:opacity 0,
              :left 0,
              :top 0,
              :position "absolute",
              :transition "transform 120ms cubic-bezier(.075, .82, .165, 1) 0ms, opacity 120ms cubic-bezier(.075, .82, .165, 1) 0ms"} 
             ))
      )))

