(ns tonic.typography
  (:require
   [garden.units :refer [px percent]]
   [reagent.debug :as d]
   [clojure.string :as str]
   [herb.core :refer-macros [with-style]]
   [cljs.spec.alpha :as s :include-macros true]
   [reagent.core :as r]
   [tonic.core :as t]))

(def font-families {:headline ["'Raleway'" "sans-serif"]
                    :body ["'Open Sans'" "sans-serif"]})

(def font-weight {:light 300
                  :regular 400
                  :medium 500})

(s/def ::valid-kinds #{:title :display1 :display2
                       :display3 :display4 :subheading
                       :headline :button :body1 :body2})

(def kinds {:display1 {:font-size (px 34)
                       :font-weight (:regular font-weight)
                       :font-family (:headline font-families)
                       :line-height "40px"}

            :display2 {:font-size (px 45)
                       :font-weight (:regular font-weight),
                       :font-family (:headline font-families)
                       :line-height (px 48)}

            :display3 {:font-size (px 56)
                       :font-weight (:regular font-weight)
                       :font-family (:headline font-families)
                       :letter-spacing "-.02em"
                       :line-height 1.35}

            :display4 {:font-size (px 112)
                       :font-weight (:light font-weight)
                       :font-family (:headline font-families)
                       :letter-spacing "-.04em"
                       :line-height 1}

            :subheading {:font-size (px 16)
                         :font-weight (:regular font-weight)
                         :font-family (:body font-families)
                         :line-height (px 24)}

            :headline {:font-size (px 24)
                       :font-weight (:regular font-weight)
                       :font-family (:headline font-families)
                       :line-height (px 32)}

            :title {:font-size (px 21)
                    :font-family (:headline font-families)
                    :line-height 1
                    :font-weight (:medium font-weight)}

            :button {:font-size 14
                     :text-transform "uppercase"
                     :font-weight (:medium font-weight)
                     :font-family (:body font-families)}

            :body1 {:font-size (px 14)
                    :font-weight (:regular font-weight)
                    :font-family (:body font-families)
                    :line-height (px 20)}

            :body2 {:font-size (px 14)
                    :font-weight (:regular font-weight)
                    :font-family (:body font-families)
                    :line-height (px 20)}})
(def mapping
  {:display4 :h1
   :display3 :h1
   :display2 :h1
   :display1 :h1
   :headline :h1
   :title :h2
   :subheading :h3
   :body2 :aside
   :body1 :p})

(s/def ::valid-aligns #{:left :right :center :justify})
(s/def ::valid-styles #{:normal :italic})
(s/def ::valid-directions #{:ltr :rtl})

(defn typography-style
  [kind align style direction elevation]
  (let [k (str/join "-" [(name kind) (name align) (name style) (name direction) elevation])
        kinds {:display1 (:display1 kinds)
               :display2 (:display2 kinds)
               :display3 (:display3 kinds)
               :display4 (:display4 kinds)
               :subheading (:subheading kinds)
               :headline (:headline kinds)
               :title (:title kinds)
               :button (:button kinds)
               :body1 (:body1 kinds)
               :body2 (:body2 kinds)}

        directions {:ltr {:direction "ltr"}
                    :rtl {:direction "rtl"}}

        styles {:italic {:font-style "italic"}}

        aligns {:left {:text-align "left"}
                :right {:text-align "right"}
                :center {:text-align "center"}
                :justify {:text-align "justify"}}]

    (with-meta
      (merge
       {:text-shadow (t/text-shadow elevation)}
       (kind kinds)
       (direction directions)
       (style styles)
       (align aligns))
      {:key k})))

;; TODO Add color?
(defn typography
  [{:keys [kind align class elevation style on-click direction]
    :or {kind :body1
         style :normal
         align :left
         direction :ltr
         elevation 0}}]
  {:pre [(s/valid? ::valid-aligns align)
         (s/valid? ::valid-kinds kind)
         (s/valid? ::valid-directions direction)
         (s/valid? ::valid-styles style)]}
  (let [class* (with-style typography-style kind align style direction elevation)]
    (into
     [(kind mapping) {:on-click on-click
                      :class (if class (str class " " class*)
                                 class*)}]
     (r/children (r/current-component)))))
