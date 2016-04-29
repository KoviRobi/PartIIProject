(import (scheme base)
        (scheme write))

(define-syntax dont-call
  (syntax-rules ()
    ((dont-call)
     (syntax-error "I told you to not call me!" 'stuff) ) ) )
(dont-call)
(writeln "End")
