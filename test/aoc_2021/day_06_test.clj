(ns ^:day-06 aoc-2021.day-06-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-06 :as sut]))

(deftest test-setup
  (is (= {4 1, 3 2, 2 1, 1 1} (sut/setup [3,4,3,1,2]))))

(deftest test-step
  (are [result initial] (= result (-> initial sut/setup sut/step))
    {3 1, 2 2, 1 1, 0 1} [3,4,3,1,2]
    {8 1, 6 1, 2 1, 1 2, 0 1} [2,3,2,0,1]))

(deftest test-run-18
  (are [result initial days] (= (sut/setup result) (->> initial (sut/setup) (sut/run days)))
    [6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8] [3,4,3,1,2] 18))

(deftest test-count-fishes
  (is (= 26 (-> [6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8] (sut/setup) (sut/count-fishes)))))

(deftest test-solution-1
  (are [result days] (= result (sut/solution-1 [3,4,3,1,2] days))
    26 18
    5934 80))

(deftest test-part-1
  (is (= 42 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
