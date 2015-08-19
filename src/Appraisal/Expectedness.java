package Appraisal;

import java.util.List;

import Collaboration.Collaboration;
import MentalGraph.*;
import MentalGraph.MentalGraph.Edge;
import MentalStates.MentalStates;
import Turns.Turns;
import jess.Fact;
import jess.JessException;
import jess.Rete;

public class Expectedness extends AppraisalProcesses{
	
	public boolean isEventExpected(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, Fact beliefFact, Turns turn) {
		
		Fact factLastGoal = mentalStates.extractGoal(JessEngine, turn.getLastTurn());
		Fact factPreviousGoal = mentalStates.extractGoal(JessEngine, turn.getPreviousTurn());
		
		List<Edge> listLastPath     = mentalGraph.getShortestPath(beliefFact, factLastGoal);
		List<Edge> listPreviousPath = mentalGraph.getShortestPath(beliefFact, factPreviousGoal);
		
		try {
			if(factLastGoal.getSlotValue("goal").toString().equals(factPreviousGoal.getSlotValue("goal").toString())) {
				if(collaboration.isGoalAchieved(factPreviousGoal)) {
					
				}
				else
					return false;
			}
			else 
				return true;
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
