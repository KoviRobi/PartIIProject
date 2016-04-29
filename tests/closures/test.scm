(import (scheme base)
        (scheme write))

(define convert
  (lambda (n)
    (n (lambda (x) (+ 1 x)) 0) ) )

; Church encoded data, good test of closures
(define zero (lambda (f x) x))
(define one (lambda (f x) (f x)))
(define five (lambda (f x) (f (f (f (f (f x)))))))
(define cons (lambda (a b) (lambda (f) (f a b))))
(define car (lambda (p) (p (lambda (a d) a))))
(define cdr (lambda (p) (p (lambda (a d) d))))
(define true (lambda (t f) t))
(define false (lambda (t f) f))
(define zero? (lambda (n) (n (lambda (m) false) true)))
(define add (lambda (m n) (lambda (f x) (n f (m f x)))))
(define mult (lambda (m n) (lambda (f x) (n (lambda (x) (m f x)) x))))
(define sub1 (lambda (n)
  (cdr (n (lambda (x)
               (cons (add (car x) one) (car x)))
                              (cons zero zero) )) ))
(define if (lambda (p t f) ((p t f))))

(define y
  (lambda (f)
    ((lambda (x) (f (lambda (a) ((x x) a))))
     (lambda (x) (f (lambda (a) ((x x) a)))) )))

(define fact
  (lambda (fact)
    (lambda (n)
      (if (zero? n)
        (lambda () one)
        (lambda () (mult n (fact (sub1 n)))) ) ) ) )

(writeln (convert ((y fact) five)))
