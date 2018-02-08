(defproject tonic "0.1.0-SNAPSHOT"
  :description "Various frontend tools to ease development"
  :url "https://github.com/roosta/tonic"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [herb "0.2.0-SNAPSHOT"]
                 [garden "1.3.3"]
                 [re-frame "0.10.4"]
                 [facjure/mesh "0.4.0"]
                 [rm-hull/inkspot "0.2.1"]]

  :source-paths ["src"]

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/cljs/test/test.js"
                                   :output-dir "target/cljs/test"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true
                                   :main flora-ui.runner}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:output-to "flora-ui.js"
                                   :optimizations :advanced}}]})
