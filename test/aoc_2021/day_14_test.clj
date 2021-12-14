(ns ^:day-14 aoc-2021.day-14-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-14 :as sut]
            [aoc.convert :as c]
            [aoc.file :as f]
            [clojure.string :as str]))

(def test-input (f/read "day/14/test.txt"))
(def input (f/read "day/14/input.txt"))

(deftest test-parse
  (is (=
       {:template [\N \N \C \B]
        :rules {[\B \B] \N,
                [\B \C] \B,
                [\B \H] \H,
                [\B \N] \B,
                [\C \B] \H,
                [\C \C] \N,
                [\C \H] \B,
                [\C \N] \C,
                [\H \B] \C,
                [\H \C] \B,
                [\H \H] \N,
                [\H \N] \C,
                [\N \B] \B,
                [\N \C] \B,
                [\N \H] \C,
                [\N \N] \C}}
       (sut/parse test-input))))

(deftest test-run
  (are [f result steps] (= result (-> test-input (sut/parse) (sut/run steps) :template f))
    str/join "NCNBCHB" 1
    str/join "NBCCNBBBCBHCB" 2
    str/join "NBBBCNCCNBBNBNBBCHBHHBCHB" 3
    str/join "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB" 4
    count 97 5
    count 3073 10))

(deftest test-solution-1
  (are [result s] (= result (sut/solution-1 s))
    1588 test-input
    3058 input))

(deftest test-solution-2
  (are [result s] (= result (sut/solution-2 s))
    2188189693529 test-input
    3447389044530 input))
