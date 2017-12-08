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
;;  2) we bind `fibs` to an infinite, lazy sequence of Fibonacci numbers that we generate by mapping
;;     the elements of `(range)` using our `fib` implementation. n.b. this is not very efficient at all because
;;     `fib` was not implemented with any kind of memoization, so we repeat term calculations over and over again.
;;  3) now we can easily pipe `fibs` into `take-while`, `filter`, and `reduce` to get our answer.
(defn fib [n]
  (cond
    (= n 1) 1
    (= n 2) 2
    :else (+ (fib (- n 1)) (fib (- n 2)))))
(let [fibs (map (fn [i] (fib (+ i 1))) (range))]
  (->> fibs
       (take-while (fn [x] (< x 4000000)))
       (filter even?)
       (reduce +)))

