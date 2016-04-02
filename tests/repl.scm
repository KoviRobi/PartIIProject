(import (scheme base)
        (scheme read)
        (scheme eval)
        (scheme write))

(let ((interaction-environment
      (mutable-environment '(scheme base)) ))
  (let loop ()
    (write
      (eval
        (read)
        (interaction-environment) ) )
    (loop) ) )
