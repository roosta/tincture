(ns demo.core
  (:require [reagent.core :as r]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to flora demo"]
   [:div [:a {:href "/#about"} "go to about page"]]])

(defn about-page []
  [:div [:h2 "About flora demo"]
   [:div [:a {:href "/"} "go to the home page"]]])

;; -------------------------
;; Routes

(def page (r/atom #'home-page))

(defn appframe []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/#about" []
  (reset! page #'about-page))

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
