(ns demo.core
  (:require [reagent.core :as r]
            [demo.paper :as paper-demo]
            [demo.typography :as typo-demo]
            [devtools.core :as devtools]
            [demo.appbar :as appbar-demo]
            [re-frame.core :as rf]
            [flora-ui.events]
            [flora-ui.subs]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

(devtools/install!)

(defn home-page []
  [:div [:h2 "Welcome to Flora-ui demo"]
   [:ul
    [:li [:a {:href "/#paper"} "Paper demo"]]
    [:li [:a {:href "/#typography"} "Typography demo"]]
    [:li [:a {:href "/#appbar"} "Appbar demo"]]
    ]
   ])


;; -------------------------
;; Routes

(def page (r/atom #'home-page))

(defn appframe []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/#paper" []
  (reset! page #'paper-demo/main))

(secretary/defroute "/#typography" []
  (reset! page #'typo-demo/main))

(secretary/defroute "/#appbar" []
  (reset! page #'appbar-demo/main))

(defn mount-root []
  (r/render [appframe] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:flora-ui/initialize])
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
