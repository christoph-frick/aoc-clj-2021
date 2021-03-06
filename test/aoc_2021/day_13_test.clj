(ns ^:day-13 aoc-2021.day-13-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-13 :as sut]
            [aoc.convert :as c]))

(def test-data
  (c/strip-margin
   "6,10
   |0,14
   |9,10
   |0,3
   |10,4
   |4,11
   |6,0
   |6,12
   |4,1
   |0,13
   |10,12
   |3,4
   |3,0
   |8,4
   |1,10
   |2,14
   |8,10
   |9,0
   |
   |fold along y=7
   |fold along x=5"))

(deftest test-parse
  (is (= {:coords #{[6 10]
                    [0 14]
                    [9 10]
                    [0 3]
                    [10 4]
                    [4 11]
                    [6 0]
                    [6 12]
                    [4 1]
                    [0 13]
                    [10 12]
                    [3 4]
                    [3 0]
                    [8 4]
                    [1 10]
                    [2 14]
                    [8 10]
                    [9 0]}
          :instrs [[:y 7]
                   [:x 5]]}
         (sut/parse test-data))))

(deftest test-solution-1
  (is (= 17 (sut/solution-1 test-data))))

(deftest test-part-1
  (is (= 675 (sut/part-1))))

(deftest test-part-2
  (is (=
       (c/strip-margin
        "#..#.####.#..#.#..#.####.####...##.####
        |#..#....#.#.#..#..#.#....#.......#....#
        |####...#..##...####.###..###.....#...#.
        |#..#..#...#.#..#..#.#....#.......#..#..
        |#..#.#....#.#..#..#.#....#....#..#.#...
        |#..#.####.#..#.#..#.#....####..##..####")
       (sut/part-2))))
