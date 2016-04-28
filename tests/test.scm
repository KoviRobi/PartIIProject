(import (scheme base)
        (scheme read)
        (scheme eval)
        (scheme write)
        (scheme java))

(writeln 1)
(define foo (lambda (x) (lambda (y) x)))
(define bar (foo 1))
(define baz (foo 2))
(writeln (bar 'a))
(writeln (baz 'b))
(writeln ((lambda (x) x)
 ((lambda (x) x) 1)))

((static-field (class 'java.lang.System) 'out)
  'println "Hello, world!")

(multi-despatch (static-field (class 'java.lang.System) 'out)
    ('print "Hello"))
