(deftemplate MENTAL-STATE::belief
"Robot's belief based on SharedPlans theory."
(slot belief (type STRING)))

(deftemplate MENTAL-STATE::intention
"Robot's intention or belief about human's intention."
(slot intention (type STRING)))

(deftemplate MENTAL-STATE::motive
"Robot's motive."
(slot motive (type STRING)))

(deftemplate MENTAL-STATE::goal
"Robot's goal or belief about human's goal."
(slot goal (type STRING)))

(deftemplate MENTAL-STATE::emotion-instance
"Robot's emotion instance or or belief about human's emotion instance."
(slot emotion (type STRING)))