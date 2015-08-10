(deftemplate MENTAL-STATE::belief
"Robot's belief based on SharedPlans theory."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN))
(slot belief (type STRING))
(slot belief-type (type SYMBOL) (allowed-values PRIVATE INFERRED MUTUAL))
(slot belief-about (type SYMBOL) (allowed-values SELF OTHER EVENT ENVIRONMENT TASK)))

(deftemplate MENTAL-STATE::intention
"Robot's intention or belief about human's intention."
(slot id (type STRING))
(slot intention (type STRING)))

(deftemplate MENTAL-STATE::motive
"Robot's motive."
(slot id (type STRING))
(slot motive (type STRING)))

(deftemplate MENTAL-STATE::goal
"Robot's goal or belief about human's goal."
(slot goal (type STRING)))

(deftemplate MENTAL-STATE::emotion-instance
"Robot's emotion instance or or belief about human's emotion instance."
(slot emotion (type STRING)))