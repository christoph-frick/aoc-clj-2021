(ns aoc-2021.day-08
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-line
  [s]
  (let [sets (map set (str/split s #" "))]
    {:inputs (take 10 sets)
     :number (drop 11 sets)}))

(defn parse
  [s]
  (map parse-line (str/split-lines s)))

(defn cnt=?
  [xs n]
  (= (count xs) n))

(defn cnt-pred
  [x]
  (fn [_ input]
    (cnt=? input x)))

(defn cnt-mask-cnt-pred
  [initial-count mask count-after-mask]
  (fn [solution input]
    (let [to-mask (solution mask)]
      (and
       (cnt=? input initial-count)
       (cnt=? (set/difference input to-mask) count-after-mask)))))

(defn solution
  [{:keys [inputs] :as puzzle}]
  (let [steps [1 (cnt-pred 2)
               7 (cnt-pred 3)
               4 (cnt-pred 4)
               8 (cnt-pred 7)
               9 (cnt-mask-cnt-pred 6 4 2)
               2 (cnt-mask-cnt-pred 5 4 3)
               3 (cnt-mask-cnt-pred 5 1 3)
               5 (cnt-mask-cnt-pred 5 1 4)
               0 (cnt-mask-cnt-pred 6 1 4)
               6 (cnt-mask-cnt-pred 6 1 5)]

        solve-fn (fn [{:keys [inputs solution] :as acc} [nr pred]]
                   (let [input (first (filter (partial pred solution) inputs))]
                     (-> acc
                         (update :inputs disj input)
                         (update :solution assoc nr input))))

        solution (reduce solve-fn
                         {:inputs (into #{} inputs)
                          :solution {}}
                         (partition 2 steps))]
    (assoc puzzle
           :solution (set/map-invert (:solution solution)))))

(defn number
  [{:keys [solution number]}]
  (->> number
       (map solution)
       (str/join)
       (c/stol)))

(defn solve
  [puzzle]
  (number (solution puzzle)))

; right now i have no clue what this would help for part-2...
(defn solution-1
  [s]
  (->> s
       (parse)
       (mapcat :number)
       (map count)
       (filter #{2 3 4 7})
       (count)))

(defn solution-2
  [s]
  (->> s
       (parse)
       (map solve)
       (apply +)))

(def input "day/08/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  (solution-2 (file/read input)))
