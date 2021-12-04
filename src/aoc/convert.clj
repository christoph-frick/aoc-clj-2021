(ns aoc.convert
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(defn stol
    "String to long base 10"
    [s]
    (Long/parseLong s))

(defn btol
    "String to long base 2"
    [s]
    (Long/parseLong s 2))

(defn split-groups
    [s]
    (str/split (str/trim s) #"\n\n"))

(defn read-array
  [s]
  (edn/read-string (str "[" s "]")))
