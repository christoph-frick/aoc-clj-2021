(ns ^:lib aoc.convert-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc.convert :as sut]))

(deftest test-stol
  (is (= 42 (sut/stol "42"))))
