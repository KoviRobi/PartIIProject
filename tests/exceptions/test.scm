(import (scheme base)
        (scheme java))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

(with-exception-handler
  (lambda (x)
    (println x))
  (lambda () (raise "Raise and handle exceptions")))
