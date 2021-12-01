(ns aoc-2021.day-01
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn count-increasing
  [xs]
  (->> xs
       (partition 2 1)
       (filter (partial apply <))
       (count)))

(defn part-1
  []
  (->> "day/01/input.txt"
       (file/read-lines)
       (map c/stol)
       (count-increasing)))

(defn part-2
    []
    )
