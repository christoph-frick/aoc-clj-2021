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
  (reduce
   (fn step [{:keys [boards winners] :as game} number]
     (let [boards' (map #(mark-number % number) boards)
           winners-and-losers (group-by winner? boards')
           [new-winners losers] (map #(get winners-and-losers % []) [true false])
           game' (assoc game
                        :boards losers
                        :winners (into winners (map (partial vector number) new-winners)))]
       (if (empty? losers)
         (reduced game')
         game')))
   game
   (:numbers game)))

(defn non-drawn-numbers
  [board]
  (->> board
       vals
       (filter (complement :drawn?))
       (map :number)))

(defn solution
  [picker-fn s]
  (let [game (run (parse-game s))
        [number winner] (-> game :winners picker-fn)]
    (* number (apply + (non-drawn-numbers winner)))))

(defn solution-1
  [s]
  (solution first s))

(defn solution-2
  [s]
  (solution last s))

(def input "day/04/input.txt")

(defn part-1
  []
  (solution-1 (file/read input)))

(defn part-2
  []
  (solution-2 (file/read input)))
