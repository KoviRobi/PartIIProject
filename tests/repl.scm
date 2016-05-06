(import (scheme base)
        (scheme read)
        (scheme eval)
        (scheme write)
        (scheme java))

(define interaction-environment
  (mutable-environment
    '(scheme base)
    '(scheme eval)
    '(scheme java)
    '(scheme read)
    '(scheme repl)
    '(scheme write)
    '(scheme time)))

(let loop ()
  ((static-field (class 'java.lang.System) 'out) 'print "> ")
  (writeln (eval (read) interaction-environment))
  (loop))
