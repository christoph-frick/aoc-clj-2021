#!/usr/bin/env bb

(require '[clojure.java.shell :refer [sh]])

(defn parse-day
  [s]
  (edn/read-string s))

(defn format-day
  [d]
  (format "%02d" d))

(defn replace
  [day s]
  (str/replace s #"XX" day))

(defn gen
  [day src tgt]
  (let [tgt' (replace day tgt)]
    (->> src
         slurp
         (replace day)
         (spit tgt'))))

(assert (= 1 (count *command-line-args*)))

(let [day (-> *command-line-args* first parse-day format-day)
      resource-root (str "resources/day/" day)]
  (gen day "tmpl/day_XX.clj"      "src/aoc_2021/day_XX.clj")
  (gen day "tmpl/day_XX_test.clj" "test/aoc_2021/day_XX_test.clj")
  (sh "sh" "-c" (str "mkdir " resource-root " && "
                     "mv /tmp/input " resource-root "/input.txt &&"
                     "touch " resource-root "/test.txt"))
  (println (replace day "lein kaocha --watch --focus-meta :lib --focus-meta :day-XX")))
