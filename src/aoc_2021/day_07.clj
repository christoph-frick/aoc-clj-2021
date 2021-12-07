(ns aoc-2021.day-07
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn min-max
  [[x :as xs]]
  (reduce (fn [[a b] x]
            [(min a x) (max b x)])
          [x x]
          xs))

(defn optimize1
  [xs center]
  (apply + (map #(Math/abs (- % center)) xs)))

(defn optimize
  [xs]
  (let [[xmin xmax] (min-max xs)
        attempts (into {} (map #(vector % (optimize1 xs %))) (range xmin (inc xmax)))]
    (apply min-key val attempts)))

(def input "day/07/input.txt")

(defn part-1
  []
  (-> input
      (file/read)
      (c/read-array)
      (optimize)
      (val)))

(defn part-2
  []
  nil)
