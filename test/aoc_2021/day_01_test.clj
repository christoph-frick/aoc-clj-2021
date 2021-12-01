(ns ^:day-01 aoc-2021.day-01-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-01 :as sut]))

(def test-data
  [199
   200
   208
   210
   200
   207
   240
   269
   260
   263])

(deftest test-count-increasing
  (is (= 7 (sut/count-increasing test-data))))

(deftest test-solution
  (are [f r] (= r (f))
    sut/part-1 1548
    sut/part-2 nil))
