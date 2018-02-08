(defproject demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.3"]
                 [org.clojure/clojurescript "1.9.946"]
                 [herb "0.2.0-SNAPSHOT"]
                 [rm-hull/inkspot "0.2.1"]
                 [re-frame "0.10.4"]
                 [reagent "0.8.0-alpha2"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.14"]]

  :min-lein-version "2.5.0"

  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :resource-paths ["public"]

  :source-paths ["src" "../src"]

  :figwheel {:http-server-root "."
             :nrepl-port 7899
             :server-port 3450
             :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
             :css-dirs ["public/css"]}

  :cljsbuild {:builds {:app
                       {:source-paths ["src" "../src"]
                        :compiler
                        {:main "demo.core"
                         :output-to "public/js/app.js"
                         :output-dir "public/js/out"
                         :asset-path   "js/out"
                         :closure-defines {"re_frame.trace.trace_enabled_QMARK_" true}
                         :preloads [day8.re-frame.trace.preload]
                         :source-map true
                         :optimizations :none
                         :pretty-print  true}
                        :figwheel
                        {:on-jsload "demo.core/mount-root" }}}}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.9"]
                                  [figwheel-sidecar "0.5.14"]
                                  [day8.re-frame/trace "0.1.18-react16"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.2"]]
                   :source-paths ["script"]}})
