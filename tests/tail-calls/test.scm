(import (scheme base)
        (scheme java))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

(println "Begin")
(define omega (lambda (x) (x x)))
(omega omega) ; tail call
(println "End")
