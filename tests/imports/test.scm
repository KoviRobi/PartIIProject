(import (rename (only (scheme base) cons) (cons construct))
        (prefix (except (scheme base) cons) get-))

(get-car (construct 1 2))
