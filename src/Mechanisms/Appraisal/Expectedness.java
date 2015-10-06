package Mechanisms.Appraisal;

import MentalState.Goal;
import MetaInformation.Events;

public class Expectedness extends AppraisalProcesses{
	
	public enum EXPECTEDNESS {MOST_EXPECTED, EXPECTED, LESS_EXPECTED, LESS_UNEXPECTED, UNEXPECTED, MOST_UNEXPECTED};
	
	public EXPECTEDNESS isEventExpected(Events event) {

		Goal eventGoal = collaboration.recognizeGoal(event);
		Goal graphGoal = mentalState.getMentalGraph().getGraphGoal();
		
		if(eventGoal == null)
			return EXPECTEDNESS.MOST_UNEXPECTED;
		
		if (eventGoal.equals(graphGoal))
			return EXPECTEDNESS.EXPECTED;
		else {
			if (!collaboration.isGoalAchieved(graphGoal))
				return EXPECTEDNESS.UNEXPECTED;
			else {
				Goal topLevelGoal = collaboration.getTopLevelGoal(event);
				
				if (!collaboration.isGoalAchieved(topLevelGoal)) {
					if (collaboration.doesContribute(eventGoal, topLevelGoal))
						return EXPECTEDNESS.EXPECTED;
					else
						return EXPECTEDNESS.UNEXPECTED;
				}
				else {
					if (collaboration.doesContribute(eventGoal, graphGoal))
						return EXPECTEDNESS.EXPECTED;
					else {
						if (collaboration.isGoalFocused(eventGoal))
							return EXPECTEDNESS.EXPECTED;
						else
							return EXPECTEDNESS.UNEXPECTED;
					}
				}
			}
		}
	}
}
