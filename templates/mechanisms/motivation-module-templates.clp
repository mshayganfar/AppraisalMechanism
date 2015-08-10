(deftemplate MOTIVATION::motive
"Robot's motive."
(slot task (type STRING))
(slot event (type STRING))
(slot motive (type STRING))
(slot motive-insistence (type SYMBOL) (allowed-values HIGH LOW MEDIUM UNKNOWN) (default UNKNOWN))
(slot motive-importance (type SYMBOL) (allowed-values IMPORTANT UNIMPORTANT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot motive-urgency (type SYMBOL) (allowed-values URGENT NONURGENT NEUTRAL UNKNOWN) (default UNKNOWN))
(slot motive-intensity (type SYMBOL) (allowed-values HIGH LOW MEDIUM UNKNOWN) (default UNKNOWN))
(slot motive-failure-disruptiveness (type SYMBOL) (allowed-values HIGH DISRUPTIVE NONDISRUPTIVE NEUTRAL UNKNOWN) (default UNKNOWN))
(slot motive-status (type SYMBOL) (allowed-values ACTIVE PASSIVE UNKNOWN) (default UNKNOWN)))