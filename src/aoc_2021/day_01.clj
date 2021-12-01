(ns aoc-2021.day-01
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn count-increasing
  [window-size xs]
  (->> xs
       (partition window-size 1)
       (map (partial apply +))
       (partition 2 1)
       (filter (partial apply <))
       (count)))

(defn part
  [window-size]
  (count-increasing
   window-size
   (->> "day/01/input.txt"
        (file/read-lines)
        (map c/stol))))

(defn part-1
  []
  (part 1))

(defn part-2
  []
  (part 3))
