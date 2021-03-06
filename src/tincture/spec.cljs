(ns tincture.spec
  (:require [clojure.spec.alpha :as s]))

(defn check-spec
  "Takes a value and a key for a spec, throw an exception if value doesn't match
  the spec, otherwise return passed value"
  ([value spec]
   (check-spec value spec "Invalid value"))
  ([value spec message]
   (if-not (s/valid? spec value)
     (throw (ex-info message (s/explain-data spec value)))
     value)))

(s/def ::email
  (letfn [(pred [s]
            (re-matches #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$" s))]
    (s/spec pred)))
