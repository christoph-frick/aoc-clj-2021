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

(def rating-illegal
  {\) 3
   \] 57
   \} 1197
   \> 25137})

(def rating-incomplete
  {\) 1
   \] 2
   \} 3
   \> 4})

(defmulti rate first)

(defmethod rate :illegal
  [[_ c]]
  (rating-illegal c))

(defmethod rate :incomplete
  [[_ cs]]
  (reduce #(+ (* 5 %1) %2)
          0
          (map rating-incomplete cs)))

(defn status-pred
  [status]
  (fn [[e]]
    (= status e)))

(defn solution
  [reducer init status s]
  (transduce
   (comp (map check)
         (filter (status-pred status))
         (map rate))
   reducer
   init
   (parse s)))

(defn solution-1
  [s]
  (solution + 0 :illegal s))

(defn solution-2
  [s]
  (let [results (sort (solution conj [] :incomplete s))
        idx (bit-shift-right (count results) 1)]
    (nth results idx)))

(def input "day/10/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  (solution-2 (file/read input)))
