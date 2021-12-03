(ns aoc-2021.day-03
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn multi-frequencies
  "Creates the frequencies for each column in the given sequence of sequences"
  [xs]
  (let [count-1 (fn [acc x] (update acc x (fnil inc 0)))]
    (reduce (fn [acc ys]
              (mapv count-1 acc ys))
            (repeat (-> xs first count) {})
            xs)))

(defn gamma
  [xs]
  (let [most-common (fn [fs] (key (apply max-key val fs)))]
    (->> (multi-frequencies xs)
         (map most-common)
         (str/join ""))))

(defn compl
  [s]
  (->> s
       (map {\0 \1 \1 \0})
       (str/join "")))

(defn epsilon-from-gamma
    [gamma]
    (compl gamma))

(defn solution-1
  [xs]
  (let [gamma (gamma xs)
        epsilon (epsilon-from-gamma gamma)]
    (* (c/btol gamma) (c/btol epsilon))))

(def input "day/03/input.txt")

(defn part-1
  []
  (-> input
      (file/read-lines)
      (solution-1)))

(defn part-2
  []
  nil)
