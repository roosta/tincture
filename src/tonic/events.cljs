(ns tonic.events
  (:import [goog.dom ViewportSizeMonitor])
  (:require [reagent.core :as r]
            [goog.events.EventType :as event-type]
            [goog.events :as gevents]
            [tonic.db :as db]
            [reagent.debug :as d]
            [re-frame.core :refer [reg-event-db reg-event-fx reg-fx inject-cofx path trim-v
                                   after debug dispatch]]
            [clojure.spec.alpha :as s]) )

(defonce vsm (ViewportSizeMonitor.))

(defonce vsm-listener
  (gevents/listen
   vsm
   event-type/RESIZE
   (fn [e]
     (let [size (.getSize vsm)]
       (dispatch [:tonic/set-viewport-size [(.-width size) (.-height size)]])))))

(reg-event-db
 :tonic/initialize
 (fn [db]
   (let [size (.getSize vsm)]
     (-> db
         (merge db/default-db)
         (assoc :tonic/viewport-size [(.-width size) (.-height size)])))))

(reg-event-db
 :tonic/set-viewport-size
 (fn [db [_ new]]
   (assoc db :tonic/viewport-size new)))


(reg-event-db
 :tonic/set-font-families
 (fn [db [_ fonts]]
   (assoc db :tonic/font-families fonts)))


;; Lato,'Helvetica Neue',Arial,Helvetica,sans-serif
