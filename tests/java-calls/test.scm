(import (scheme base)
        (scheme java)
        (scheme write))


(define system-out (static-field (class 'java.lang.System) 'out))
(define println (lambda (value) (system-out 'println value)))
; Message passing tests
(define test-class (class 'test.JavaCallTest))
(println test-class)
(write (test-class 'getConstructors))
(define test-object (test-class 'new "Hello, world!"))
(test-object 'printMessage)
(println (field test-object 'message))
(println (static-field test-class 'secretMessage))
((test-class 'new) 'printMessage 0)
(test-object 'printStaticMessage '(1 2 3))
(test-class 'printStaticMessage '(4 5 6))
