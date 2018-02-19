(defproject tonic "0.1.2-SNAPSHOT"
  :description "Various frontend tools to ease development"
  :url "https://github.com/roosta/tonic"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [herb "0.3.3"]
                 [prismatic/dommy "1.1.0"]
                 [re-frame "0.10.5"]
                 ; [rm-hull/inkspot "0.2.1"]
                 ]

  :source-paths ["src"]

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/cljs/test/test.js"
                                   :output-dir "target/cljs/test"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true
                                   :main tonic.runner}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:output-to "tonic.js"
                                   :optimizations :advanced}}]})
