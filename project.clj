(defproject tincture "0.2.0"
  :description "Frontend development toolkit"
  :url "https://github.com/roosta/tincture"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-codox "0.10.6"]]
  :codox {:language :clojurescript
          :metadata {:doc/format :markdown}
          :output-path "doc"
          :source-paths ["src"]}
  :dependencies [[org.clojure/clojure "1.10.0" :scope "provided"]
                 [org.clojure/clojurescript "1.10.439" :scope "provided"]
                 [cljsjs/react-transition-group "2.4.0-0"]
                 [herb "0.7.3-SNAPSHOT"]
                 ;; [prismatic/dommy "1.1.0"]
                 [re-frame "0.10.6"]]
  :source-paths ["src"])
