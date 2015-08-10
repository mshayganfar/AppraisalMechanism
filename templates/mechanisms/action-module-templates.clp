(deftemplate ACTION::action
"Attributes of an action."
(slot task (type STRING))
(slot event (type STRING))
(slot action (type STRING)))

(deftemplate ACTION::emotion-acknowledged
"Indicator of emotion acknowledgement."
(slot status (type SYMBOL) (allowed-values ACKNOWLEDGED IGNORED UNKNOWN) (default UNKNOWN)))