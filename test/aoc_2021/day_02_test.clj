(ns ^:day-02 aoc-2021.day-02-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-02 :as sut]
            [clojure.string :as str]))

(deftest test-parse-step
  (are [result step] (= result (sut/parse-step step))
    ["forward" 5] "forward 5"))

(deftest test-step
  (are [result step] (= result (->> step sut/parse-step (sut/step sut/instrs-part-1 (sut/state))))
    {:x 1 :y 0 :aim 0} "forward 1"))

(def input
    "forward 5
down 5
forward 8
up 3
down 8
forward 2")

(deftest test-run
  (are [result instrs] (= result (sut/run instrs (map sut/parse-step (str/split-lines input))))
    {:x 15 :y 10 :aim 0} sut/instrs-part-1
    {:x 15 :y 60 :aim 10} sut/instrs-part-2))


(deftest test-part-1
  (is (= 2091984 (sut/part-1))))

(deftest test-part-2
  (is (= 2086261056 (sut/part-2))))
