(import (scheme base)
        (scheme eval)
        (scheme write))

(define environment  (environment '(scheme base)))

(writeln (eval '(cons 1 2) environment))