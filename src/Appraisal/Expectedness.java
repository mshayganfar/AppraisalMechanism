package Appraisal;

import MentalStates.MentalStates;
import Turns.Turns;
import jess.Rete;

public class Expectedness {
	
	public boolean isEventExpected(Rete JessEngine, MentalStates mentalStates, Turns turn) {
		
		String strLastGoal = mentalStates.extractGoal(JessEngine, turn.getLastTurn());
		String stePreviousGoal = mentalStates.extractGoal(JessEngine, turn.getPreviousTurn());
		
		return true;
	}
}
