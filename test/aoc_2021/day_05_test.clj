(ns ^:day-05 aoc-2021.day-05-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-05 :as sut]))

(def test-data ["0,9 -> 5,9"
                "8,0 -> 0,8"
                "9,4 -> 3,4"
                "2,2 -> 2,1"
                "7,0 -> 7,4"
                "6,4 -> 2,0"
                "0,9 -> 2,9"
                "3,4 -> 1,4"
                "0,0 -> 8,8"
                "5,5 -> 8,2"])

(deftest test-parse-line
  (is (= [[0 9] [5 9]] (sut/parse-line "0,9 -> 5,9"))))

(deftest test-solution-1
  (is (= 5 (sut/solution sut/hor-or-vert? test-data))))

(deftest test-solution-2
  (is (= 12 (sut/solution identity test-data))))

(deftest test-part-1
  (is (= 6113 (sut/part-1))))

(deftest test-part-2
  (is (= 20373 (sut/part-2))))
