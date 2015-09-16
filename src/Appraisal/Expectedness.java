package Appraisal;

import Appraisal.Desirability.DESIRABILITY;
import Collaboration.Collaboration.FOCUS_STATUS;
import MentalGraph.*;
import MentalStates.MentalStates;
import MetaInformation.Events;
import MetaInformation.Turns;
import jess.Fact;
import jess.JessException;
import jess.Rete;

public class Expectedness extends AppraisalProcesses{
	
	public enum EXPECTEDNESS {EXPECTED, UNEXPECTED};
	
	public EXPECTEDNESS isEventExpected(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, Fact beliefFact, Turns turn, Events event) {
		
//		Fact factLastGoal = mentalStates.extractGoal(JessEngine, turn.getLastTurn());
//		Fact factPreviousGoal = mentalStates.extractGoal(JessEngine, turn.getPreviousTurn());
		
		try {
			
			if(!factLastGoal.getSlotValue("goal").toString().equals(factPreviousGoal.getSlotValue("goal").toString())) {
				if(collaboration.isGoalAchieved(factPreviousGoal)) {
					if(mentalGraph.getShortestPath(beliefFact, factLastGoal).size() == 0)
						return false;
					else {
						double dblLastPathUtility     = getPathUtility(JessEngine, mentalStates, mentalGraph, beliefFact, mentalStates.extractGoal(JessEngine, turn.getLastTurn()));
						double dblPreviousPathUtility = getPathUtility(JessEngine, mentalStates, mentalGraph, beliefFact, mentalStates.extractGoal(JessEngine, turn.getPreviousTurn()));
						if((dblLastPathUtility - dblPreviousPathUtility) >= getHumanEmotionalThreshold())
							return true;
						else
							return false;
					}
				}
				else
					return false;
			}
			else 
				return true;
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		Fact eventGoal = collaboration.recognizeGoal(event);
		Fact graphGoal = mentalGraph.getGraphGoal();
		
		if(eventGoal == null)
			return EXPECTEDNESS.UNEXPECTED;
		
		if (eventGoal.equals(graphGoal))
			return EXPECTEDNESS.EXPECTED;
		else {
			if (!collaboration.getGoalStatus(graphGoal).equals(FOCUS_STATUS.ACHIEVED))
				return EXPECTEDNESS.UNEXPECTED;
			else {
				Fact topLevelGoal = collaboration.getTopLevelGoal();
				if (!collaboration.getGoalStatus(topLevelGoal).equals(FOCUS_STATUS.ACHIEVED)) {
					if (collaboration.doesContibute(eventGoal, topLevelGoal))
						return EXPECTEDNESS.EXPECTED;
					else
						return EXPECTEDNESS.UNEXPECTED;
				}
				else {
					
				}
			}
		}
		
		return null;
	}
}
