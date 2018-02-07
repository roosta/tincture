(defproject flora-ui "0.1.0-SNAPSHOT"
  :description "Opinionated UI framework built with garden"
  :url "https://github.com/roosta/flora-ui"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.3"]
                 [cljs-css-modules "0.2.1" :exclusions [garden]]
                 [garden "1.3.3"]
                 ;; [cljsjs/hammer "2.0.8-0"]
                 [facjure/mesh "0.4.0"]
                 [rm-hull/inkspot "0.2.1"]
                 [cljsjs/react-transition-group "2.2.0-0"]
                 [org.clojure/clojurescript "1.9.908" :scope "provided"]
                 [reagent "0.7.0"  :exclusions [cljsjs/react cljsjs/react-dom]]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.14"]]

  :min-lein-version "2.5.0"

  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :figwheel {:server-port 3333
             :nrepl-port 7891
             :css-dirs ["resources/public/css"]}

  :source-paths ["src/clj"]

  :cljsbuild {:builds [{:id           "demo"
                        :source-paths ["src"]
                        :compiler     {:main demo.core
                                       :preloads [devtools.preload re-frisk.preload]
                                       :asset-path "js/out"
                                       :output-to "resources/public/js/demo.js"
                                       :output-dir "resources/public/js/out"
                                       :optimizations :none
                                       :source-map true
                                       :source-map-timestamp true}
                        :figwheel {:on-jsload "demo.core/mount-root"}}]}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.7"]
                                  [re-frisk "0.5.3"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [figwheel-sidecar "0.5.14"]]
                   :source-paths ["script"]}})
