(ns aoc-2021.day-02
  (:require [aoc.file :as file]
            [aoc.convert :as c]))

(defn state
  ([]
   (state 0 0 0))
  ([x y aim]
   {:x x :y y :aim aim}))

(defn parse-step
  [s]
  (let [[_ instr ofs] (re-find #"(\w+) (\d+)" s)]
    [instr (c/stol ofs)]))

(defn step
  [instrs state [instr ofs]]
  ((instrs instr) state ofs))

(defn run
  ([instrs steps] (run instrs steps (state)))
  ([instrs steps state]
   (reduce (partial step instrs) state steps)))

(def instrs-part-1
  {"forward" (fn forward-1
               [state ofs]
               (update state :x + ofs))
   "up" (fn up-1
          [state ofs]
          (update state :y - ofs))
   "down" (fn down-1
            [state ofs]
            (update state :y + ofs))})

(def instrs-part-2
  {"forward" (fn forward-2
               [{:keys [aim] :as state} ofs]
               (-> state
                   (update :x + ofs)
                   (update :y + (* aim ofs))))
   "up" (fn up-2
          [state ofs]
          (update state :aim - ofs))
   "down" (fn down-2
            [state ofs]
            (update state :aim + ofs))})

(def input "day/02/input.txt")

(defn part
  [instrs]
  (let [{:keys [x y]} (->> input
                           (file/read-lines)
                           (map parse-step)
                           (run instrs))]
    (* x y)))

(defn part-1
  []
  (part instrs-part-1))

(defn part-2
  []
  (part instrs-part-2))
