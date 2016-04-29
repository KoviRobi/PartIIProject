(import (scheme base)
        (scheme write))

; Taken from R7RS p. 24
(define-syntax be-like-begin
  (syntax-rules ()
    ((be-like-begin name)
     (define-syntax name
       (syntax-rules ()
         ((name expr (... ...))
          (begin expr (... ...))))))))
(be-like-begin sequence)
(writeln (sequence 1 2 3 4))
