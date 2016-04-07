(import (scheme base) (scheme write))

(define x 4)
((lambda (x) (set! x 1)) x)
(write x)

(define set-get
	((lambda ()
		(let ((x 10))
			(cons
				(lambda (v)
					(set! x v))
				(lambda ()
					x))))))

(write ((cdr set-get)))
(write ((car set-get) 1))
(write ((cdr set-get)))
