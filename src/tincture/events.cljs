(ns tincture.events
  (:import [goog.dom ViewportSizeMonitor])
  (:require [reagent.core :as r]
            [goog.events.EventType :as event-type]
            [goog.events :as gevents]
            [tincture.async :as async]
            [tincture.db :as db]
            [tincture.grid :as grid]
            [reagent.debug :as d]
            [re-frame.core :refer [reg-event-db reg-event-fx reg-fx inject-cofx path trim-v
                                   after debug dispatch]]
            [clojure.spec.alpha :as s]) )

(defonce vsm (ViewportSizeMonitor.))

(defn on-resize [e]
  (let [size (.getSize vsm)]
    (dispatch [:tincture/set-viewport-size [(.-width size) (.-height size)]])))

(defonce vsm-listener
  (gevents/listen vsm event-type/RESIZE
   (async/debounce
    on-resize
    200)))

(reg-event-fx
 :tincture/initialize
 (fn [{:keys [db]}]
   (let [size (.getSize vsm)]
     {:db (-> db
              (merge db/default-db)
              (assoc :tincture/viewport-size [(.-width size) (.-height size)]))})))

(reg-event-db
 :tincture/set-viewport-size
 (fn [db [_ new]]
   (assoc db :tincture/viewport-size new)))


(reg-event-db
 :tincture/set-font-families
 (fn [db [_ fonts]]
   (assoc db :tincture/font-families fonts)))


;; Lato,'Helvetica Neue',Arial,Helvetica,sans-serif
