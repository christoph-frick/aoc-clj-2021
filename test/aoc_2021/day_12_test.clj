(ns ^:day-12 aoc-2021.day-12-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-12 :as sut]))

(def test-data
    "start-A
start-b
A-c
A-b
b-d
A-end
b-end")

(deftest test-parse
  (is (=
       [["start" "A"]
        ["start" "b"]
        ["A" "c"]
        ["A" "b"]
        ["b" "d"]
        ["A" "end"]
        ["b" "end"]]
       (sut/parse test-data))))

(deftest test-to-adj-map
  (is (= {"start" #{"A" "b"}
          "A" #{"start" "b" "c" "end"}
          "b" #{"start" "A" "d" "end"}
          "c" #{"A"}
          "d" #{"b"}
          "end" #{"A" "b"}}
         (sut/to-adj-map (sut/parse test-data)))))

(deftest test-small?
  (are [result input] (= result (sut/small? input))
    true "start"
    false "A"
    false "Error"))

(deftest test-block
  (are [result n] (= result (sut/block #{} n))
    #{"start"} "start"
    #{} "A"))

(deftest test-childs
  (are [result tree blocked n] (= result (sut/childs tree blocked n))
    #{"A" "b"} {"start" #{"A" "b"}} #{} "start"
    #{"A"} {"start" #{"A" "b"}} #{"b"} "start"))

(deftest test-breadth-first-all
  (is (=  #{["start" "A" "b" "A" "c" "A" "end"]
            ["start" "A" "b" "A" "end"]
            ["start" "A" "b" "end"]
            ["start" "A" "c" "A" "b" "A" "end"]
            ["start" "A" "c" "A" "b" "end"]
            ["start" "A" "c" "A" "end"]
            ["start" "A" "end"]
            ["start" "b" "A" "c" "A" "end"]
            ["start" "b" "A" "end"]
            ["start" "b" "end"]}
          (-> test-data
              sut/parse
              sut/to-adj-map
              sut/breadth-first-all))))

(deftest test-solution-1
  (are [result tree-config] (= result (sut/solution-1 tree-config))
    10
    test-data

    19
    "dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc"

    226
    "fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW"))

(deftest test-part-1
  (is (= 4707 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
