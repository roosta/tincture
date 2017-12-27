(ns flora-ui.drawer
  #_(:require
   [cljs-css-modules.macro :refer-macros [defstyle]]
   [re-frame.core :as rf]
   [audioskop.components.transition :refer [css-transition]]
   [audioskop.components.typography :refer [typography]]
   [garden.units :refer [px percent]]
   [audioskop.copy :as copy]
   [audioskop.utils :as utils :refer [join-classes]]))

#_(defstyle backdrop-style
  [:.root {:z-index 3
           :width "100%"
           :height "100vh"
           :position "fixed"
           :top 0
           :left 0
           :background-color "rgba(0,0,0,0.6)"
           :opacity 1
           :will-change "opacity"}]
  [:.enter {:opacity 0.01}]
  [:.enter-active {:opacity 1
                   :transition (utils/transition {:prop "opacity"
                                                  :duration "400ms"
                                                  :timing-fn "ease"})}]
  [:.exit {:opacity 1}]
  [:.exit-active {:transition (utils/transition {:prop "opacity"
                                                 :duration "400ms"
                                                 :timing-fn "ease"})
                  :opacity 0.01}])

#_(defn backdrop
  []
  (let [open? (rf/subscribe [:drawer?])]
    [css-transition {:in @open?
                     :mount-on-enter true
                     :unmount-on-exit true
                     :timeout 400
                     :class-names (clj->js {:enter (:enter backdrop-style)
                                           :enterActive (:enter-active backdrop-style)
                                           :exit (:exit backdrop-style)
                                           :exitActive (:exit-active backdrop-style)})}
     [:div {:on-click #(rf/dispatch [:close-drawer])
            :class (:root backdrop-style)}]]))

#_(defstyle drawer-style
  [:.root {}]
  [:.drawer {:width "60%"
             :padding-top (px 24)
             :z-index 3
             :height (str "calc(100% - " (:height copy/appbar) "px)")
             :flex-direction "column"
             :will-change "transform"
             :align-items "center"
             :display "flex"
             :color (:white copy/colors)
             :background (:drawer-background copy/appbar)
             :box-shadow (utils/box-shadow 3)
             :position "fixed"
             :top (px (:height copy/appbar))
             :right 0}]
  [:.enter {:transform "translatex(99.9%)"}]
  [:.link {:position "relative"}]
  [:.underline {:position "absolute"
                :left 0
                :right 0
                ;; :width 0
                :width "100%"
                :bottom "10px"
                :opacity 0
                ;; :opacity 1
                :margin "0 auto"
                :transition "opacity 400ms ease"
                :height (px 1)
                :background-color (:white copy/colors)}]
  [:.lang {:position "absolute"
           :bottom (px 40)}]
  [:.underline-active {:opacity 1}]
  [:.enter-active {:transform "translateX(0)"
                   :transition (utils/transition {:prop "transform"
                                                  :duration "400ms"
                                                  :timing-fn "ease"})}]
  [:.exit {:transform "translateX(0)"}]
  [:.exit-active {:transition (utils/transition {:prop "transform"
                                                 :duration "400ms"
                                                 :timing-fn "ease"})
                  :transform "translateX(99.9%)"}])

#_(defn drawer
  []
  (let [open? (rf/subscribe [:drawer?])
        lang (rf/subscribe [:language])
        page (rf/subscribe [:page])]
    [:nav {:class (:root drawer-style)}
     [backdrop]
     [css-transition {:in @open?
                      :mount-on-enter true
                      :unmount-on-exit true
                      :timeout 400
                      :class-names {:enter (:enter drawer-style)
                                    :enter-active (:enter-active drawer-style)
                                    :exit (:exit drawer-style)
                                    :exit-active (:exit-active drawer-style)}}
      [:div {:class (:drawer drawer-style)}
       (doall (for [item copy/menu]
                (let [val (val item)]
                  ^{:key (key item)}
                  [:a {:on-click #(rf/dispatch [:close-drawer])
                       :class (:link drawer-style)
                       :href (:href val)}
                   [typography {:kind :title}
                    (@lang val)]
                   [:div {:class (join-classes drawer-style :underline (when (= @page (key item)) :underline-active))}]])))
       #_[typography {:class (:lang drawer-style)
                    :on-click #(rf/dispatch [:set-language (case @lang
                                                             :en :no
                                                             :no :en)])
                    :kind :title}
        (case @lang
          :en "Norsk"
          :no "English")
        ]]]]))
