(ns aoc-2021.day-05
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn parse-line
  [s]
  (let [[x1 y1 x2 y2] (map
                       c/stol
                       (rest
                        (re-find #"(\d+),(\d+) -> (\d+),(\d+)" s)))]
    [[x1 y1] [x2 y2]]))

(defn dir
  [[x1 y1] [x2 y2]]
  [(compare x2 x1) (compare y2 y1)])

(defn hor-or-vert?
  [[[x1 y1] [x2 y2]]]
  (or (= x1 x2) (= y1 y2)))

(defn apply-line
  [ground [s e]]
  (reduce
   (fn [ground pos]
     (update ground pos (fnil inc 0)))
   ground
   (let [[dx dy] (dir s e)
         inc (fn [[x y]] [(+ x dx) (+ y dy)])]
     (reductions (fn [pos _]
                   (let [pos' (inc pos)]
                     (if (= pos' e)
                       (reduced pos')
                       pos')))
                 s
                 (range)))))

(defn count-overlaps
  [ground]
  (->> ground
       (vals)
       (filter (partial < 1 ))
       (count)))

(def input "day/05/input.txt")

(defn solution
  [filter-fn ss]
  (let [lines (->> ss (map parse-line) (filter filter-fn))
        ground (reduce apply-line {} lines)]
    (count-overlaps ground)))

(defn part-1
  []
  (solution hor-or-vert? (file/read-lines input)))

(defn part-2
  []
  (solution identity (file/read-lines input)))
