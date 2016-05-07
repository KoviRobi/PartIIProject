(import (scheme base)
        (scheme write))

(with-exception-handler
  displayln
  (lambda () (raise "Raise and handle exceptions")))
