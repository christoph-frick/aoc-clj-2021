(ns ^:day-03 aoc-2021.day-03-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-03 :as sut]))
(def test-data
  ["00100"
   "11110"
   "10110"
   "10111"
   "10101"
   "01111"
   "00111"
   "11100"
   "10000"
   "11001"
   "00010"
   "01010"])

(deftest test-multi-frequencies
  (are [result inputs] (= result (sut/multi-frequencies inputs))
    [{\0 5 \1 7}]
    (map #(subs % 0 1) test-data)))

(deftest test-gamma
  (is (= "10110" (sut/gamma test-data))))

(deftest test-epsilon-from-gamma
  (is (= "01001" (sut/epsilon-from-gamma "10110"))))

(deftest test-solution-1
  (is (= 198 (sut/solution-1 test-data))))

(deftest test-part-1
  (is (= 3633500 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
