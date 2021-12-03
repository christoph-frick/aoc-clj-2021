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

(defn bit-criteria
  [rating-fn fallback xs]
  (loop [pos 0
         xs xs]
    (let [freqs (group-by #(nth % pos) xs)
          [count0 count1] (mapv #(count (get freqs % [])) [\0 \1])
          next-key (if (= count0 count1)
                     fallback
                     (if (rating-fn count0 count1)
                       \0
                       \1))
          xs' (get freqs next-key)]
      (if (= 1 (count xs'))
        (first xs')
        (recur (inc pos) xs')))))

(def find-o2
  (partial bit-criteria > \1))

(def find-co2
  (partial bit-criteria < \0))

(defn solution-2
  [xs]
  (apply * (map #(c/btol (% xs)) [find-o2 find-co2])))

(def input "day/03/input.txt")

(defn part-1
  []
  (-> input
      (file/read-lines)
      (solution-1)))

(defn part-2
  []
  (-> input
      (file/read-lines)
      (solution-2)))
