(defproject tincture "0.1.7-SNAPSHOT"
  :description "Frontend development toolkit"
  :url "https://github.com/roosta/tincture"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0" :scope "provided"]
                 [org.clojure/clojurescript "1.10.339" :scope "provided"]
                 [cljsjs/react-transition-group "2.4.0-0"]
                 [herb "0.5.1-SNAPSHOT"]
                 [prismatic/dommy "1.1.0"]
                 [re-frame "0.10.5"]]

  :cljsbuild {:builds [{:id "prod"
                        :source-paths ["src"]
                        :compiler {:output-to "tincture.js"
                                   :optimizations :advanced}}]}
  :source-paths ["src"])
