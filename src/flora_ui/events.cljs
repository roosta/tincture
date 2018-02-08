(ns flora-ui.events
  (:import [goog.dom ViewportSizeMonitor])
  (:require [reagent.core :as r]
            [goog.events.EventType :as event-type]
            [goog.events :as gevents]
            [flora-ui.db :as db]
            [reagent.debug :as d]
            [re-frame.core :refer [reg-event-db reg-event-fx reg-fx inject-cofx path trim-v
                                   after debug dispatch]]
            [clojure.spec.alpha :as s]) )

(defonce vsm (ViewportSizeMonitor.))

(defonce vsm-listener
  (gevents/listen vsm
                  event-type/RESIZE
                  (fn [e]
                    (let [size (.getSize vsm)]
                      (dispatch [:flora-ui/set-viewport-size [(.-height size) (.-width size)]])))))

(reg-event-db
 :flora-ui/initialize
 (fn [db _]
   (let [size (.getSize vsm)]
     (-> db
         (assoc :flora-ui/viewport-size [(.-width size)
                                         (.-height size)])
         (assoc :flora-ui/theme db/default-theme)))))

(reg-event-db
 :flora-ui/set-viewport-size
 (fn [db [new]]
   (assoc db :flora-ui/viewport-size new)))
