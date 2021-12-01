(ns aoc.file
  (:require [clojure.java.io :as io]))

(defn read-lines
  [file-name]
  (let [res (io/resource file-name)]
    (assert res (str "resource " file-name " not found"))
    (with-open [rdr (io/reader res)]
      (doall (line-seq rdr)))))
