(ns tincture.events
  (:import [goog.dom ViewportSizeMonitor])
  (:require [reagent.core :as r]
            [goog.events.EventType :as event-type]
            [goog.events :as gevents]
            [tincture.db :as db]
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
       (dispatch [:tincture/set-viewport-size [(.-width size) (.-height size)]])))))

(reg-event-db
 :tincture/initialize
 (fn [db]
   (let [size (.getSize vsm)]
     (-> db
         (merge db/default-db)
         (assoc :tincture/viewport-size [(.-width size) (.-height size)])))))

(reg-event-db
 :tincture/set-viewport-size
 (fn [db [_ new]]
   (assoc db :tincture/viewport-size new)))


(reg-event-db
 :tincture/set-font-families
 (fn [db [_ fonts]]
   (assoc db :tincture/font-families fonts)))


;; Lato,'Helvetica Neue',Arial,Helvetica,sans-serif
