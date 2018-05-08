(ns demo.core
  (:require [reagent.core :as r]
            [demo.typography :as typo-demo]
            [devtools.core :as devtools]
            [re-frame.core :as rf]
            [tincture.core :as t]
            [tincture.container :refer [container]]
            [herb.core
             :refer-macros [<class]
             :refer [global-style!]]
            [tincture.typography :refer [typography]]
            [demo.slide :refer [slide-demo]]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

(def global-style
  [:body {:margin 0
          :font-family ["Helvetica Neue" "Verdana" "Helvetica" "Arial" "sans-serif"]}])

(devtools/install!)

(defn home-page []
  [container
   [typography {:variant :headline}
    "Welcome to the tincture demo"]
   [:ul
    [:li [:a {:href "/#typography"}
          [typography {:variant :body1}
           "Typography demo"]]]
    [:li [:a {:href "/#slide"}
          [typography {:variant :body1}
           "Slide demo"]]]]])

;; -------------------------
;; Routes

(def page (r/atom #'home-page))

(defn appframe []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/#typography" []
  (reset! page #'typo-demo/main))

(secretary/defroute "/#slide" []
  (reset! page #'slide-demo))

(defn mount-root []
  (r/render [appframe] (.getElementById js/document "app")))

(defn init! []
  (global-style! global-style)
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
