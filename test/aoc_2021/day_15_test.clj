(ns ^:day-15 aoc-2021.day-15-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-15 :as sut]
            [aoc.convert :as c]
            [aoc.file :as f]))

(def test-input (f/read "day/15/test.txt"))
(def input (f/read "day/15/input.txt"))

(deftest test-parse
  (let [{:keys [width height coords]} (sut/parse test-input)]
    (is (= 1 (coords [0 0]) (coords [9 9])))
    (is (= 10 width height))))

(deftest test-solution-1
  (are [result s] (= result (sut/solution-1 s))
    40 test-input
    769 input))

(deftest ^:kaocha/pending test-solution-2
  (are [result s] (= result (sut/solution-2 s))
    :FIXME test-input
    #_#_
    :FIXME input))
