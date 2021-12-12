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

(defprotocol BlockingStrategy
  (init [strategy])
  (childs [strategy tree blocked node]))

(defrecord SingleVisitBlockingStrategy []
  BlockingStrategy
  (init [_]
    #{"start"})
  (childs [_ tree blocked node]
    (let [blocked' (if (small? node) (conj blocked node) blocked)]
      (into []
            (comp
             (remove blocked')
             (map (fn [cn]
                    [cn blocked'])))
            (tree node)))))

(defrecord OneDoubleAllowedVisitBlockingStrategy []
  BlockingStrategy
  (init [_]
    {:once nil
     :blocked #{"start"}})
  (childs [_ tree {:keys [once blocked]} node]
    (let [; if there is no previous once and this one is not blocked and eligible
          ; then make it the new once
          once' (if (and (nil? once)
                         (not (blocked node))
                         (small? node))
                  node
                  once)
          blocked' (if (small? node)
                     (conj blocked node)
                     blocked)]
      (into []
            (comp
             (remove blocked')
             (mapcat (fn [cn]
                       (let [ret [[cn
                                   ; default branch is to keep the old once and the new blocked
                                   {:once once
                                    :blocked blocked'}]]]
                         ; at split, also add the new just picked once and remove it from blocked
                         (if (not= once once')
                           (conj ret [cn
                                      {:once once'
                                       :blocked (disj blocked' once')}])
                           ret)))))
            (tree node)))))

(defn end?
  [n]
  (= n "end"))

(defn breadth-first-all
  [strategy adj-map]
  (loop [q (queue {:node "start"
                   :path []
                   :blocked (init strategy)})
         results #{}]
    (if-let [{:keys [node path blocked]} (peek q)]
      (let [q' (pop q)
            path' (conj path node)]
        (if (end? node)
          (recur q' (conj results path'))
          (let [cs (childs strategy adj-map blocked node)]
            (if (seq cs)
              (recur (into q'
                           (map (fn [[cn blocked']]
                                  {:node cn
                                   :path path'
                                   :blocked blocked'}))
                           cs)
                     results)
              (recur q' results)))))
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
  (solution (->SingleVisitBlockingStrategy) s))

(defn solution-2
  [s]
  (solution (->OneDoubleAllowedVisitBlockingStrategy) s))

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
