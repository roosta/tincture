(ns flora-ui.footer
  #_(:require
   [cljs-css-modules.macro :refer-macros [defstyle]]
   [re-frame.core :as rf]
   [audioskop.utils :refer [join-classes]]
   [audioskop.components.typography :refer [typography]]
   [garden.selectors :as s]
   [audioskop.components.icons :as icons]
   [garden.units :refer [px percent]]
   [audioskop.copy :as copy]
   [reagent.core :as r]
   [reagent.debug :as d]))

#_(defstyle footer-style
  [:.root {:background (:background copy/footer)
           :overflow "auto"
           :display "flex"
           :align-items "center"
           :justify-content "center"
           :color (:white copy/colors)
           :height (px (:height copy/footer))}]
  [:.container {:width "40%"}]
  [:.icon {:width "28px"
           :margin (px 7)
           :height "28px"}]
  [:.icons {:text-align "center"}]
  [:.typo {:margin 0
           :margin-bottom (px 5)}]
  [:.head {:margin-bottom (px 10)}]
  (at-media {:max-width "48em"}
            [:.container {:width "80%"}]))

#_(defn footer
  []
  (let [lang (rf/subscribe [:language])]
    [:footer {:class (:root footer-style)}
     [:div {:class (:container footer-style)}
      [typography {:kind :title
                   :class (join-classes footer-style :typo :head)
                   :align :center}
       (:head copy/footer)]
      [typography {:kind :subheading
                   :class (:typo footer-style)
                   :align :center}
       (:address copy/contact-info)]
      [typography {:kind :subheading
                   :class (:typo footer-style)
                   :align :center}
       (:city copy/contact-info)]
      [:a {:href (str "mailto:" (:email copy/contact-info))}
       [typography {:kind :subheading
                    :class (:typo footer-style)
                    :align :center}
        (:email copy/contact-info)]]
      #_[typography {:kind :body1
                   :align :center}
       (@lang (:body copy/footer))]
      [:div {:class (:icons footer-style)}
       [:a {:target "_blank"
            :href (:facebook copy/contact-info)}
        [icons/facebook {:class (:icon footer-style)}]]
       [:a {:href (:instagram copy/contact-info)
            :target "_blank"}
        [icons/instagram {:class (:icon footer-style)}]]]
      ]]))
