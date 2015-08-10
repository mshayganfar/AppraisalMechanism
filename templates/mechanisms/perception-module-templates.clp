(deftemplate PERCEPTION::action
"Attributes of a percept of an action."
(slot event (type STRING))
(slot action (type STRING))
(slot agent (type SYMBOL) (allowed-values ROBOT HUMAN NONE BOTH UNKNOWN) (default UNKNOWN))
(slot action-status (type SYMBOL) (allowed-values RUN WAIT DONE UNKNOWN) (default UNKNOWN)))