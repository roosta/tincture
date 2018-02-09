(ns demo.core
  (:require [reagent.core :as r]
            [demo.typography :as typo-demo]
            [devtools.core :as devtools]
            [re-frame.core :as rf]
            [tonic.core :as t]
            [tonic.typography :refer [typography]]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

(devtools/install!)

(defn home-page []
  [:div
   [typography {:kind :headline}
    "Welcome to the Tonic demo"]
   [:ul
    [:li [:a {:href "/#typography"}
          [typography {:kind :body1}
           "Typography demo"]]]]])

;; -------------------------
;; Routes

(def page (r/atom #'home-page))

(defn appframe []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/#typography" []
  (reset! page #'typo-demo/main))

(defn mount-root []
  (r/render [appframe] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (secretary/dispatch! path))
    :path-exists?
    (fn [path]
      (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root)
  (t/init!))

(init!)
