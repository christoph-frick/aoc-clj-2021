(ns aoc-2021.day-14
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [aoc.util :as u]
            [clojure.string :as str]))

(defn parse-rule
  [s]
  ((u/*** vec first) (str/split s #" -> ")))

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

(defn calc
  [fs]
  (apply - (apply (juxt max min) (vals fs))))

(defn solution-1
  [s]
  (-> s
      (parse)
      (run 10)
      :template
      (frequencies)
      (calc)))

(def pairs (partial partition 2 1))

(defn add
  [m k n]
  (update m k (fnil + 0) n))

(defn step-2
  [rules [counts tuples]]
  (reduce (fn [[counts tuples] [[a b :as tuple] c]]
            (let [rule (rules tuple)]
              [(add counts rule c)
               (-> tuples
                   (add [a rule] c)
                   (add [rule b] c))]))
          [counts
           {}]
          tuples))

(defn run-2
  [{:keys [rules template]} steps]
  (u/reduce-times (partial step-2 rules)
                  [(frequencies template)
                   (frequencies (pairs template))]
                  steps))

(defn solution-2
  [s]
  (-> s
      (parse)
      (run-2 40)
      (first)
      (calc)))
