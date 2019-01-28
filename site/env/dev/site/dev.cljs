(ns ^:figwheel-no-load site.dev
  (:require
    [site.core :as core]
    [orchestra-cljs.spec.test :as st]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)

;; (st/instrument)
