(ns ^:day-02 aoc-2021.day-02-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-02 :as sut]
            [clojure.string :as str]))

(deftest test-parse-step
  (are [result step] (= result (sut/parse-step step))
    ["forward" 5] "forward 5"))

(deftest test-step
  (are [result step] (= result (->> step sut/parse-step (sut/step (sut/pos))))
    {:x 1 :y 0} "forward 1"))

(def input
    "forward 5
down 5
forward 8
up 3
down 8
forward 2")

(deftest test-run
  (is (= {:x 15 :y 10} (sut/run (map sut/parse-step (str/split-lines input))))))

(deftest test-part-1
  (is (= 2091984 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
