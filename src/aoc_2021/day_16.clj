(ns aoc-2021.day-16
  (:require [aoc.file :as file]
            [aoc.convert :as c]
            [aoc.util :as u]
            [clojure.string :as str]
            [clojure.walk :as walk]))

(def lut
  {\0 "0000"
   \1 "0001"
   \2 "0010"
   \3 "0011"
   \4 "0100"
   \5 "0101"
   \6 "0110"
   \7 "0111"
   \8 "1000"
   \9 "1001"
   \A "1010"
   \B "1011"
   \C "1100"
   \D "1101"
   \E "1110"
   \F "1111"})

(defn hex-to-bin
  [s]
  (str/join (map lut s)))

(def read split-at)

(defn to-nr
  [chars]
  (c/btol (str/join chars)))

(declare parse)

(defn group-end?
  [c]
  (= c \0))

(defn parse-literal
  [s]
  (loop [[[group-marker & group] s] (read 5 s)
         result []]
    (let [result' (into result group)]
      (if (group-end? group-marker)
        [{:literal (to-nr result')} s]
        (recur (read 5 s) result')))))

(defn parse-arguments-by-length
  [s]
  (let [[len-bits s] (read 15 s)
        len (to-nr len-bits)
        [args-s s] (read len s)]
    (loop [args-s args-s
           args []]
      (if (seq args-s)
        (let [[arg args-s] (parse args-s)]
          (recur args-s (conj args arg)))
        [{:args args} s]))))

(defn parse-arguments-by-count
  [s]
  (let [[cnt-bits s] (read 11 s)
        cnt (to-nr cnt-bits)]
    (loop [n cnt
           s s
           args []]
      (if (pos? n)
        (let [[arg s] (parse s)]
          (recur (dec n) s (conj args arg)))
        [{:args args} s]))))

(def argument-parsers
  {\0 parse-arguments-by-length
   \1 parse-arguments-by-count})

(defn parse-operator
  [s]
  (let [[length-type s] (read 1 s)
        argument-parser (get argument-parsers (first length-type))]
    (argument-parser s)))

(def parsers
    {4 parse-literal})

(defn parser
  [type]
  (get parsers type parse-operator))

(defn parse
  [s]
  (let [[version s] (read 3 s)
        [type-id s] (read 3 s)
        type (to-nr type-id)
        parser (parser type)]
    (let [[result s] (parser s)]
      [(into {:version (to-nr version)
              :type type}
             result)
       s])))

(defn on-node
  [f]
  (fn [node]
    (if (map? node)
      (f node)
      node)))

(defn solution
  [node-fn s]
  (->> s
       hex-to-bin
       parse
       first
       (walk/postwalk (on-node node-fn))))

(defn sum-version
  [node]
  (apply + (get node :version 0) (get node :args ())))

(defn solution-1
  [s]
  (solution sum-version s))

(defn to-pred
  [pred]
  (fn [a b]
    (if (pred a b) 1 0)))

(defn to-op
  [f]
  (fn
    [{:keys [args]}]
    (apply f args)))

(def ops
  (into
   {4 :literal}
   (map (u/*** identity to-op))
   {0 +
    1 *
    2 min
    3 max
    5 (to-pred >)
    6 (to-pred <)
    7 (to-pred =)}))

(defn op-dispatch
  [{:keys [type] :as node}]
  ((ops type) node))

(defn solution-2
  [s]
  (solution op-dispatch s))
