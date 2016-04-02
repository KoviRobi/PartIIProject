(import (scheme base)
        ;(scheme read)
        (scheme eval)
        (scheme write)
        (scheme java))

(define interaction-environment (mutable-environment '(scheme base)))

(define onetwo (eval '(cons 1 2) interaction-environment))
(write onetwo)
(write (onetwo 'new))
(write ((vector-ref (onetwo 'getConstructors) 0) 'isAccessible))

;(let ((interaction-environment
;      (mutable-environment '(scheme base)) ))
;  (let loop ()
;    (write
;      (eval
;        (read)
;        (interaction-environment) ) )
;    (loop) ) )
