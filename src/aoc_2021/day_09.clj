(ns aoc-2021.day-09
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]
            [clojure.set :as set]
            [aoc.pos2d :as pos]))

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

(defn low-point?
  [points p]
  (every? (partial < (points p))
          (->> (pos/neighbours-4 p)
               (map points)
               (remove nil?))))

(defn find-low-points
  [{:keys [points]}]
  (into {}
        (filter #(low-point? points (key %)))
        points))

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
                     (into #{}
                           (comp (mapcat pos/neighbours-4)
                                 (filter is-basin?))
                           todo)
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
