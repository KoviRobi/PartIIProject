(import (scheme base)
        (scheme write))

(displayln "Begin")
(define omega (lambda (x) (x x)))
(omega omega) ; tail call
(displayln "End") ; Never printed
