(ns ^:lib aoc.pos2d-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc.pos2d :as sut]))

(deftest test-add
  (is (= [42 666] (sut/add [21 333] [21 333]))))

(deftest test-neighbours
  (are [result f pos] (= (set result) (set (f pos)))
    [[43 666] [41 666]
     [42 667] [42 665]] sut/neighbours-4 [42 666]
    [[43 666] [41 666]
     [42 667] [42 665]
     [41 665] [41 667]
     [43 665] [43 667]] sut/neighbours-8 [42 666]))

(deftest test-coord-map
  (is (= {:width 2
          :height 2
          :coords {[0 0] \a
                   [1 0] \b
                   [0 1] \c
                   [1 1] \d}} (sut/coord-map ["ab" "cd"]))))
