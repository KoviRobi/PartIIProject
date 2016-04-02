(import (scheme base)
        (scheme java))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

; Closure
((lambda (x)
  ((lambda (y)
    (println x))
   "y-arg"))
 "x-arg")

; Church encoded data, good test of closures
(define zero (lambda (f x) x))
(define one (lambda (f x) (f x)))
(define cons (lambda (a b) (lambda (f) (f a b))))
(define car (lambda (p) (p (lambda (a d) a))))
(define cdr (lambda (p) (p (lambda (a d) d))))
(define true (lambda (t f) t))
(define false (lambda (t f) f))
(define zero? (lambda (n) (n (lambda (m) false) true)))
(define add (lambda (n m) (lambda (f x) (n f (m f x)))))
(define sub1 (lambda (n)
  (cdr (n (lambda (x) (cons (add (car x) one) (car x)))
                              (cons zero zero) )) ))
(define if (lambda (p t f) ((p t f))))

(let loop ((n (lambda (f x) (f (f (f x))))))
  (if (zero? n)
    (lambda () (println "Zero"))
    (lambda () (println "Non-zero")  (loop (sub1 n))) ) )
