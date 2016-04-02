(import (scheme base)
        (scheme java))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

(define x 4)

(println `(1 ,@(cons 2 3) x ,x))