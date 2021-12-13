(ns aoc.convert
  (:require [clojure.string :as str]
            [clojure.edn :as edn])
  (:import [java.util.regex Pattern]))

(defn stol
    "String to long base 10"
    [s]
    (Long/parseLong s))

(defn ctol
  "Character to long base 10"
  [c]
  (Character/digit c 10))

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

(defn strip-margin
  ([s]
   (strip-margin s "|"))
  ([s margin]
   (str/replace s
                (re-pattern (str "(?m)^\\p{javaSpaceChar}+" (Pattern/quote margin)))
                "")))
