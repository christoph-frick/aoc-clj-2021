(ns aoc-2021.day-08
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn parse-line
  [s]
  (let [sets (map set (str/split s #" "))]
    {:inputs (take 10 sets)
     :number (drop 11 sets)}))

(defn parse
  [s]
  (map parse-line (str/split-lines s)))

; right now i have no clue what this would help for part-2...
(defn solution-1
  [s]
  (->> s
       (parse)
       (mapcat :number)
       (map count)
       (filter #{2 3 4 7})
       (count)))

(def input "day/08/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  nil)
