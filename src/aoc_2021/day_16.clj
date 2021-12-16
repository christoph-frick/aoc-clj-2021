(ns aoc-2021.day-16
  (:require [aoc.file :as file]
            [aoc.convert :as c]
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

(defn solution-1
  [s]
  (->> s
       hex-to-bin
       parse
       first
       (walk/postwalk (fn [node]
                        (if (map? node)
                          (apply + (get node :version 0) (get node :args ()))
                          node)))))

(defn solution-2
  [s]
  :TODO)
