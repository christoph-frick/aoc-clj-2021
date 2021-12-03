(ns aoc.convert)

(defn stol
    "String to long base 10"
    [s]
    (Long/parseLong s))

(defn btol
    "String to long base 2"
    [s]
    (Long/parseLong s 2))
