package MetaInformation;

import MentalStates.Goal;
import MentalStates.MentalStates;
import jess.Fact;
import jess.Rete;

public class Events {

	public enum EVENT_TYPE{UTTERANCE, ACTION, EMOTION};
	
	private Fact belief;
	private Fact goalFact;
	private EVENT_TYPE eventType;
	
	public Events(Fact belief, Fact goalFact, EVENT_TYPE eventType) {
		this.belief    = belief;
		this.goalFact  = goalFact;
		this.eventType = eventType;
	}
	
	public EVENT_TYPE getEventType() {
		
		return eventType;
	}
	
	public Goal getEventRelatedGoal(Rete JessEngine, MentalStates ms) {
		
		return ms.getEventRelatedGoal(JessEngine, goalFact);
	}
	
	public Fact getEventRelatedGoalFact() {
		
		return goalFact;
	}
	
	public Fact getEventRelatedBelief() {
		
		return belief;
	}
}
