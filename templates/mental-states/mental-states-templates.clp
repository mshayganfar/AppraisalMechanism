(deftemplate MENTAL-STATE::belief
"Robot's belief based on SharedPlans theory."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot belief-type (type SYMBOL) (allowed-values PRIVATE INFERRED MUTUAL UNKNOWN) (default UNKNOWN))
(slot belief-about (type SYMBOL) (allowed-values SELF OTHER EVENT ENVIRONMENT TASK UNKNOWN) (default UNKNOWN))
(slot belief (type STRING))
(slot strength (type SYMBOL) (allowed-values HIGH MEDIUM LOW UNKNOWN) (default UNKNOWN))
(slot accuracy (type SYMBOL) (allowed-values HIGH MEDIUM LOW UNKNOWN) (default UNKNOWN))
(slot frequency (type SYMBOL) (allowed-values HIGH MEDIUM LOW UNKNOWN) (default UNKNOWN))
(slot recency (type SYMBOL) (allowed-values HIGH MEDIUM LOW UNKNOWN) (default UNKNOWN))
(slot saliency (type SYMBOL) (allowed-values HIGH MEDIUM LOW UNKNOWN) (default UNKNOWN))
(slot persistence (type SYMBOL) (allowed-values HIGH MEDIUM LOW UNKNOWN) (default UNKNOWN)))

(deftemplate MENTAL-STATE::motive
"Robot's motive."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot motive (type STRING)))

(deftemplate MENTAL-STATE::intention
"Robot's intention or belief about human's intention."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot intention (type STRING)))

(deftemplate MENTAL-STATE::goal
"Robot's goal or belief about human's goal."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot goal (type STRING)))

(deftemplate MENTAL-STATE::emotion-instance
"Robot's emotion instance or or belief about human's emotion instance."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot emotion-instance (type SYMBOL) (allowed-values JOY ANGER HOPE GUILT PRIDE SHAME WORRY FRUSTRATION NEUTRAL) (default NEUTRAL)))