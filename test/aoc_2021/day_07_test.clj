(ns ^:day-07 aoc-2021.day-07-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-07 :as sut]))

(def test-data [16,1,2,0,4,2,7,1,2,14])

(deftest test-triangular
  (are [result n] (= result (sut/triangular 0 n))
    1 1
    3 2
    6 3))

(deftest test-optimize1
  (are [result cost-fn center] (= result (sut/optimize1 cost-fn test-data center))
    37 sut/dist 2
    41 sut/dist 1
    39 sut/dist 3
    168 sut/triangular 5
    206 sut/triangular 2))

(deftest test-optimize
  (are [center fuel cost-fn] (= [center fuel] (sut/optimize cost-fn test-data))
    2 37 sut/dist
    5 168 sut/triangular))

(deftest test-part-1
  (is (= 340052 (sut/part-1))))

(deftest test-part-2
  (is (= 92948968 (sut/part-2))))
