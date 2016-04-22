(import (scheme base)
        (scheme write))

; Church encoded data, good test of closures
(define zero (lambda (f x) x)) ;1
(define one (lambda (f x) (f x))) ;2
(define cons (lambda (a b) (lambda (f) (f a b)))) ;4
(define car (lambda (p) (p (lambda (a d) a)))) ;6
(define cdr (lambda (p) (p (lambda (a d) d)))) ;8
(define true (lambda (t f) t)) ;9
(define false (lambda (t f) f)) ;10
(define zero? (lambda (n) (n (lambda (m) false) true))) ;12
(define add (lambda (n m) (lambda (f x) (n f (m f x))))) ;14
(define sub1 (lambda (n)
  (cdr (n (lambda (x)
               (cons (add (car x) one) (car x)))
                              (cons zero zero) )) ))
(define if (lambda (p t f) ((p t f))))

(define y
  (lambda (f)
    ((lambda (x) (f (lambda (a) ((x x) a))))
     (lambda (x) (f (lambda (a) ((x x) a)))) )))

(let loop ((n (lambda (f x) (f (f (f x))))))
  (if (zero? n)
    (lambda () (displayln "Zero"))
    (lambda () (displayln "Non-zero")  (loop (sub1 n))) ) )
