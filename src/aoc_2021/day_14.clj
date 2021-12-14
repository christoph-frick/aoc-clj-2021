(ns aoc-2021.day-14
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str]))

(defn parse-rule
  [s]
  (let [[_ rule replacement] (re-find #"(\w+) -> (\w)" s)]
    {(vec rule) (first replacement)}))

(defn parse
  [s]
  (let [[template rules] (c/split-groups s)]
    {:template (seq template)
     :rules (into {}
                  (map parse-rule)
                  (str/split-lines rules))}))

(defn transform1
  [rules [a _ :as pair]]
  (if (contains? rules pair)
    [a (rules pair)]
    pair))

(defn step
  [{:keys [template rules] :as state}]
  (assoc state
         :template
         (into []
               (mapcat (partial transform1 rules))
               (partition-all 2 1 template))))

(defn run
  [state n]
  (->> state
       (iterate step)
       (drop n)
       (first)))

(defn calc-day-1
  [{:keys [template]}]
  (let [fs (frequencies template)
        min (apply min (vals fs))
        max (apply max (vals fs))]
    (- max min)))

(defn solution-1
  [s]
  (-> s
      (parse)
      (run 10)
      (calc-day-1)))

(defn solution-2
  [s]
  :TODO)
