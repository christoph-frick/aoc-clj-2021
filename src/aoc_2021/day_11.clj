(ns aoc-2021.day-11
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [aoc.pos2d :as pos]
            [clojure.string :as str]))

(defn parse
  [s]
  (->> s
       (str/split-lines)
       (mapv (partial mapv c/ctol))
       (pos/coord-map)
       :coords))

(defn step1
  [m pos]
  (let [v (m pos 10)]
    (cond
      (< v 9) [(update m pos inc) []]
      (= v 9) [(assoc m pos 10) (pos/neighbours-8 pos)]
      :else [m []])))

(defn step-pos
  [m pos]
  (loop [[m [p & ps]] (step1 m pos)]
    (if p
      (let [[m' todo'] (step1 m p)]
        (recur [m' (into ps todo')]))
      m)))

(defn gt9?
  [x]
  (< 9 x))

(defn flip
  [x]
  (if (gt9? x) 0 x))

(defn step
  [m]
  (let [m' (reduce step-pos m (keys m))
        flash-count (count (filter gt9? (vals m')))
        clean-m (zipmap (keys m') (map flip (vals m')))]
    [clean-m flash-count]))

(defn run
  [m steps]
  (reduce (fn [[m cnt] _]
            (let [[m' cnt'] (step m)]
              [m' (+ cnt cnt')]))
          [m 0]
          (range steps)))

(def input "day/11/input.txt")

(defn part-1
  []
  (-> input
      (file/read)
      (parse)
      (run 100)
      (second)))

(defn part-2
  []
  nil)
