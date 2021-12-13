(ns aoc-2021.day-13
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn parse
  [s]
  (let [[coords instrs] (c/split-groups s)]
    {:coords (into #{}
                   (partition-all 2)
                   (c/read-array coords))
     :instrs (into []
                   (comp
                    (map #(re-find #"fold along ([xy])=(\d+)" %))
                    (map rest)
                    (map (fn [[coord n]] [(keyword coord) (c/stol n)])))
                   (str/split-lines instrs))}))

(def coord-to-index
  {:x 0
   :y 1})

(defn fold
  [coords [axis n]]
  (let [coord-idx (coord-to-index axis)
        halves (group-by (fn [coord]
                           (> (nth coord coord-idx) n))
                         coords)]
    (into (set (halves false))
          (map (fn [coord]
                 (update coord coord-idx (partial - (* 2 n)))))
          (halves true))))

(defn solution-1
  [s]
  (let [{:keys [coords instrs]} (parse s)]
    (count (fold coords (first instrs)))))

(def input "day/13/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  nil)
