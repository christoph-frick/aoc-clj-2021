(ns ^:day-16 aoc-2021.day-16-test
  (:require [clojure.test :refer [deftest testing is are]]
            [aoc-2021.day-16 :as sut]
            [aoc.convert :as c]
            [aoc.file :as f]))

(def input (f/read "day/16/input.txt"))

(def t1 "D2FE28")
(def t2 "38006F45291200")
(def t3 "EE00D40C823060")

(deftest test-hex-to-bin
  (are [result input] (= result (sut/hex-to-bin input))
    "110100101111111000101000" t1
    "00111000000000000110111101000101001010010001001000000000" t2
    "11101110000000001101010000001100100000100011000001100000" t3))

(deftest test-parse
  (are [result input] (= result (-> input sut/hex-to-bin sut/parse first))
    {:version 6
     :type 4
     :literal 2021}
    t1

    {:version 1
     :type 6
     :args [{:version 6 :type 4 :literal 10}
            {:version 2 :type 4 :literal 20}]}
    t2

    {:version 7
     :type 3
     :args [{:version 2 :type 4 :literal 1}
            {:version 4 :type 4 :literal 2}
            {:version 1 :type 4 :literal 3}]}
    t3

    {:args [{:args
             [{:args [{:version 6 :type 4 :literal 15}]
               :type 2
               :version 5}]
             :type 2
             :version 1}]
     :type 2
     :version 4}
    "8A004A801A8002F478"

    {:args [{:args
             [{:literal 10, :type 4, :version 0}
              {:literal 11, :type 4, :version 5}],
             :type 0,
             :version 0}
            {:args
             [{:literal 12, :type 4, :version 0}
              {:literal 13, :type 4, :version 3}],
             :type 0,
             :version 1}],
     :type 0,
     :version 3}
    "620080001611562C8802118E34"

    {:args
     [{:args
       [{:literal 10, :type 4, :version 0}
        {:literal 11, :type 4, :version 6}],
       :type 0,
       :version 0}
      {:args
       [{:literal 12, :type 4, :version 7}
        {:literal 13, :type 4, :version 0}],
       :type 0,
       :version 4}],
     :type 0,
     :version 6}
    "C0015000016115A2E0802F182340"

    {:args
     [{:args
       [{:args
         [{:literal 6 :type 4 :version 7}
          {:literal 6 :type 4 :version 6}
          {:literal 12 :type 4 :version 5}
          {:literal 15 :type 4 :version 2}
          {:literal 15 :type 4 :version 2}]
         :type 0
         :version 3}]
       :type 0
       :version 1}]
     :type 0
     :version 5}
    "A0016C880162017C3686B18A3D4780"))

(deftest test-solution-1
  (are [result s] (= result (sut/solution-1 s))
    16 "8A004A801A8002F478"
    12 "620080001611562C8802118E34"
    23 "C0015000016115A2E0802F182340"
    31 "A0016C880162017C3686B18A3D4780"
    993 input))

#_(deftest ^:kaocha/pending test-solution-2
  (are [result s] (= result (sut/solution-2 s))
    #_#_
    :FIXME input))
