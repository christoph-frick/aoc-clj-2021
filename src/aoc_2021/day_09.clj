(ns aoc-2021.day-09
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn parse
  [s]
  (mapv (partial mapv c/ctol)
        (str/split-lines s)))

(defn setup
  [m]
  (let [height (count m)
        width (count (first m))]
    {:width width
     :height height
     :points (into {}
                   (for [y (range height)
                         x (range width)]
                     [[x y] (get-in m [y x])]))}))

(defn add
  [[x y] [s t]]
  [(+ x s) (+ y t)])

(def neighbour-coords
  [[-1 0] [1 0] [0 -1] [0 1]])

(defn neighbours
  [pos]
  (map (partial add pos) neighbour-coords))

(defn find-low-points
  [{:keys [points]}]
  (into {}
        (filter (fn [[p _]]
                  (every? (partial < (points p)) (->> (neighbours p)
                                                      (map points)
                                                      (remove nil?))))
                points)))

(defn find-basin
  [{:keys [points]} low-point]
  (let [is-basin? (fn [p]
                    (let [h (points p)]
                      (and h (< h 9))))]
    (loop [result #{low-point}
           todo #{low-point}]
      (if (empty? todo)
        result
        (let [result' (set/union result todo)
              todo' (set/difference
                     (->> todo
                          (mapcat neighbours)
                          (set)
                          (filter is-basin?)
                          (set))
                     result')]
          (recur result' todo'))))))

(defn solution-1
  [s]
  (->> s
       (parse)
       (setup)
       (find-low-points)
       (vals)
       (map inc)
       (apply +)))

(defn solution-2
  [s]
  (let [m (->> s (parse) (setup))]
    (->> m
         (find-low-points)
         (keys)
         (map (partial find-basin m))
         (sort-by (comp - count))
         (take 3)
         (map count)
         (apply *))))

(def input "day/09/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  (solution-2 (file/read input)))
