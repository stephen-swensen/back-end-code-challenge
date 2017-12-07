;; Problem(1): Add all the natural numbers below one thousand that are multiples of 3 or 5
;; Solution: we create a range from 0 to 999 inclusive, filter in those divisible by 3 or 5, and sum
;; Answer: 166833
(reduce
  +
  (filter
    (fn [x] (or (= 0 (mod x 3))(= 0 (mod x 5))))
    (range 1000)))
