(ns aoc-2021.day-15
  (:require [aoc.pos2d :as pos]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn parse
  [s]
  (->> s
       (str/split-lines)
       (mapv (partial mapv c/ctol))
       (pos/coord-map)))

(defn calc-path
  [coords path]
  (apply + (map coords (rest path))))

(defn children
  [coords node]
  (for [ofs [[1 0] [0 1]]
        :let [c-pos (pos/add (:pos node) ofs)]
        :when (contains? coords c-pos)]
    c-pos))

(defn start
  [_]
  [0 0])

(defn end
  [{:keys [width height]}]
  [(dec width) (dec height)])

(defn dijkstra
  [{:keys [coords] :as m}]
  (let [[start end] ((juxt start end) m)]
    (loop [node start
           unvisited (into
                      (sorted-set)
                      (keys coords))
           dists {start 0}]
      (if (empty? unvisited)
        (dists end)
        (let [neighbours (into {}
                               (comp
                                (map (partial pos/add node))
                                (filter unvisited)
                                (map (fn [pos]
                                       [pos (+ (dists node) (coords pos))])))
                               [[1 0] [0 1]])
              dists' (merge-with min dists neighbours)
              unvisited' (disj unvisited node)]
          (recur (first unvisited') unvisited' dists'))))))

(defn times-5
  [{:keys [width height coords]}]
  (let [mod10 #(if (> % 9) (- % 9) %)]
    {:coords (into coords
                   (for [h (range 5)
                         w (range 5)
                         :when (not (= h w 0))
                         y (range height)
                         x (range width)
                         :let [o [x y]
                               t (pos/add o [(* w width) (* h height)])]]
                     [t (mod10 (+ h w (coords o)))]))
     :height (* 5 height)
     :width (* 5 width)}))

(defn solution-1
  [s]
  (-> s
      (parse)
      (dijkstra)))

(defn solution-2
  [s]
  (-> s
      (parse)
      (times-5)
      (dijkstra)))
