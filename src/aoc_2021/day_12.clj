(ns aoc-2021.day-12
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [clojure.string :as str])
  (:import [clojure.lang PersistentQueue]))

(defn parse-line
  [s]
  (str/split s #"-" 2))

(defn parse
  [s]
  (map parse-line (str/split-lines s)))

(defn to-adj-map
  [parsed]
  (apply merge-with into
         (mapcat (fn [[k v]]
                   [(hash-map k #{v})
                    (hash-map v #{k})])
                 parsed)))

(defn small?
  [s]
  (= s (str/lower-case s)))

(defn queue
  [init]
  (conj PersistentQueue/EMPTY init))

(defn block
  [blocked n]
  (if (small? n)
    (conj blocked n)
    blocked))

(defn childs
  [tree blocked n]
  (into #{}
        (remove blocked)
        (tree n #{})))

(defn end?
  [n]
  (= n "end"))

(defn breadth-first-all
  [adj-map]
  (loop [q (queue {:node "start"
                   :path []
                   :blocked #{"start"}})
         results #{}]
    (if-let [{:keys [node path blocked]} (peek q)]
      (let [q' (pop q)
            blocked' (block blocked node)
            path' (conj path node)]
        (if (end? node)
          (recur q' (conj results path'))
          (let [cs (childs adj-map blocked' node)]
            (if (seq cs)

              (recur (into q'
                           (map (fn [cn] {:node cn
                                          :path path'
                                          :blocked blocked'}))
                           cs)
                     results)
              (recur q' results)))))
      results)))

(defn solution-1
  [s]
  (-> s
      (parse)
      (to-adj-map)
      (breadth-first-all)
      (count)))

(def input "day/12/input.txt")

(defn part-1
  []
  (-> input
      (file/read)
      (solution-1)))

(defn part-2
  []
  nil)
