(ns aoc2020.core
  {:clj-kondo/config '{:linters {:unresolved-symbol {:exclude [FLOOR]}}}}
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as math]
            [clojure.core.reducers :as r]
            [criterium.core :as c])
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

(defmacro time-it
  "Times stuff"
  [expr]
  `(do
     (println "Running timing for " (quote ~expr))
     (eval (macroexpand '(c/bench ~expr))))
  )

;; Baseline

(defn euler-2
  "Find the sum of all even Fibonacci numbers < 4 million "
  []
  (let [limit 4000000]
    (loop [f1 1N f2 1N sum-of-evens 0]
      (if (<= f2 limit)
        (let [f3 (+ f1 f2)
              sum (if (even? f3) (+ sum-of-evens f3) sum-of-evens)]
          (recur f2 f3 sum))
        sum-of-evens))))

(time (euler-2))

(defn fib
  "Using Binet's formula"
  [n]
  (let [sqrt-5 (math/sqrt 5)
        f #(/
             (-' (math/expt (/ (+' 1 sqrt-5) 2) %)
                 (math/expt (/ (-' 1 sqrt-5) 2) %))
             sqrt-5)
        r (f n)]
    (biginteger
      (with-precision 0 :rounding FLOOR r))))

(macroexpand '(time-it (println "hello")))

(time-it (+ 1 1))

(transduce (map fib) + (range 0N 1000 3))
;;(map c/bench [
;;  (time (euler-2))
;;  (time (transduce
;;          (comp (map fib)
;;                (filter #(< % 4000000)))
;;          + (range 0 34 3)))
;;
;;  (time (r/fold + (r/filter #(< % 4000000) (r/map fib (range 0 34 3)))))
;;
;;  (time (reduce + (filter #(< % 4000000) (map fib (range 0 34 3)))))
;;  ])
