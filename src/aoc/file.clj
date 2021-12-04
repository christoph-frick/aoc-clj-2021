(ns aoc.file
  (:require [clojure.java.io :as io]))

(defn read
  [file-name]
  (let [res (io/resource file-name)]
    (assert res (str "resource " file-name " not found"))
    (-> res io/reader slurp)))

(defn read-lines
  [file-name]
  (let [res (io/resource file-name)]
    (assert res (str "resource " file-name " not found"))
    (with-open [rdr (io/reader res)]
      (doall (line-seq rdr)))))
