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

;(define get/cc (lambda () (call-with-current-continuation (lambda (c) c))))
;(define foo (get/cc))
;display foo)
;(foo 1)

;((lambda (x) (writeln x) (x 5)) (get/cc))

#;((lambda (yin)
  ((lambda (yang)
    (yin yang) )
   ((lambda (cc)
           (display "*") cc)
          (get/cc) ) ) )
 ((lambda (cc)
   (display "@") cc)
  (get/cc) ) )

#;(define-syntax case
  (syntax-rules (else =>)
    ((case (key ...)
       clauses ...)
     (let ((atom-key (key ...)))
       (case atom-key clauses ...)))
    ((case key
       (else => result))
     (result key))
    ((case key
       (else result1 result2 ...))
     (begin result1 result2 ...))
    ((case key
       ((atoms ...) result1 result2 ...))
     (if (memv key '(atoms ...))
         (begin result1 result2 ...)))
    ((case key
       ((atoms ...) => result))
     (if (memv key '(atoms ...))
         (result key)))
    ((case key
       ((atoms ...) => result)
       clause clauses ...)
     (if (memv key '(atoms ...))
         (result key)
         (case key clause clauses ...)))
    ((case key
       ((atoms ...) result1 result2 ...)
       clause clauses ...)
     (if (memv key '(atoms ...))
         (begin result1 result2 ...)
         (case key clause clauses ...)))))

#;(define make-box
  (lambda (value)
    (let ((box
      (call-with-current-continuation (lambda (exit)
        (let ((behaviour
            (call-with-current-continuation (lambda (store)
              (exit (lambda (msg . new)
                (call-with-current-continuation (lambda (caller)
                  (case msg
                    ((get) (store (cons (car behaviour) caller)))
                    ((set) (store (cons (car new) caller))) ) )) )) )) ))
          ((cdr behaviour) (car behaviour)) ) )) ))
      (box 'set value)
      box ) ) )

#;(define box (make-box 1))
#;(displayln (box 'get))
