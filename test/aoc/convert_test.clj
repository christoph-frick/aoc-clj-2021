(ns ^:lib aoc.convert-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc.convert :as sut]))

(deftest test-stol
  (is (= 42 (sut/stol "42"))))

(deftest test-btol
  (is (= 5 (sut/btol "0101"))))

(deftest test-ctol
  (is (= [1 2 3] (map sut/ctol "123"))))

(deftest test-split-groups
  (is (= ["a" "b"] (sut/split-groups "a\n\nb\n"))))

(deftest test-read-array
  (are [result s] (= result (sut/read-array s))
    [1 2 3] "1 2 3"
    [1 2 3] "1,2,3"))

(deftest test-strip-margin
  (is (= "test
test
test" (sut/strip-margin "test
                        |test
                        |test"))))
