(ns aoc.util)

(defn reduce-times
  [reducing-fn init steps]
  (reduce (fn [acc _] (reducing-fn acc))
          init
          (range steps)))

(defn ***
  [& fs]
  (fn [xs]
    (vec (map #(% %2) fs xs))))

