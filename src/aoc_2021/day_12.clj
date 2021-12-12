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

(defprotocol DescendStrategy
  (init [strategy])
  (childs [strategy state tree node]))

(defn block-small
  [blocked node]
  (if (small? node)
    (conj blocked node)
    blocked))

(defn select-children
  [remove-fn child-node-transform tree node]
  (into []
        (comp
         (remove remove-fn)
         child-node-transform)
        (tree node)))

(defrecord SingleVisitDescendStrategy []
  DescendStrategy
  (init [_]
    #{"start"})
  (childs [_ blocked tree node]
    (let [blocked' (block-small blocked node)]
      (select-children
       blocked'
       (map (fn [cn]
              [cn blocked']))
       tree
       node))))

(defn one-double-allowed-cfg
  [once blocked]
  {:once once
   :blocked blocked})

(defrecord OneDoubleVisitAllowedDescendStrategy []
  DescendStrategy
  (init [_]
    (one-double-allowed-cfg nil #{"start"}))
  (childs [_ {:keys [once blocked]} tree node]
    (let [; if there is no previous once and this one is not blocked and eligible
          once' (if (and (nil? once)
                         (not (blocked node))
                         (small? node))
                  node
                  once)]
      (select-children
       blocked
       (mapcat (fn [cn]
                 (conj
                  ; at split, also add the new just picked once, but keep the old blocked
                  (when (not= once once')
                    [[cn (one-double-allowed-cfg once' blocked)]])
                  ; default branch is to keep the old once and the new blocked
                  [cn (one-double-allowed-cfg once (block-small blocked node))])))
       tree
       node))))

(defn end?
  [n]
  (= n "end"))

(defn breadth-first-node
  [strategy-state path node]
  {:strategy-state strategy-state
   :path path
   :node node})

(defn breadth-first-all
  [strategy adj-map]
  (loop [q (queue (breadth-first-node (init strategy) [] "start"))
         results #{}]
    (if-let [{:keys [node path strategy-state]} (peek q)]
      (let [q' (pop q)
            path' (conj path node)]
        (if (end? node)
          (recur q' (conj results path'))
          (if-let [cs (seq (childs strategy strategy-state adj-map node))]
            (recur (into q'
                         (map (fn [[cn strategy-state']]
                                (breadth-first-node strategy-state' path' cn)))
                         cs)
                   results)
            (recur q' results))))
      results)))

(defn solution
  [strategy s]
  (->> s
       (parse)
       (to-adj-map)
       (breadth-first-all strategy)
       (count)))

(defn solution-1
  [s]
  (solution (->SingleVisitDescendStrategy) s))

(defn solution-2
  [s]
  (solution (->OneDoubleVisitAllowedDescendStrategy) s))

(def input "day/12/input.txt")

(defn part-1
  []
  (-> input
      (file/read)
      (solution-1)))

(defn part-2
  []
  (-> input
      (file/read)
      (solution-2)))
