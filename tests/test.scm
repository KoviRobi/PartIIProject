(import (scheme base)
        (only (scheme java) class)
        (except (scheme java) class field)
        (rename (prefix (only (scheme java) field) get-) (get-field get-field-from-object)))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

(println " - Syntax ambiguity with #|a|#|b|#|c#|")
(println " - On track")
(println " - Backup woes")
(println " - Working demo of the features:")

(with-exception-handler
  (lambda (x)
    (println x))
  (lambda () (raise "Raise and handle exceptions")))

(define-syntax bar
  (syntax-rules ()
    ((bar 0) (println "Hygienic macros"))
    ((bar 1) (println " - no binding introduced or captured")) ) )

(let ((print println)
      (println "Java calls"))
  (print println)
  (bar 0)
  (bar 1) )

(println "")
(define omega (lambda (x) (x x)))
;(omega omega) ; tail call
(println "End")
(println (quote (1 2 "Foo" a)))

(define-syntax baz
  (syntax-rules ()
    ((baz x y ...) '((... ...) (x end) (y ... end))) ) )

(println (baz 1 2 3))
(println (baz 4 5))
(println (baz 6))

; More complicated ellipsis macro
(define-syntax structural-match
  (syntax-rules ()
    ((structural-match (x ...) ((y ...) ...))
     '((x (x y) ...) ...) ) ) )

(println
  (structural-match	(a b c)
					((1 2 3)
					(4 5 6)
					(7 8 9 10) ) ) )

; Hygiene test
(define x-hygiene 1)
(define-syntax foo-hygiene
  (syntax-rules ()
    ((foo-hygiene M)
     (let ((y-hygiene 2))
       (println x-hygiene)
       M) ) ) )
(let ((x-hygiene 3)
      (y-hygiene 4) )
  (foo-hygiene (println y-hygiene)) )

(define-syntax dont-call
  (syntax-rules ()
    ((dont-call)
     (syntax-error "I told you to not call me!" 'tired 'want 'to 'sleep) ) ) )
;(dont-call)

(println (quote 'foo))

((lambda (x) (println x))
 ((lambda (x) x)
  4))

; Message passing tests
(define test-class (class 'test.JavaCallTest))
(println test-class)
(println (test-class 'getConstructors))
(define test-object (test-class 'new "Hello, world!"))
(test-object 'printMessage)
(println (get-field-from-object test-object 'message))
(println (static-field test-class 'secretMessage))
((test-class 'new) 'printMessage 0)
(test-object 'printStaticMessage '(1 2 3))
(test-class 'printStaticMessage '(4 5 6))

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
    (lambda () (println "zero"))
    (lambda () (println "Non-zero")  (loop (sub1 n))) ) )

;(let ((interaction-environment
;      (mutable-environment '(scheme base)) ))
;  (let loop ()
;    (write
;      (eval
;        (read)
;        (interaction-environment) ) )
;    (loop) ) )

; ToDo: Possibly use shadow environments,
; an environment of two environments, lookups
; happen in the first, but extensions to both?
;(define-syntax be-like-begin
;  (syntax-rules ()
;    ((be-like-begin name)
;     (define-syntax name
;       (syntax-rules ()
;         ((name expr (... ...))
;          (begin expr (... ...))))))))
;(be-like-begin sequence)
;(println (sequence 1 2 3 4))