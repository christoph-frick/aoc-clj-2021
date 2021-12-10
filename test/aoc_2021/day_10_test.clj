(ns ^:day-10 aoc-2021.day-10-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-10 :as sut]
            [aoc.file :as file]))

(def data (file/read "day/10/test-input.txt"))

(deftest test-parse
  (is (= 10 (count (sut/parse data)))))

(deftest test-opening?
  (are [result input] (= result (sut/opening? input))
    true \(
    false \)))

(deftest test-check-ok
  (are [s] (= [:ok] (sut/check s))
    "[]"
    "([])"
    "{()()()}"
    "<([{}])>"
    "[<>({}){}[([])<>]]"
    "(((((((((())))))))))"))

(deftest test-check
  (is (= [[:incomplete [\} \} \] \] \) \} \) \]]]
          [:incomplete [\) \} \> \] \} \)]]
          [:illegal \}]
          [:incomplete [\} \} \> \} \> \) \) \) \)]]
          [:illegal \)]
          [:illegal \]]
          [:incomplete [\] \] \} \} \] \} \] \} \>]]
          [:illegal \)]
          [:illegal \>]
          [:incomplete [\] \) \} \>]]]
         (map sut/check (sut/parse data)))))

(deftest test-solution-1
  (is (= 26397 (sut/solution-1 data))))

(deftest test-rate-incomplete
  (is (= 288957 (sut/rate (sut/check "[({(<(())[]>[[{[]{<()<>>")))))

(deftest test-solution-2
  (is (= 288957 (sut/solution-2 data))))

(deftest test-part-1
  (is (= 168417 (sut/part-1))))

(deftest test-part-2
  (is (= 2802519786 (sut/part-2))))
