(ns ^:day-11 aoc-2021.day-11-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-11 :as sut]
            [aoc.pos2d :as pos]
            [clojure.string :as str]))

(def test-data
    "5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526")

(deftest test-parse
  (is (= {[0 0] 1
          [1 0] 2
          [0 1] 3
          [1 1] 4}
         (sut/parse "12\n34"))))

(deftest test-step1
  (are [result value] (= result (sut/step1 {[0 0] value} [0 0]))
    [{[0 0] 1}
     []]
    0

    [{[0 0] 2}
     []]
    1

    [{[0 0] 10}
     [[-1 -1] [0 -1] [1 -1]
      [-1 0] [1 0]
      [-1 1] [0 1] [1 1]]]
    9))

(defn fmt
  [m]
  (str/join "\n"
            (map (comp str/join vals)
                 (vals (pos/coords-to-sorted-nested m)))))

(deftest test-step
  (are [input result] (= result (-> input sut/parse sut/step first fmt))
    "11111
19991
19191
19991
11111"
    "34543
40004
50005
40004
34543"))

(deftest test-run
  (are [flashes steps] (= flashes (-> test-data sut/parse (sut/run steps) second))
    204 10
    1656 100))

(deftest test-part-1
  (is (= 1644 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
