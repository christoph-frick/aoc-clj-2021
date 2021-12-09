(ns ^:day-09 aoc-2021.day-09-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-09 :as sut]))

(def data
  "2199943210
3987894921
9856789892
8767896789
9899965678")

(deftest test-parse
  (is (= [[2 1 9 9 9 4 3 2 1 0]
          [3 9 8 7 8 9 4 9 2 1]
          [9 8 5 6 7 8 9 8 9 2]
          [8 7 6 7 8 9 6 7 8 9]
          [9 8 9 9 9 6 5 6 7 8]]
         (sut/parse data))))

(deftest test-setup
  (let [{:keys [width height points]} (sut/setup (sut/parse data))]
    (is (= 5 height))
    (is (= 10 width))
    (is (= (* 5 10) (count points)))
    (is (contains? points [0 0]))
    (is (= (->> data (re-seq #"\d") (frequencies)) (frequencies (map str (vals points)))))))

(deftest test-neightbours
  (is (= #{[41 666]
           [43 666]
           [42 665]
           [42 667]}
         (set (sut/neighbours [42 666])))))

(deftest test-find-low-points
  (is (= {[1 0] 1,
          [2 2] 5,
          [6 4] 5,
          [9 0] 0}
         (-> data sut/parse sut/setup sut/find-low-points))))

(deftest test-solution-1
  (is (= 15 (sut/solution-1 data))))

(deftest test-find-basin
  (let [m (sut/setup (sut/parse data))]
    (is (= #{[0 0] [1 0] [0 1]} (sut/find-basin m [1 0])))))

(deftest test-solution-2
  (is (= 1134 (sut/solution-2 data))))

(deftest test-part-1
  (is (= 541 (sut/part-1))))

(deftest test-part-2
  (is (= 847504 (sut/part-2))))
