(import (scheme base)
        ;(scheme read)
        (scheme eval)
        (scheme write)
        (scheme java))

(define interaction-environment (mutable-environment '(scheme base)))

(define onetwo (eval '(cons 1 2) interaction-environment))
(write onetwo)
(write (eval '(cons 3 4) interaction-environment))
(write (((class 'java.lang.String) 'getConstructor #()) 'setAccessible #false))
;(write (onetwo 'new))
;(write  (onetwo))

;(let ((interaction-environment
;      (mutable-environment '(scheme base)) ))
;  (let loop ()
;    (write
;      (eval
;        (read)
;        (interaction-environment) ) )
;    (loop) ) )
