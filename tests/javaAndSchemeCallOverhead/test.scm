(import (scheme base)
        (scheme java)
        (scheme time)
        (scheme inexact)
        (scheme write)
        (schemeCall test))

(define (repeat start end step body)
  (when (<= start end)
    (body start)
    (repeat (+ start step) end step body) ) )

#;(repeat 0 100000 1
  (lambda (n)
    (let* ((start (current-jiffy))
           (result ((class 'javaCall.test) 'call 1 "foo"))
           (end (current-jiffy)) )
      (display "Scheme to Java,")
      (displayln (- end start)) ) ) )

(repeat 0 100000 1
  (lambda (n)
    (let* ((start (current-jiffy))
           (result (test 1 "foo"))
           (end (current-jiffy)) )
      (display "Scheme to Scheme,")
      (displayln (- end start)) ) ) )
