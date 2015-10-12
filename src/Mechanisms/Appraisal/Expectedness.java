package Mechanisms.Appraisal;

import MentalState.Goal;
import MetaInformation.Events;
import edu.wpi.disco.Disco;

public class Expectedness extends AppraisalProcesses{
	
	public enum EXPECTEDNESS {MOST_EXPECTED, EXPECTED, LESS_EXPECTED, LESS_UNEXPECTED, UNEXPECTED, MOST_UNEXPECTED};
	
	public EXPECTEDNESS isEventExpected(Events event) {

		Disco disco = mentalState.getDisco();
		Goal eventGoal = collaboration.recognizeGoal(event); // This needs to be changed!
		
		if (disco.getSegment().isInterruption()) {
			if (disco.getTop(disco.getFocus()).getType().isPathFrom(eventGoal.getPlan().getType()))
				return EXPECTEDNESS.UNEXPECTED;
			else
				return EXPECTEDNESS.MOST_UNEXPECTED;
		}
		else {
			if (!disco.isLastShift())
				return EXPECTEDNESS.MOST_EXPECTED;
			else
				return EXPECTEDNESS.EXPECTED;
		}
		
	}
}
