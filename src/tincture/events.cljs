(ns tincture.events
  (:import [goog.dom ViewportSizeMonitor])
  (:require [reagent.core :as r]
            [goog.events.EventType :as event-type]
            [goog.dom :as dom]
            [goog.events :as gevents]
            [tincture.async :as async]
            [tincture.grid :as grid]
            [reagent.debug :as d]
            [re-frame.core :refer [reg-event-db reg-event-fx reg-fx inject-cofx path trim-v
                                   after debug dispatch]]
            [clojure.spec.alpha :as s]))

(defonce ^:private vsm (ViewportSizeMonitor.))

(def ^:private default-db
  {:tincture/viewport-size nil
   :tincture/font {:font/family nil
                   :font/url nil}})

(defn- on-resize [e]
  (let [size (.getSize vsm)]
    (dispatch [:tincture/set-viewport-size [(.-width size) (.-height size)]])))

(defonce ^:private vsm-listener
  (gevents/listen vsm event-type/RESIZE
   (async/debounce
    on-resize
    200)))

(reg-event-fx
 :tincture/initialize
 (fn [{:keys [db]} [_ {:keys [font-family font-url]}]]
   (let [size (.getSize vsm)]
     (cond-> {:db (-> db
                      (merge default-db)
                      (assoc :tincture/viewport-size [(.-width size) (.-height size)]))}
       font-family (assoc-in [:db :tincture/font :font/family] font-family)
       font-url (assoc-in [:db :tincture/font :font/url] font-url)
       font-url (assoc :tincture.font/attach font-url)))))

(reg-event-db
 :tincture/set-viewport-size
 (fn [db [_ new]]
   (assoc db :tincture/viewport-size new)))

#_(reg-event-fx
 :tincture/set-font
 (fn [{:keys [db]} [_ font-family font-url]]
   {:db (-> (assoc-in db [:tincture/font :font/family] font-family)
            (assoc-in [:tincture/font :font/url] font-url))
    :tincture.font/attach font-url}))

(defn- attach-font!
  "Attach font to head of js/document. Checks if element is already
  present and only attach if its not."
  [font-url]
  (let [el (dom/getElement "tincture-font")
        head (.-head js/document)]
    (when (not el)
      (let [el (.createElement js/document "link")]
        (set! (.-id el) "tincture-font")
        (.setAttribute el "href" font-url)
        (.setAttribute el "rel" "stylesheet")
        (.appendChild head el)))))

(reg-fx
 :tincture.font/attach
 (fn [url]
   (attach-font! url)))
