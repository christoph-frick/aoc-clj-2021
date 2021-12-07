(ns ^:day-07 aoc-2021.day-07-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-07 :as sut]))

(def test-data [16,1,2,0,4,2,7,1,2,14])

(deftest test-optimize1
  (are [result center] (= result (sut/optimize1 test-data center))
    37 2
    41 1
    39 3))

(deftest test-optimize
  (is (= [2 37] (sut/optimize test-data))))

(deftest test-part-1
  (is (= 340052 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
