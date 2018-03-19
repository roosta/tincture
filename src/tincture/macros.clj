(ns tincture.macros
  (:require
   [clojure.data.json :as json]
   [tincture.utils :as utils]
   [clojure.java.io :as io]))

(defn ^:private load-json [json-str]
  (json/read-str json-str :key-fn keyword))

(defn ^:private entry [{:keys [name colors]}]
  [(utils/name->kword name) colors])

(defmacro ui-gradients [json-uri]
  (let [g (->>
           (io/resource json-uri)
           (slurp)
           (load-json)
           (map entry)
           (into {}))]
    `~g))
