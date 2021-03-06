(defproject site "0.1.0-SNAPSHOT"
  :description "Demo site for tincture library"
  :url "http://tincture.roosta.sh"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring-server "0.5.0"]
                 [reagent "0.8.1"]
                 [tincture "0.3.3-1"]
                 [reagent-utils "0.3.3"]
                 [ring "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [re-frame "0.10.9"]
                 [hiccup "1.0.5"]
                 [cljsjs/hammer "2.0.8-0"]
                 [yogthos/config "1.1.6"]
                 [org.clojure/clojurescript "1.10.520"
                  :scope "provided"]
                 [metosin/reitit "0.3.10"]
                 [pez/clerk "1.0.0"]
                 [venantius/accountant "0.2.4"
                  :exclusions [org.clojure/tools.reader]]]

  :plugins [[lein-environ "1.1.0"]
            [lein-cljsbuild "1.1.7"]]

  :ring {:handler site.handler/app
         :uberwar-name "site.war"}

  :min-lein-version "2.5.0"
  :uberjar-name "site.jar"
  :main site.server
  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :source-paths ["src"]
  :resource-paths ["resources" "target/cljsbuild"]

  :cljsbuild
  {:builds {:min {:source-paths ["src" "env/prod"]
                  :compiler {:output-to        "target/cljsbuild/public/js/app.js"
                             :output-dir       "target/cljsbuild/public/js"
                             :source-map       "target/cljsbuild/public/js/app.js.map"
                             :closure-defines {"goog.DEBUG" false}
                             :optimizations :advanced
                             :pretty-print  false}}
            :app {:source-paths ["src" "env/dev"]
                  :figwheel {:on-jsload "site.core/mount-root"}
                  :compiler {:main "site.dev"
                             :asset-path "/js/out"
                             :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true}
                             :preloads             [day8.re-frame-10x.preload]
                             :output-to "target/cljsbuild/public/js/app.js"
                             :output-dir "target/cljsbuild/public/js/out"
                             :source-map true
                             :optimizations :none
                             :pretty-print  true}}}}

  :figwheel {:http-server-root "public"
             :server-port 3449
             :nrepl-port 7002
             :nrepl-middleware [cider.piggieback/wrap-cljs-repl]
             :css-dirs ["resources/public/css"]
             :ring-handler site.handler/app}

  :profiles {:dev {:repl-options {:init-ns site.repl}
                   :dependencies [[cider/piggieback "0.4.2"]
                                  [binaryage/devtools "0.9.10"]
                                  [orchestra "2019.02.06-1"]
                                  [ring/ring-mock "0.4.0"]
                                  [ring/ring-devel "1.7.1"]
                                  [philoskim/debux "0.5.6"]
                                  [prone "2019-07-08"]
                                  [figwheel-sidecar "0.5.19"]
                                  [day8.re-frame/re-frame-10x "0.4.4"]
                                  [nrepl "0.6.0"]
                                  [pjstadig/humane-test-output "0.10.0"]]

                   :source-paths ["env/dev"]
                   :plugins [[lein-figwheel "0.5.19"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :env {:dev true}}

             :uberjar {:source-paths ["env/prod"]
                       :dependencies [[philoskim/debux-stubs "0.5.6"]]
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
                       :env {:production true}
                       :aot :all
                       :omit-source true}})
