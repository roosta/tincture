(ns flora-ui.typography
  (:require-macros [flora-ui.macro :refer [defui]])
  (:require
   [garden.units :refer [px percent]]
   [reagent.debug :as d]
   [clojure.string :as str]
   [herb.macro :refer-macros [with-style]]
   [cljs.spec.alpha :as s :include-macros true]
   [reagent.core :as r]
   [flora-ui.utils :as utils]
   #_[cljs-css-modules.macro :refer-macros [defstyle]]))

(def font-weight {:light 300
                  :regular 400
                  :medium 500})

(s/def ::valid-kinds #{:title :display1 :display2
                       :display3 :display4 :subheading
                       :headline :button :body1 :body2})

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
  [theme kind align style direction elevation]
  (let [k (str/join "-" [(name kind) (name align) (name style) (name direction) elevation])
        kinds {:display1 (-> theme :typography :display1)
               :display2 (-> theme :typography :display2)
               :display3 (-> theme :typography :display3)
               :display4 (-> theme :typography :display4)
               :subheading (-> theme :typography :subheading)
               :headline (-> theme :typography :headline)
               :title (-> theme :typography :title)
               :button (-> theme :typography :button)
               :body1 (-> theme :typography :body1)
               :body2 (-> theme :typography :body2)}

        directions {:ltr {:direction "ltr"}
                    :rtl {:direction "rtl"}}

        styles {:italic {:font-style "italic"}}

        aligns {:left {:text-align "left"}
                :right {:text-align "right"}
                :center {:text-align "center"}
                :justify {:text-align "justify"}}]

    (with-meta
      (merge
       {:text-shadow (utils/text-shadow elevation)}
       (kind kinds)
       (direction directions)
       (style styles)
       (align aligns))
      {:key k})))

;; TODO Add color?
(defui typography
  (fn [{:keys [theme kind align class elevation style on-click direction]
        :or {kind :body1
             style :normal
             align :left
             direction :ltr
             elevation 0}}]
    {:pre [(s/valid? ::valid-aligns align)
           (s/valid? ::valid-kinds kind)
           (s/valid? ::valid-directions direction)
           (s/valid? ::valid-styles style)]}
    (let [class* (with-style typography-style theme kind align style direction elevation)]
      (into
       [(kind mapping) {:on-click on-click
                        :class (if class (str class " " class*)
                                   class*)}]
       (r/children (r/current-component))))))
