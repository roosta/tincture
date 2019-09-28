(defproject tincture "0.3.3-SNAPSHOT"
  :description "Frontend development toolkit for ClojureScript"
  :url "https://github.com/roosta/tincture"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-codox "0.10.7"]]
  :codox {:language :clojurescript
          :metadata {:doc/format :markdown}
          :output-path "docs"
          :source-paths ["src"]}
  :resource-paths ["target" "resources"]
  :jar-exclusions [#"(?:^|\/)public\/"]
  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.2.1"]
                                  [day8.re-frame/test "0.1.5"]]
                   :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
                             "fig:test" ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" tincture.test-runner]
                             "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
                             "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]}}}
  :dependencies [[org.clojure/clojure "1.10.1" :scope "provided"]
                 [org.clojure/clojurescript "1.10.520" :scope "provided"]
                 [cljsjs/react-transition-group "2.4.0-0" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [reagent "0.8.1"]
                 [rm-hull/inkspot "0.2.1"]
                 [herb "0.10.0"]
                 [prismatic/dommy "1.1.0"]
                 [re-frame "0.10.7"]]
  :source-paths ["src"]
  :clean-targets ^{:protect false} ["target"]
  )
