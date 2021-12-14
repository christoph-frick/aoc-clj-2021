(ns ^:lib aoc.util-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc.util :as sut]))

(deftest test-reduce-times
  (is (= 3 (sut/reduce-times inc 0 3))))

(deftest test-***
  (is (= [[:a 1] [:b 2]] (map (sut/*** keyword inc) [["a" 0] ["b" 1]]))))
