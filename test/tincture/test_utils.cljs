(ns tincture.test-utils)

(defn container-div []
  (let [id (str "container-" (gensym))
        node (.createElement js/document "div")]
    (set! (.-id node) id)
    [node id]))

(defn insert-container! [container]
  (.appendChild (.-body js/document) container))

(defn new-container! []
  (let [[n s] (container-div)]
    (insert-container! n)
    (.getElementById js/document s)))

