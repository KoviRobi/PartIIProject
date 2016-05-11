(define-library (schemeCall test)
  (export (rename testf test))
  (import (scheme base))
  (begin
    (define (testf a b) b) ) )
