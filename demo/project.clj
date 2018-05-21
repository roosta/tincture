(defproject demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.4"]
                 [org.clojure/clojurescript "1.10.238"]
                 [herb "0.5.0-SNAPSHOT"]

                 [prismatic/dommy "1.1.0"]

                 [cljsjs/react-transition-group "2.3.1-0"]
                 [figwheel-sidecar "0.5.16"]
                 [re-frame "0.10.5"]
                 [reagent "0.8.1"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.16"]]

  :min-lein-version "2.5.0"

  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :resource-paths ["public" "../resources"]

  :source-paths ["src" "../src"]

  :figwheel {:http-server-root "."
             :nrepl-port 7899
             :server-port 3460
             :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
             :css-dirs ["public/css"]}

  :cljsbuild {:builds {:app
                       {:source-paths ["src" "../src"]
                        :compiler
                        {:main "demo.core"
                         :output-to "public/js/app.js"
                         :output-dir "public/js/out"
                         :asset-path   "js/out"
                         :preloads [re-frisk.preload]
                         :source-map true
                         :optimizations :none
                         :pretty-print  true}
                        :figwheel
                        {:on-jsload "demo.core/mount-root" }}}}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.16"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [re-frisk "0.5.4"]
                                  [cider/piggieback "0.3.5"]]
                   :source-paths ["script"]}})
