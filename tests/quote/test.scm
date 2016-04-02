(import (scheme base)
        (scheme java))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

(println '(1 2 3))