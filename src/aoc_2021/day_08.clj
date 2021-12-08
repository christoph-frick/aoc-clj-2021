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

(defn cnt-pred
  [x]
  (fn [_ input]
    (= x (count input))))

(defn cnt-mask-cnt-pred
  [initial-count mask count-after-mask]
  (fn [solution input]
    (let [to-mask (solution mask)]
      (and
       (= initial-count (count input))
       (= count-after-mask (count (set/difference input to-mask)))))))

(defn solution
  [{:keys [inputs] :as puzzle}]
  (let [solution (reduce (fn [{:keys [inputs solution]} [nr f]]
                           (let [split (group-by (partial f solution) inputs)]
                             {:inputs (split false)
                              :solution (assoc solution nr (first (split true)))}))
                         {:inputs inputs
                          :solution {}}
                         (partition 2 [1 (cnt-pred 2)
                                       7 (cnt-pred 3)
                                       4 (cnt-pred 4)
                                       8 (cnt-pred 7)
                                       9 (cnt-mask-cnt-pred 6 4 2)
                                       2 (cnt-mask-cnt-pred 5 4 3)
                                       3 (cnt-mask-cnt-pred 5 1 3)
                                       5 (cnt-mask-cnt-pred 5 1 4)
                                       0 (cnt-mask-cnt-pred 6 1 4)
                                       6 (cnt-mask-cnt-pred 6 1 5)]))]
    (assoc puzzle
           :solution (into {}
                           (map (fn [[k v]]
                                  [v k]))
                           (:solution solution)))))

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
       (map (comp number solution))
       (apply +)))

(def input "day/08/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  (solution-2 (file/read input)))
