(ns aoc2020.core
  (:require [clojure.string :as str])
  (:gen-class))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn aoc2020-1 []
  (let [convert (fn [x] (vec (sort (map #(Integer/parseInt %) (str/split x #"\n")))))
        input (convert (slurp "1-input.txt"))
        groups (partition-by #(quot % 505) input)
        first-last (juxt (juxt first last) (juxt #(nth % 2) #(nth % 3)))
        y (first (first-last groups))
        y1 (first y)
        y2 (last y)
        flt #(= 2020 (first %))]
     (map
      #(map (juxt + * (fn [x y] [x y])) y1 (cycle [%])) y2)))

(aoc2020-1)


(defn euler-1
  "Find the sum of all the multiples of 3 or 5 below 1000"
  [col]

  (let
      [
       func (filter
              #(some zero? [(mod % 5) (mod % 3)]))]
    (transduce func + col)))

(euler-1 (range 1 1000))

;;(defn euler-2
;;  )
