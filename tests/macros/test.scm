(import (scheme base)
        (scheme write))

(define-syntax macro-alternation
  (syntax-rules ()
    ((_ 0) (displayln 'Zero))
    ((_ 1) (displayln 'One)) ) )

(macro-alternation 0)
(macro-alternation 1)

(define-syntax multi-matches
  (syntax-rules ()
    ((multi-matches x y ...) '((... ...) (x end) (y ... end))) ) )

(displayln (multi-matches 1 2 3))
(displayln (multi-matches 4 5))
(displayln (multi-matches 6))

; More complicated ellipsis macro
(define-syntax structural-match
  (syntax-rules ()
    ((structural-match (x ...) ((y ...) ...))
     '((x (x y) ...) ...) ) ) )

(displayln
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
       (displayln x-hygiene)
       M) ) ) )
(let ((x-hygiene 3)
      (y-hygiene 4) )
  (foo-hygiene (displayln y-hygiene)) )
