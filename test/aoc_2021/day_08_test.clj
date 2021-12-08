(ns ^:day-08 aoc-2021.day-08-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-08 :as sut]))

(def input1
    "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

(def input
    "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"
    )

(deftest test-parse-line
  (let [parsed (sut/parse-line input1)]
    (is (= #{:inputs :number} (-> parsed keys set)))
    (is (= 10 (-> parsed :inputs count)))
    (is (= 4 (-> parsed :number count)))))

(deftest test-parse
  (is (= 10 (-> input sut/parse count))))

(deftest test-solution-1
  (is (= 26 (sut/solution-1 input))))

(deftest test-part-1
  (is (= 367 (sut/part-1))))

(deftest ^:kaocha/pending test-part-2
  (is (= 42 (sut/part-2))))
