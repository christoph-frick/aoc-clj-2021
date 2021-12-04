(ns ^:day-04 aoc-2021.day-04-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-04 :as sut]))

(def test-input
    "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7")

(deftest test-parse-board
  (is (=
       {+0 {:drawn? false, :number 0, :x 4, :y 0},
        +1 {:drawn? false, :number 1, :x 0, :y 4},
        +2 {:drawn? false, :number 2, :x 1, :y 1},
        +3 {:drawn? false, :number 3, :x 2, :y 3},
        +4 {:drawn? false, :number 4, :x 3, :y 1},
        +5 {:drawn? false, :number 5, :x 4, :y 3},
        +6 {:drawn? false, :number 6, :x 0, :y 3},
        +7 {:drawn? false, :number 7, :x 4, :y 2},
        +8 {:drawn? false, :number 8, :x 0, :y 1},
        +9 {:drawn? false, :number 9, :x 1, :y 2},
        +10 {:drawn? false, :number 10, :x 1, :y 3},
        +11 {:drawn? false, :number 11, :x 3, :y 0},
        +12 {:drawn? false, :number 12, :x 1, :y 4},
        +13 {:drawn? false, :number 13, :x 1, :y 0},
        +14 {:drawn? false, :number 14, :x 2, :y 2},
        +15 {:drawn? false, :number 15, :x 3, :y 4},
        +16 {:drawn? false, :number 16, :x 3, :y 2},
        +17 {:drawn? false, :number 17, :x 2, :y 0},
        +18 {:drawn? false, :number 18, :x 3, :y 3},
        +19 {:drawn? false, :number 19, :x 4, :y 4},
        +20 {:drawn? false, :number 20, :x 2, :y 4},
        +21 {:drawn? false, :number 21, :x 0, :y 2},
        +22 {:drawn? false, :number 22, :x 0, :y 0},
        +23 {:drawn? false, :number 23, :x 2, :y 1},
        +24 {:drawn? false, :number 24, :x 4, :y 1}}
       (sut/parse-board "22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19"))))

(deftest test-parse-game
  (let [game (sut/parse-game test-input)]
    (is (contains? game :boards))
    (is (= 3 (-> game :boards count)))
    (is (contains? game :numbers))
    (is (= 27 (-> game :numbers count)))))

(deftest test-draw-and-winner
  (let [board (sut/parse-board "14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7")
        numbers [7 4 9 5 11 17 23 2 0 14 21 24]
        run (reduce sut/mark-number board numbers)]
    (is (false? (sut/winner? board)))
    (is (true? (sut/winner? run)))))

(deftest test-run
  (let [game (sut/parse-game test-input)
        [number _] (sut/run game)]
    (is (= 24 number))))

(deftest test-solution-1
  (is (= 4512 (sut/solution-1 test-input))))

(deftest test-part-1
  (is (= 38594 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
