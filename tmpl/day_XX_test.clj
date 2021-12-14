(ns ^:day-XX aoc-2021.day-XX-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-XX :as sut]
            [aoc.convert :as c]
            [aoc.file :as f]))

(def test-input (f/read "day/XX/test.txt"))
(def input (f/read "day/XX/input.txt"))

(deftest ^:kaocha/pending test-parse
  (is (= :FIXME (sut/parse test-input))))

(deftest ^:kaocha/pending test-solution-1
  (are [result s] (= result (sut/solution-1 s))
    :FIXME test-input
    #_#_
    :FIXME input))

(deftest ^:kaocha/pending test-solution-2
  (are [result s] (= result (sut/solution-2 s))
    :FIXME test-input
    #_#_
    :FIXME input))
