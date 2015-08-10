(deftemplate COPING::attention-based-coping-behavior
"Coping behaviors based on strategies made in terms of their impact on attention."
(slot task (type STRING))
(slot event (type STRING))
(slot action (type STRING))
(slot intention (type STRING))
(slot coping-strategy (type SYMBOL) (allowed-values SEEK-INFO SUPPRESS-INFO UNKNOWN) (default UNKNOWN)))

(deftemplate COPING::belief-based-coping-behavior
"Coping behaviors based on strategies made in terms of their impact on belief."
(slot task (type STRING))
(slot event (type STRING))
(slot action (type STRING))
(slot intention (type STRING))
(slot coping-strategy (type SYMBOL) (allowed-values SHIFT-RESPONSIBILITY WISHFUL-THINKING UNKNOWN) (default UNKNOWN)))

(deftemplate COPING::desire-based-coping-behavior
"Coping behaviors based on strategies made in terms of their impact on intention."
(slot task (type STRING))
(slot event (type STRING))
(slot action (type STRING))
(slot intention (type STRING))
(slot coping-strategy (type SYMBOL) (allowed-values MENTAL-DISENGAGEMENT POSITIVE-REINTERPRETATION UNKNOWN) (default UNKNOWN)))

(deftemplate COPING::intention-based-coping-behavior
"Coping behaviors based on strategies made in terms of their impact on intention."
(slot task (type STRING))
(slot event (type STRING))
(slot action (type STRING))
(slot intention (type STRING))
(slot coping-strategy (type SYMBOL) (allowed-values PLANNING SEEK-INSTRUMENTAL-SUPPORT MAKE-AMENDS PROCRASTINATION RESIGNATION AVOIDANCE UNKNOWN) (default UNKNOWN)))

(deftemplate COPING::emotion-focused-coping-behavior
"Coping behaviors based on strategies made in terms of their focus on problems or emotions."
(slot task (type STRING))
(slot event (type STRING))
(slot action (type STRING))
(slot intention (type STRING))
(slot coping-strategy (type SYMBOL) (allowed-values SUPPRESS-OTHER-ACTIVITIES RESTRAINT SEEK-SOCIAL-EMOTIONAL-SUPPORT POSITIVE-REINTERPRETATION ACCEPTANCE DENIAL BEHAVIORAL-DISENGAGEMENT MENTAL-DISENGAGEMENT UNKNOWN) (default UNKNOWN)))

(deftemplate COPING::problem-focused-coping-behavior
"Coping behaviors based on strategies made in terms of their focus on problems or emotions."
(slot task (type STRING))
(slot event (type STRING))
(slot action (type STRING))
(slot intention (type STRING))
(slot coping-strategy (type SYMBOL) (allowed-values ACTIVE PLANNING SEEK-SOCIAL-INSTRUMENTAL-SUPPORT UNKNOWN) (default UNKNOWN)))

