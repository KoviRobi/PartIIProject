(import (scheme base) (scheme write))

; Arguments copied
(define x 4)
((lambda (x) (set! x 1)) x)
(displayln x)

; Closures not
(define set-get
	((lambda ()
		(let ((x 10))
			(cons
				(lambda (v)
					(set! x v))
				(lambda ()
					x))))))

(displayln ((cdr set-get)))
(displayln ((car set-get) 1))
(displayln ((cdr set-get)))
