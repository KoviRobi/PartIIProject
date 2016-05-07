(import (scheme base)
        (scheme read)
        (scheme eval)
        (scheme write)
        (scheme java))

;(writeln 1)
;(define foo (lambda (x) (lambda (y) x)))
;(define bar (foo 1))
;(define baz (foo 2))
;(writeln (bar 'a))
;(writeln (baz 'b))
;(writeln ((lambda (x) x)
; ((lambda (x) x) 1)))
;
;(multi-despatch (static-field (class 'java.lang.System) 'out)
;    ('print "Hello")
;    ('println ", world!"))

;(displayln (call/cc (lambda (cc) (cc "Foo"))))

(define get/cc (lambda () (call-with-current-continuation (lambda (c) c))))
((lambda (yin)
  ((lambda (yang)
    (yin yang) )
   ((lambda (cc)
           (display "*") cc)
          (get/cc) ) ) )
 ((lambda (cc)
   (display "@") cc)
  (get/cc) ) )

(define behaviour 1)
(define make-box
  (lambda (value)
    (let ((box
      (call-with-current-continuation (lambda (exit)
        (set! behaviour
            (call-with-current-continuation (lambda (store)
              (exit (lambda (msg . new)
                (call-with-current-continuation (lambda (caller)
                  (if (eq? msg 'get)
                    (store (cons (car behaviour) caller))
                    (if (eq? msg 'set)
                      (store (cons (car new) caller)) ) ) )) )) )) )
          ((cdr behaviour) (car behaviour)) )) ))
      (box 'set value)
      box ) ) )

(define box (make-box 1))
(displayln (box 'get))
(box 'set 3)
(displayln (box 'get))
