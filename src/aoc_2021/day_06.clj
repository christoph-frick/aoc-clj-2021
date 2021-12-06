(ns aoc-2021.day-06
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn setup
    [initial]
    (frequencies initial))

(defn step
  [state]
  (->> state
       (map (fn next-gen [[gen cnt]]
              (case gen
                0 {6 cnt, 8 cnt}
                {(dec gen) cnt})))
       (apply merge-with +)))

(defn run
  [days state]
  (reduce (fn run-step [state day]
            (step state))
          state
          (range days)))

(defn count-fishes
    [state]
    (apply + (vals state)))

(defn solution-1
  [initial days]
  (->> initial
       (setup)
       (run days)
       (count-fishes)))

(def input "day/06/input.txt")

(defn part
  [days]
  (-> input
      (file/read)
      (c/read-array)
      (solution-1 days)))

(defn part-1
  []
  (part 80))

(defn part-2
  []
  (part 256))
