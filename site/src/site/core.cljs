(ns site.core
  (:require [reagent.core :as reagent :refer [atom]]
            [site.slide :refer [slide-demo]]
            [site.gradient :refer [gradient-demo]]
            [reagent.session :as session]
            [garden.units :refer [px]]
            [herb.core :refer-macros [<class defgroup]]
            [tincture.grid :refer [Grid]]
            [site.typography :as typo-demo]
            [site.paper :as paper-demo]
            [site.grid :as grid-demo]
            [reitit.frontend :as reitit]
            [tincture.container :refer [Container]]
            [tincture.typography :refer [Typography]]
            [tincture.core :as t]
            [herb.core :refer-macros [defglobal]]
            [clerk.core :as clerk]
            [accountant.core :as accountant]))

(defglobal global-style
  [:body {:margin 0}])

;; -------------------------
;; Routes

(def router
  (reitit/router
   [["/" :index]
    ["/typography" :typography]
    ["/slide" :slide]
    ["/gradient" :gradient]
    ["/paper" :paper]
    ["/grid" :grid]]))

(defn path-for [route & [params]]
  (if params
    (:path (reitit/match-by-name router route params))
    (:path (reitit/match-by-name router route))))

;; -------------------------
;; Page components

(defgroup styles
  {:container {:height "100vh"}
   :row {:padding (px 32)}
   :ul {:display :inline-block}})

(defn home-page []
  [Container
   [Grid {:container true
          :class (<class styles :container)
          :justify :center
          :align-content :center}
    [Grid {:item true
           :class (<class styles :row)
           :md 6
           :xs 12}
     [Typography {:variant :h2}
      "Welcome to the Tincture demo"]
     [Typography {:variant :body1}
      "Tincture is a frontend toolkit for ClojureScript that provides several
    utility functions, and definitions to aid/speed up developing web page visuals."]
      [:ul {:class (<class styles :ul)}
       [:li [:a {:href "/typography"}
             [Typography {:variant :body1}
              "Typography demo"]]]
       [:li [:a {:href "/slide"}
             [Typography {:variant :body1}
              "Slide demo"]]]
       [:li [:a {:href "/gradient"}
             [Typography {:variant :body1}
              "Gradient demo"]]]
       [:li [:a {:href "/paper"}
             [Typography {:variant :body1}
              "Paper demo"]]]
       [:li [:a {:href "/grid"}
             [Typography {:variant :body1}
              "Grid demo"]]]]]]])

;; -------------------------
;; Translate routes -> page components

(defn page-for [route]
  (case route
    :index #'home-page
    :typography #'typo-demo/main
    :slide #'slide-demo
    :paper #'paper-demo/main
    :gradient #'gradient-demo
    :grid #'grid-demo/main))

;; -------------------------
;; Page mounting component

(defn current-page []
  (let [page (:current-page (session/get :route))]
    [:div [page]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (clerk/initialize!)
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (let [match (reitit/match-by-path router path)
            current-page (:name (:data  match))
            route-params (:path-params match)]
        (reagent/after-render clerk/after-render!)
        (session/put! :route {:current-page (page-for current-page)
                              :route-params route-params})
        (clerk/navigate-page! path)))
    :path-exists?
    (fn [path]
      (boolean (reitit/match-by-path router path)))})
  (accountant/dispatch-current!)
  (mount-root)
  (t/init!))
