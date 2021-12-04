(ns aoc-2021.day-04
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn parse-board
  [s]
  (let [board (c/read-array s)]
    (into {}
          (for [y (range 5)
                x (range 5)
                :let [number (nth board (+ (* y 5) x))]]
            [number {:number number
                     :x x
                     :y y
                     :drawn? false}]))))

(defn parse-game
  [s]
  (let [lines (c/split-groups s)]
    {:numbers (c/read-array (first lines))
     :boards (mapv parse-board (rest lines))
     :winners []}))

(defn mark-number
  [board number]
  (if (contains? board number)
    (assoc-in board [number :drawn?] true)
    board))

(defn winner?
  [board]
  (let [drawn (filter :drawn? (vals board))
        winning-row? (fn [[_ numbers]] (= 5 (count numbers)))]
    (boolean (some winning-row? (mapcat #(group-by % drawn) [:x :y])))))

(defn run
  [game]
  (loop
   [{:keys [numbers boards] :as game} game]
    (let [[number & numbers'] numbers
          boards' (map #(mark-number % number) boards)
          winner (first (filter winner? boards'))]
      (if winner
        [number winner]
        (recur (assoc game
                      :numbers numbers'
                      :boards boards'))))))

(defn solution-1
  [s]
  (let [game (parse-game s)
        [number winner] (run game)
        non-drawn-numbers (->> winner
                               vals
                               (filter (complement :drawn?))
                               (map :number))]
    (* number (apply + non-drawn-numbers))))

(def input "day/04/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  nil)
