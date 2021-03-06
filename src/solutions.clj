;; Problem(1): Add all the natural numbers below one thousand that are multiples of 3 or 5
;; Answer: 233168
;; Explanation: a straight forward application of `filter` and `reduce` to the `range` of numbers
;;  between 0 and 999 inclusive. We use the "thread last" macro (`->>`) to give pipeline syntax
;;  similar to F#'s `|>` operator.
(->> (range 1000)
     (filter (fn [x] (or (= 0 (mod x 3))(= 0 (mod x 5)))))
     (reduce +))

;; Problem(2): By considering the terms in the Fibonacci sequence whose values do not exceed four million,
;;  find the sum of the even-valued terms.
;; Answer: 4613732
;; Explanation:
;;  1) we define a function `fib` which returns the nth Fibonacci number using the standard recursive definition
;;  2) we bind `fibs` to an infinite, lazy sequence of Fibonacci numbers that we generate by applying `fib` to
;;     elements of `(range)` using `map`. n.b. this is not very efficient at all because `fib` was not implemented
;;     with any kind of memoization, so we repeat term calculations over and over again.
;;  3) now we can easily pipe `fibs` into `take-while`, `filter`, and `reduce` to get our answer.
(defn fib [n]
  (if (<= n 2)
    n
    (+ (fib (- n 1)) (fib (- n 2)))))
(let [fibs (map fib (range))]
  (->> fibs
       (take-while (fn [x] (< x 4000000)))
       (filter even?)
       (reduce +)))

;; Problem(4): Find the largest palindrome made from the product of two 3-digit numbers.
;; Answer: 906609
;; Explanation:
;;  1) define `palindrome? [n]` function which compares n as string to n as string reversed
;;  2) we generate a list of all 3-digit number products, then filter for palindromes and
;;     take the max
(defn palindrome? [n]
  (= (str n) (->> n str reverse (apply str))))
(->> (for [x (range 100 1000) y (range 100 1000)] (* x y))
     (filter palindrome?)
     (apply max))

;; Problem(7): What is the 1001st prime number?
;; Answer: 104743
;; Explanation:
;;  1) we define the `prime? [n]` function which is implemented with an inner recursive function that checks
;;     the divisibility of `n` by the numbers 2 through the square root of n
;;  2) we create a lazy, infinite sequence of primes and take the 10001th
(use '[clojure.math.numeric-tower])
(defn prime? [n]
  ((fn test [i]
     (cond
       (> i (sqrt n)) true
       (= (mod n i) 0) false
       :else (test (+ i 1)))) 2))
(let [primes (->> (range) (drop 1) (filter prime?))]
  (nth primes 10001))

