(ns tincture.icons
  (:require
   [herb.core :refer-macros [<class]]
   [clojure.string :as str]
   [reagent.core :as r]))

(defn svg-icon-style
  []
  {:display "inline-block"
   :fill "currentColor"
   ;; :height "24px"
   ;; :width "24px"
   :user-select "none"
   :flex-shrink 0
   ;; :transition ""
   })

(defn svg-icon
  [{:keys [class viewbox]}]
  (into
   [:svg {:view-box viewbox
          :class [class (<class svg-icon-style)]}]
   (r/children (r/current-component))))

(defn mail
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 28 28"}
   [:path {:d "m28 11.094v12.406c0 1.375-1.125 2.5-2.5 2.5h-23c-1.375 0-2.5-1.125-2.5-2.5v-12.406c0.469 0.516 1 0.969 1.578 1.359 2.594 1.766 5.219 3.531 7.766 5.391 1.313 0.969 2.938 2.156 4.641 2.156h0.031c1.703 0 3.328-1.188 4.641-2.156 2.547-1.844 5.172-3.625 7.781-5.391 0.562-0.391 1.094-0.844 1.563-1.359zm28 6.5c0 1.75-1.297 3.328-2.672 4.281-2.438 1.687-4.891 3.375-7.313 5.078-1.016 0.703-2.734 2.141-4 2.141h-0.031c-1.266 0-2.984-1.437-4-2.141-2.422-1.703-4.875-3.391-7.297-5.078-1.109-0.75-2.688-2.516-2.688-3.938 0-1.531 0.828-2.844 2.5-2.844h23c1.359 0 2.5 1.125 2.5 2.5z"}]])

(defn instagram
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 28"}
   [:path {:d "M16 14c0-2.203-1.797-4-4-4s-4 1.797-4 4 1.797 4 4 4 4-1.797 4-4zM18.156 14c0 3.406-2.75 6.156-6.156 6.156s-6.156-2.75-6.156-6.156 2.75-6.156 6.156-6.156 6.156 2.75 6.156 6.156zM19.844 7.594c0 0.797-0.641 1.437-1.437 1.437s-1.437-0.641-1.437-1.437 0.641-1.437 1.437-1.437 1.437 0.641 1.437 1.437zM12 4.156c-1.75 0-5.5-0.141-7.078 0.484-0.547 0.219-0.953 0.484-1.375 0.906s-0.688 0.828-0.906 1.375c-0.625 1.578-0.484 5.328-0.484 7.078s-0.141 5.5 0.484 7.078c0.219 0.547 0.484 0.953 0.906 1.375s0.828 0.688 1.375 0.906c1.578 0.625 5.328 0.484 7.078 0.484s5.5 0.141 7.078-0.484c0.547-0.219 0.953-0.484 1.375-0.906s0.688-0.828 0.906-1.375c0.625-1.578 0.484-5.328 0.484-7.078s0.141-5.5-0.484-7.078c-0.219-0.547-0.484-0.953-0.906-1.375s-0.828-0.688-1.375-0.906c-1.578-0.625-5.328-0.484-7.078-0.484zM24 14c0 1.656 0.016 3.297-0.078 4.953-0.094 1.922-0.531 3.625-1.937 5.031s-3.109 1.844-5.031 1.937c-1.656 0.094-3.297 0.078-4.953 0.078s-3.297 0.016-4.953-0.078c-1.922-0.094-3.625-0.531-5.031-1.937s-1.844-3.109-1.937-5.031c-0.094-1.656-0.078-3.297-0.078-4.953s-0.016-3.297 0.078-4.953c0.094-1.922 0.531-3.625 1.937-5.031s3.109-1.844 5.031-1.937c1.656-0.094 3.297-0.078 4.953-0.078s3.297-0.016 4.953 0.078c1.922 0.094 3.625 0.531 5.031 1.937s1.844 3.109 1.937 5.031c0.094 1.656 0.078 3.297 0.078 4.953z"}]])

(defn facebook
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 28"}
   [:path {:d "M19.5 2c2.484 0 4.5 2.016 4.5 4.5v15c0 2.484-2.016 4.5-4.5 4.5h-2.938v-9.297h3.109l0.469-3.625h-3.578v-2.312c0-1.047 0.281-1.75 1.797-1.75l1.906-0.016v-3.234c-0.328-0.047-1.469-0.141-2.781-0.141-2.766 0-4.672 1.687-4.672 4.781v2.672h-3.125v3.625h3.125v9.297h-8.313c-2.484 0-4.5-2.016-4.5-4.5v-15c0-2.484 2.016-4.5 4.5-4.5h15z"}]])

(defn twitter
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 28"}
   [:path {:d "M20 9.531c-0.594 0.266-1.219 0.438-1.891 0.531 0.688-0.406 1.203-1.062 1.453-1.828-0.641 0.375-1.344 0.656-2.094 0.797-0.594-0.641-1.453-1.031-2.391-1.031-1.813 0-3.281 1.469-3.281 3.281 0 0.25 0.016 0.516 0.078 0.75-2.734-0.141-5.156-1.437-6.781-3.437-0.281 0.484-0.453 1.062-0.453 1.656 0 1.141 0.531 2.141 1.422 2.734-0.547-0.016-1.062-0.172-1.563-0.406v0.031c0 1.594 1.203 2.922 2.703 3.219-0.281 0.078-0.5 0.125-0.797 0.125-0.203 0-0.406-0.031-0.609-0.063 0.422 1.297 1.625 2.25 3.063 2.281-1.125 0.875-2.531 1.406-4.078 1.406-0.266 0-0.531-0.016-0.781-0.047 1.453 0.922 3.172 1.469 5.031 1.469 6.031 0 9.344-5 9.344-9.344 0-0.141 0-0.281-0.016-0.422 0.641-0.453 1.203-1.031 1.641-1.703zM24 6.5v15c0 2.484-2.016 4.5-4.5 4.5h-15c-2.484 0-4.5-2.016-4.5-4.5v-15c0-2.484 2.016-4.5 4.5-4.5h15c2.484 0 4.5 2.016 4.5 4.5z"}]])

(defn bars
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 28"}
   [:path {:d "M24 21v2c0 0.547-0.453 1-1 1h-22c-0.547 0-1-0.453-1-1v-2c0-0.547 0.453-1 1-1h22c0.547 0 1 0.453 1 1zM24 13v2c0 0.547-0.453 1-1 1h-22c-0.547 0-1-0.453-1-1v-2c0-0.547 0.453-1 1-1h22c0.547 0 1 0.453 1 1zM24 5v2c0 0.547-0.453 1-1 1h-22c-0.547 0-1-0.453-1-1v-2c0-0.547 0.453-1 1-1h22c0.547 0 1 0.453 1 1z"}]])

(defn chevron-right
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 24"}
   [:path {:d "M8.578 16.359l4.594-4.594-4.594-4.594 1.406-1.406 6 6-6 6z"}]])

(defn chevron-left
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 24"}
   [:path {:d "M15.422 16.078l-1.406 1.406-6-6 6-6 1.406 1.406-4.594 4.594z"}]])

(defn chevron-down
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 24"}
   [:path {:d "M 7.41,8.295 6,9.705 l 6,6 6,-6 -1.41,-1.41 -4.59,4.58 z"}]])

(defn chevron-up
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 24"}
   [:path {:d "m16.59 15.705 1.41-1.41-6-6-6 6 1.41 1.41 4.59-4.58z"}]])

(defn menu
  [{:keys [class]}]
  [svg-icon {:class class
             :viewbox "0 0 24 24"}
   [:path {:d "M3 18h18v-2H3v2zm0-5h18v-2H3v2zm0-7v2h18V6H3z"}]])
