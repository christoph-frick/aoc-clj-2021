(ns aoc-2021.day-02
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn pos
  ([]
   (pos 0 0))
  ([x y]
   {:x x :y y}))

(defn forward
  [pos ofs]
  (update pos :x + ofs))

(defn up
  [pos ofs]
  (update pos :y - ofs))

(defn down
  [pos ofs]
  (update pos :y + ofs))

(def instrs
  {"forward" forward
   "up" up
   "down" down})

(defn parse-step
  [s]
  (let [[_ instr ofs] (re-find #"(\w+) (\d+)" s)]
    [instr (c/stol ofs)]))

(defn step
  [pos [instr ofs]]
  ((instrs instr) pos ofs))

(defn run
  ([steps] (run steps (pos)))
  ([steps pos]
   (reduce step pos steps)))

(def input "day/02/input.txt")

(defn part-1
  []
  (let [{:keys [x y]} (->> input
                           (file/read-lines)
                           (map parse-step)
                           (run))]
    (* x y)))

(defn part-2
  []
  nil)
