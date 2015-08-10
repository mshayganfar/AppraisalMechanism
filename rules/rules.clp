;;;=============================================================
;;;     
;;;     Jess version 4.1
;;;
;;;     Author: Mohammad Shayganfar
;;;
;;;=============================================================

(focus MENTAL-STATE)

(defrule MENTAL-STATE::test
"To check rules."
(MENTAL-STATE::belief (belief "astronaut-frustrated"))
=>
(printout t "belief-asserted" crlf))
;(assert (MENTAL-STATE::belief (belief "astronaut-anger"))))