(deftemplate MENTAL-STATE::belief
"Robot's belief based on SharedPlans theory."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot event-type (type SYMBOL) (allowed-values UTTERANCE ACTION EMOTION UNKNOWN) (default UNKNOWN))
(slot event-origin (type SYMBOL) (allowed-values EXTERNAL_EVENT INTERNAL_EVENT UNKNOWN) (default UNKNOWN))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot belief-type (type SYMBOL) (allowed-values PRIVATE INFERRED MUTUAL UNKNOWN) (default UNKNOWN))
(slot belief-about (type SYMBOL) (allowed-values SELF OTHER ENVIRONMENT TASK UNKNOWN) (default UNKNOWN))
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
(slot motive (type STRING))
(slot motive-status (type SYMBOL) (allowed-values ACTIVE PASSIVE UNKNOWN) (default UNKNOWN))
(slot insistence (type SYMBOL) (allowed-values HIGH LOW MEDIUM UNKNOWN) (default UNKNOWN))
(slot importance (type SYMBOL) (allowed-values IMPORTANT UNIMPORTANT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot urgency (type SYMBOL) (allowed-values URGENT NONURGENT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot intensity (type SYMBOL) (allowed-values HIGH LOW MEDIUM UNKNOWN) (default UNKNOWN))
(slot failure-disruptiveness (type SYMBOL) (allowed-values HIGH DISRUPTIVE NONDISRUPTIVE NEUTRAL UNKNOWN) (default UNKNOWN)))

(deftemplate MENTAL-STATE::intention
"Robot's intention or belief about human's intention."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot intention (type STRING))
(slot temporal-status (type SYMBOL) (allowed-values CONSISTENT INCONSISTENT UNKNOWN) (default UNKNOWN))
(slot direct-experience (type SYMBOL) (allowed-values SIMILAR DISSIMILAR UNKNOWN) (default UNKNOWN))
(slot certainty (type SYMBOL) (allowed-values CERTAIN UNCERTAIN UNKNOWN) (default UNKNOWN))
(slot ambivalence (type SYMBOL) (allowed-values AMBIVALENT UNAMBIVALENT UNKNOWN) (default UNKNOWN))
(slot affective-cognitive-consistency (type SYMBOL) (allowed-values CONSISTENT INCONSISTENT UNKNOWN) (default UNKNOWN)))

(deftemplate MENTAL-STATE::goal
"Robot's goal or belief about human's goal."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot goal (type STRING))
(slot proximity (type SYMBOL) (allowed-values PROXIMAL DISTAL UNKNOWN) (default UNKNOWN))
(slot specificity (type SYMBOL) (allowed-values SPECIFIC GENERAL UNKNOWN) (default UNKNOWN))
(slot difficulty (type SYMBOL) (allowed-values DIFFICULT EASY UNKNOWN) (default UNKNOWN)))

(deftemplate MENTAL-STATE::emotion-instance
"Robot's emotion instance or or belief about human's emotion instance."
(slot id (type STRING))
(slot task (type STRING))
(slot event (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN UNKNOWN) (default UNKNOWN))
(slot emotion-instance (type SYMBOL) (allowed-values JOY ANGER HOPE GUILT PRIDE SHAME WORRY FRUSTRATION NEUTRAL) (default NEUTRAL)))