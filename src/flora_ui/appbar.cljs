(ns flora-ui.appbar
  #_(:require
   [cljs-css-modules.macro :refer-macros [defstyle]]
   [re-frame.core :as rf]
   [audioskop.utils :as utils :refer [join-classes]]
   [audioskop.components.typography :refer [typography]]
   [audioskop.components.icons :as icons]
   [garden.units :refer [px percent]]
   [audioskop.copy :as copy]
   [reagent.core :as r]))

#_(defstyle appbar-styles

  [:.root {:position "fixed"
           :top 0
           :display #{"-webkit-box" "-moz-box" "-ms-flexbox" "-webkit-flexbox" "-webkit-flex" "flex"}
           :justify-content "space-between"
           :box-shadow (utils/box-shadow 3)
           :align-items "center"
           :z-index 99
           :height (px (:height copy/appbar))
           :background (:background copy/appbar)
           :padding "0 35px"
           :color (:white copy/colors)
           :left 0
           :right 0}]
  [:.bars {:display "none"
           :width (px 24)
           :color (:white copy/colors)
           :heigh (px 24)}]
  [:.button {:user-select "none"
             :-ms-user-select "none"
             :-webkit-user-select "none"
             :-moz-user-select "none"}]
  (at-media {:max-width "660px"}
            [:.bars {:display "inline"}])

  [:.menu {:display #{"-webkit-box" "-moz-box" "-ms-flexbox" "-webkit-flexbox" "-webkit-flex" "flex"}
           :justify-content "space-between"
           :align-items "center"
           :width (px 400)
           :color (:white copy/colors)}]

  (at-media {:max-width "660px"}
            [:.menu {:display "none"}])

  [:.menu-item {:position "relative"
                :cursor "pointer"}]

  [:.menu-typo {:margin 0}]

  [:.underline {:position "absolute"
                :left 0
                :right 0
                :width 0
                :bottom "-5px"
                :opacity 0
                :margin "0 auto"
                :transition "width 400ms ease, opacity 400ms ease"
                :height (px 1)
                :background-color (:white copy/colors)}]
  [:.separator {:height "25px"
                :width "1px"
    :background-color "white"}]
  [:.hover {:width "100%"
            :opacity 1}]

  [:.title {:color (:white copy/colors)
            :margin-left (px 8)}]
  [:.logo-large {:width "180px"}]
  [:.logo-s {:width "28px"
             :margin-right (px 10)
             :color "white"
             }]
  [:.label-s {:width (px 150)
              :color (:white copy/colors)}]

  [:.logo {:width "28px"
           :color (:white copy/colors)
           :height "28px"}]

  [:.brand {:display #{"-webkit-box" "-moz-box" "-ms-flexbox" "-webkit-flexbox" "-webkit-flex" "flex"}
            :align-items "center"}])

#_(defn appbar
  []
  (let [hover? (r/atom nil)]
    (fn []
      (let [lang (rf/subscribe [:language])
            page (rf/subscribe [:page])]
        [:header {:class (:root appbar-styles)}
         [:a {:href "/"
              :on-click #(rf/dispatch [:close-drawer])
              :class (:brand appbar-styles)}
          #_[icons/logo-large {:class (:logo-large appbar-styles)}]
          [icons/logo-s {:class (:logo-s appbar-styles)}]
          [icons/label-s {:class (:label-s appbar-styles)}]
          #_[typography {:kind :title
                       :class (:title appbar-styles)}
           "AUDIOSKOP"]]
         [:div {:class (:button appbar-styles)
                :on-click #(rf/dispatch [:toggle-drawer])}
          [icons/bars {:class (:bars appbar-styles)}]]
         [:nav {:class (:menu appbar-styles)}
          (doall
           (for [menu-item copy/menu]
             ^{:key menu-item}
             [:a {:on-mouse-enter #(reset! hover? (key menu-item))
                  :on-mouse-leave #(reset! hover? nil)
                  :class (:menu-item appbar-styles)
                  :href (:href (val menu-item))}
              [typography {:class (:menu-typo appbar-styles)
                           :kind :subheading}
               (@lang (val menu-item))]
              [:div {:class (join-classes appbar-styles
                                          :underline
                                          (when (or (= @hover? (key menu-item))
                                                    (= (key menu-item) @page))
                                            :hover))}]]))

          #_[:div {:class (:separator appbar-styles)}]
          ;; language
          #_[:div {:on-mouse-enter #(reset! hover? :language)
                 :on-mouse-leave #(reset! hover? nil)
                 :on-click #(rf/dispatch [:set-language (case @lang
                                                         :en :no
                                                         :no :en)])
                 :class (:menu-item appbar-styles)}
           [typography {:class (:menu-typo appbar-styles)
                        :kind :subheading}
            (case @lang
              :en "Norsk"
              :no "English")]
           [:div {:class (join-classes appbar-styles :underline (when (= @hover? :language) :hover))}]]
          ]]))))
