(deftemplate ToM::user-type
"Type of the collaborative partner."
(slot task (type STRING))
(slot event (type STRING))
(slot knowledge-competency (type SYMBOL) (allowed-values HIGH-AUTONOMY LOW-AUTONOMY MEDIUM-AUTONOMY UNKNOWN) (default UNKNOWN))
(slot behavior-competency (type SYMBOL) (allowed-values HIGH-COMMUNICATIVE LOW-COMMUNICATIVE MEDIUM-COMMUNICATIVE UNKNOWN) (default UNKNOWN)))

(deftemplate ToM::belief
"Human's anticipated belief."
(slot task (type STRING))
(slot event (type STRING))
(slot belief (type STRING)))

(deftemplate ToM::intention
"Human's anticipated intention."
(slot task (type STRING))
(slot event (type STRING))
(slot intention (type STRING)))

(deftemplate ToM::goal
"Human's anticipated goal."
(slot task (type STRING))
(slot event (type STRING))
(slot goal (type STRING)))

(deftemplate ToM::motive
"Human's anticipated motive."
(slot task (type STRING))
(slot event (type STRING))
(slot motive (type STRING))
(slot motive-insistence (type SYMBOL) (allowed-values HIGH LOW MEDIUM UNKNOWN) (default UNKNOWN))
(slot motive-importance (type SYMBOL) (allowed-values IMPORTANT UNIMPORTANT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot motive-urgency (type SYMBOL) (allowed-values URGENT NONURGENT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot motive-intensity (type SYMBOL) (allowed-values HIGH LOW MEDIUM UNKNOWN) (default UNKNOWN))
(slot motive-failure-disruptiveness (type SYMBOL) (allowed-values HIGH DISRUPTIVE NONDISRUPTIVE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot motive-status (type SYMBOL) (allowed-values ACTIVE PASSIVE UNKNOWN) (default UNKNOWN)))

(deftemplate ToM::reverse-appraisal
"Appraisal frame containing appraisal variables."
(slot task (type STRING))
(slot event (type STRING))
(slot event-type (type SYMBOL) (allowed-values UTTERANCE ACTION EXPRESSION INTERNAL UNKNOWN) (default UNKNOWN))
(slot emotion-instance (type SYMBOL) (allowed-values JOY ANGER HOPE GUILT PRIDE SHAME WORRY FRUSTRATION NEUTRAL) (default NEUTRAL))
(slot perspective (type SYMBOL) (allowed-values SELF OTHER UNKNOWN) (default UNKNOWN))
(slot relevance (type SYMBOL) (allowed-values RELEVANT IRRELEVANT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot desirability (type SYMBOL) (allowed-values DESIRABLE UNDESIRABLE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot likelihood (type SYMBOL) (allowed-values LIKELY UNLIKELY NEUTRAL UNKNOWN) (default UNKNOWN))
(slot causal-attribution (type SYMBOL) (allowed-values SELF OTHER BOTH NONE UNKNOWN) (default UNKNOWN))
(slot controllability (type SYMBOL) (allowed-values CONTROLLABLE UNCONTROLLABLE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot changeability (type SYMBOL) (allowed-values CHANGEABLE UNCHANGEABLE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot expectedness (type SYMBOL) (allowed-values EXPECTED UNEXPECTED NEUTRAL UNKNOWN) (default UNKNOWN))
(slot urgency (type SYMBOL) (allowed-values URGENT UNURGENT NEUTRAL UNKNOWN) (default UNKNOWN)))

(deftemplate ToM::appraisal-frame
"Appraisal frame containing appraisal variables from the other's perspective."
(slot task (type STRING))
(slot event (type STRING))
(slot event-type (type SYMBOL) (allowed-values UTTERANCE ACTION EXPRESSION INTERNAL UNKNOWN) (default UNKNOWN))
(slot with-respect-to (type SYMBOL) (allowed-values SHARED-GOAL SELF-GOAL OTHER-GOAL) (default SHARED-GOAL))
(slot perspective (type SYMBOL) (allowed-values SELF OTHER UNKNOWN) (default UNKNOWN))
(slot relevance (type SYMBOL) (allowed-values RELEVANT IRRELEVANT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot desirability (type SYMBOL) (allowed-values DESIRABLE UNDESIRABLE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot likelihood (type SYMBOL) (allowed-values LIKELY UNLIKELY NEUTRAL UNKNOWN) (default UNKNOWN))
(slot causal-attribution (type SYMBOL) (allowed-values SELF OTHER BOTH NONE UNKNOWN) (default UNKNOWN))
(slot controllability (type SYMBOL) (allowed-values CONTROLLABLE UNCONTROLLABLE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot changeability (type SYMBOL) (allowed-values CHANGEABLE UNCHANGEABLE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot expectedness (type SYMBOL) (allowed-values EXPECTED UNEXPECTED NEUTRAL UNKNOWN) (default UNKNOWN))
(slot urgency (type SYMBOL) (allowed-values URGENT UNURGENT NEUTRAL UNKNOWN) (default UNKNOWN)))