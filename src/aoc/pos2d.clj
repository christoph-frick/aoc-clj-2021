(ns aoc.pos2d)

(def neighbour-coords-4
  [[-1 0] [1 0] [0 -1] [0 1]])


(def neighbour-coords-8
  (into []
        (for [y (range -1 2)
              x (range -1 2)
              :when (not= x y 0)]
          [x y])))

(defn add
  [[x y] [u v]]
  [(+ x u) (+ y v)])

(defn neighbours-fn
  [coords]
  (fn neighbours [pos]
    (map #(add pos %)
         coords)))

(def neighbours-4 (neighbours-fn neighbour-coords-4))

(def neighbours-8 (neighbours-fn neighbour-coords-8))

(defn coord-map
  [rows]
  (let [height (count rows)
        width (count (first rows))]
    {:width width
     :height height
     :coords (into {}
                   (for [y (range height)
                         x (range width)]
                     [[x y] (get-in rows [y x])]))}))

(defn coords-to-sorted-nested
  [m]
  (reduce (fn [acc [x y :as pos]]
            (update acc y (fnil assoc (sorted-map)) x (m pos)))
          (sorted-map)
          (keys m)))
