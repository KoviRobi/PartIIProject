(import (scheme base))

(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))

(define-syntax dont-call
  (syntax-rules ()
    ((dont-call)
     (syntax-error "I told you to not call me!" 'tired 'want 'to 'sleep) ) ) )
(dont-call)
(println "End")
