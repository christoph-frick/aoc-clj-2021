(ns aoc-2021.day-07
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn min-max
  [[x :as xs]]
  (reduce (fn [[a b] x]
            [(min a x) (max b x)])
          [x x]
          xs))

(defn dist
    [a b]
    (Math/abs (- b a)))

(defn triangular
  [a b]
  (let [n (dist a b)]
    (bit-shift-right (* n (inc n)) 1)))

(defn optimize1
  [cost-fn xs center]
  (apply + (map (partial cost-fn center) xs)))

(defn optimize
  [cost-fn xs]
  (let [[xmin xmax] (min-max xs)
        attempts (into {} (map #(vector % (optimize1 cost-fn xs %))) (range xmin (inc xmax)))]
    (apply min-key val attempts)))

(def input "day/07/input.txt")

(defn solution
  [cost-fn input]
  (->> input
       (file/read)
       (c/read-array)
       (optimize cost-fn)
       (val)))

(defn part-1
  []
  (solution dist input))

(defn part-2
  []
  (solution triangular input))
