package Appraisal;

import MentalGraph.*;
import MentalStates.MentalStates;
import MetaInformation.Turns;
import jess.Fact;
import jess.JessException;
import jess.Rete;

public class Expectedness extends AppraisalProcesses{
	
	public Boolean isEventExpected(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, Fact beliefFact, Turns turn) {
		
		Fact factLastGoal = mentalStates.extractGoal(JessEngine, turn.getLastTurn());
		Fact factPreviousGoal = mentalStates.extractGoal(JessEngine, turn.getPreviousTurn());
		
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
		
		return null;
	}
}
