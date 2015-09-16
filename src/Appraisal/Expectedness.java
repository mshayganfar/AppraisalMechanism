package Appraisal;

import MentalGraph.*;
import MetaInformation.Events;
import jess.Fact;

public class Expectedness extends AppraisalProcesses{
	
	public enum EXPECTEDNESS {EXPECTED, UNEXPECTED};
	
	public EXPECTEDNESS isEventExpected(MentalGraph mentalGraph, Events event) {
		
		Fact eventGoal = collaboration.recognizeGoal(event);
		Fact graphGoal = mentalGraph.getGraphGoal();
		
		if(eventGoal == null)
			return EXPECTEDNESS.UNEXPECTED;
		
		if (eventGoal.equals(graphGoal))
			return EXPECTEDNESS.EXPECTED;
		else {
			if (!collaboration.isGoalAchieved(graphGoal))
				return EXPECTEDNESS.UNEXPECTED;
			else {
				Fact topLevelGoal = collaboration.getTopLevelGoal();
				
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
