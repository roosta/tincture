(ns demo.core
  (:require [reagent.core :as r]
            [demo.paper :as paper-demo]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to Flora-ui demo"]
   [:div [:a {:href "/#paper"} "Paper demo"]]])

;; -------------------------
;; Routes

(def page (r/atom #'home-page))

(defn appframe []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/#paper" []
  (reset! page #'paper-demo/main))

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
   (mount-root))

(init!)
