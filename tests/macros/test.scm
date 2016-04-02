(import (scheme base)
        (scheme java))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

(define-syntax macro-alternation
  (syntax-rules ()
    ((_ 0) (println "Zero"))
    ((_ 1) (println "One")) ) )

(macro-alternation 0)
(macro-alternation 1)

(define-syntax multi-matches
  (syntax-rules ()
    ((multi-matches x y ...) '((... ...) (x end) (y ... end))) ) )

(println (multi-matches 1 2 3))
(println (multi-matches 4 5))
(println (multi-matches 6))

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
