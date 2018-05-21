(defproject tincture "0.1.5-SNAPSHOT"
  :description "Clojurescript frontend toolkit"
  :url "https://github.com/roosta/tincture"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [cljsjs/react-transition-group "2.3.1-0"]
                 [herb "0.5.0-SNAPSHOT"]
                 [prismatic/dommy "1.1.0"]
                 [re-frame "0.10.5"]]

  :source-paths ["src"]

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/cljs/test/test.js"
                                   :output-dir "target/cljs/test"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true
                                   :main tincture.runner}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:output-to "tincture.js"
                                   :optimizations :advanced}}]})
