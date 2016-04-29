(import (scheme base)
        (scheme write))

(with-exception-handler
  (lambda (x)
    (displayln x))
  (lambda () (raise "Raise and handle exceptions")))
