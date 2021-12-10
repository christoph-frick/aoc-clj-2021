(ns aoc-2021.day-10
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn parse
  [s]
  (str/split-lines s))

(def pairs
  {\( \)
   \{ \}
   \[ \]
   \< \>})

(defn opening?
  [c]
  (contains? pairs c))

(defn check
  [s]
  (loop [open []
         [c & cs] s]
    (if (nil? c)
      (if (empty? open)
        [:ok]
        [:incomplete (->> open (reverse) (mapv pairs))])
      (if (opening? c)
        (recur (conj open c) cs)
        (let [last (peek open)]
          (if (= c (pairs last))
            (recur (pop open) cs)
            [:illegal c]))))))

(def rating
  {\) 3
   \] 57
   \} 1197
   \> 25137})

(defn solution-1
  [s]
  (transduce
   (comp (map check)
         (filter (fn [[e & _]] (= :illegal e)))
         (map second)
         (map rating))
   +
   0
   (parse s)))

(def input "day/10/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  nil)
