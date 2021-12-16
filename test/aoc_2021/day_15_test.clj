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

(deftest test-times-5
  (let [t5 (-> test-input sut/parse sut/times-5)]
    (is (= {:width 50 :height 50} (select-keys t5 [:width :height])))
    (is (= (* 50 50) (-> t5 :coords count)))
    (is (= 9 (-> t5 :coords (get [49 49])))))
  (is (= [8 9 1 2 3
          9 1 2 3 4
          1 2 3 4 5
          2 3 4 5 6
          3 4 5 6 7]
         (->> "8" sut/parse sut/times-5 :coords (into (sorted-map)) vals))))

(deftest test-solution-2
  (are [result s] (= result (sut/solution-2 s))
    315 test-input
    #_#_
    ; WRONG!
    2970 input))
