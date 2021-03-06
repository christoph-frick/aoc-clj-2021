(ns ^:lib aoc.file-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc.file :as sut]))

(deftest test-read-lines
  (is (= ["a" "b" "c"]
         (sut/read-lines "test.txt"))))

(deftest test-read
  (is (= "a\nb\nc\n" (sut/read "test.txt"))))
