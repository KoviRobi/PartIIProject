(import (scheme base)
        (scheme write) )

(write (if #true 0 1))
(write (if #false 0 1))
(if #true (write 0) (write 1))
(if #false (write 0) (write 1))
(if (not 1) (writeln 0) (writeln 1))
