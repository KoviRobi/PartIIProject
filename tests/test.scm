(import (scheme base)
        (scheme read)
        (scheme eval)
        (scheme write)
        (scheme java))

#;(multi-despatch (static-field (class 'java.lang.System) 'out)
    ('print "Hello")
    ('println ", world!"))

#;(define (get/cc) (call-with-current-continuation (lambda (c) c)))
#;(let* ((yin
         ((lambda (cc)
           (display "@") cc)
          (get/cc) ) )
       (yang
         ((lambda (cc)
           (display "*") cc)
          (get/cc) ) ) )
    (yin yang) )

#;(dynamic-wind
  (lambda () (displayln 'before))
  (lambda () (displayln 'body))
  (lambda () (displayln 'after)) )

(displayln (call/cc
  (lambda (cc)
    (dynamic-wind
      (lambda () (displayln 'before))
      (lambda ()
        (displayln 'body)
        (cc 'error) )
      (lambda () (displayln 'after)) ) ) ) )

(newline)

(with-exception-handler
  displayln
  (lambda ()
    (dynamic-wind
      (lambda () (displayln 'before))
      (lambda ()
        (displayln 'body)
        (raise 'error) )
      (lambda () (displayln 'after)) ) ) )
