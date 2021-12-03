(ns ^:lib aoc.convert-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc.convert :as sut]))

(deftest test-stol
  (is (= 42 (sut/stol "42"))))

(deftest test-btol
  (is (= 5 (sut/btol "0101"))))
