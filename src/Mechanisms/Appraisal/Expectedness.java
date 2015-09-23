package Mechanisms.Appraisal;

import MentalGraph.*;
import MentalStates.Goal;
import MetaInformation.Events;

public class Expectedness extends AppraisalProcesses{
	
	public enum EXPECTEDNESS {EXPECTED, UNEXPECTED};
	
	public EXPECTEDNESS isEventExpected(MentalGraph mentalGraph, Events event) {
		
		Goal eventGoal = collaboration.recognizeGoal(event);
		Goal graphGoal = mentalGraph.getGraphGoal();
		
		if(eventGoal == null)
			return EXPECTEDNESS.UNEXPECTED;
		
		if (eventGoal.equals(graphGoal))
			return EXPECTEDNESS.EXPECTED;
		else {
			if (!collaboration.isGoalAchieved(graphGoal))
				return EXPECTEDNESS.UNEXPECTED;
			else {
				Goal topLevelGoal = collaboration.getTopLevelGoal(event);
				
				if (!collaboration.isGoalAchieved(topLevelGoal)) {
					if (collaboration.doesContibute(eventGoal, topLevelGoal))
						return EXPECTEDNESS.EXPECTED;
					else
						return EXPECTEDNESS.UNEXPECTED;
				}
				else {
					if (collaboration.doesContibute(eventGoal, graphGoal))
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
