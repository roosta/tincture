(ns demo.core
  (:require [reagent.core :as r]
            [demo.typography :as typo-demo]
            [devtools.core :as devtools]
            [re-frame.core :as rf]
            [tincture.core :as t]
            [orchestra-cljs.spec.test :as st]
            [tincture.container :refer [container]]
            [herb.core
             :refer-macros [<class]
             :refer [global-style!]]
            [tincture.typography :refer [typography]]
            [demo.gradient :refer [gradient-demo]]
            [demo.slide :refer [slide-demo]]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

(def global-style
  [:body {:margin 0
          :font-family ["Helvetica Neue" "Verdana" "Helvetica" "Arial" "sans-serif"]}])

(devtools/install!)

(defn home-page []
  [container
   [typography {:variant :display1}
    "Welcome to the Tincture demo"]
   [typography {:variant :body1}
    "Tincture is a frontend toolkit for ClojureScript that provides several
    utility functions, and definitions to aid/speed up developing web page visuals."]
   [:ul
    [:li [:a {:href "/#typography"}
          [typography {:variant :body1}
           "Typography demo"]]]
    [:li [:a {:href "/#slide"}
          [typography {:variant :body1}
           "Slide demo"]]]
    [:li [:a {:href "/#gradient"}
          [typography {:variant :body1}
           "Gradient demo"]]]]])

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

(secretary/defroute "/#gradient" []
  (reset! page #'gradient-demo))

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

(st/instrument)
