(defproject tincture "0.3.0-SNAPSHOT"
  :description "Frontend development toolkit for ClojureScript"
  :url "https://github.com/roosta/tincture"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-codox "0.10.7"]]
  :codox {:language :clojurescript
          :metadata {:doc/format :markdown}
          :output-path "docs"
          :source-paths ["src"]}
  :profiles {:dev {:dependencies [[clj-chrome-devtools "20190502"]]}}
  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:optimizations :whitespace
                                   :output-to "target/cljs/test/test.js"
                                   :pretty-print true}}]}
  :dependencies [[org.clojure/clojure "1.10.0" :scope "provided"]
                 [org.clojure/clojurescript "1.10.520" :scope "provided"]
                 [cljsjs/react-transition-group "2.4.0-0"]
                 [herb "0.8.1"]
                 [prismatic/dommy "1.1.0"]
                 [re-frame "0.10.6"]]
  :source-paths ["src"])
