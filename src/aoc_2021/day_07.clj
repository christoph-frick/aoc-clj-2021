(ns aoc-2021.day-07
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn min-max
  [[x :as xs]]
  (reduce (fn [[a b] x]
            [(min a x) (max b x)])
          [x x]
          xs))

(defn dist
  [a b]
  (Math/abs (- b a)))

(defn half
  [x]
  (bit-shift-right x 1))

(defn triangular
  [a b]
  (let [n (dist a b)]
    (half (* n (inc n)))))

(defn optimize1
  [cost-fn xs center]
  (transduce (map (partial cost-fn center)) + 0 xs))

(defn optimize
  [cost-fn xs]
  (let [[xmin xmax] (min-max xs)]
    (loop [l xmin
           r (inc xmax)]
      (let [c (+ l (half (- r l)))
            [fl fc fr] (map (partial optimize1 cost-fn xs) [l c r])]
        (if (or (= l c) (= c r))
          (if (< fl fr)
            [l fl]
            [r fr])
          (if (< (+ fl fc) (+ fc fr))
            (recur l c)
            (recur c r)))))))

(def input "day/07/input.txt")

(defn solution
  [cost-fn input]
  (->> input
       (file/read)
       (c/read-array)
       (optimize cost-fn)
       (second)))

(defn part-1
  []
  (solution dist input))

(defn part-2
  []
  (solution triangular input))
