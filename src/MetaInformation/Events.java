package MetaInformation;

import MentalStates.Goal;
import MentalStates.MentalStates;
import jess.Fact;
import jess.Rete;

public class Events {

	public enum EVENT_TYPE{UTTERANCE, ACTION, EMOTION};
	
	private Fact beliefFact;
	private Fact goalFact;
	private Fact emotionFact;
	private EVENT_TYPE eventType;
	
	public Events(Fact belief, Fact goalFact, Fact emotionFact, EVENT_TYPE eventType) {
		this.beliefFact  = belief;
		this.goalFact    = goalFact;
		this.eventType   = eventType;
		this.emotionFact = emotionFact;
	}
	
	public EVENT_TYPE getEventType() {
		
		return eventType;
	}
	
	public Goal getEventRelatedGoal(Rete JessEngine, MentalStates ms) {
		
		return ms.getEventRelatedGoal(goalFact);
	}
	
	public Fact getEventRelatedGoalFact() {
		
		return goalFact;
	}
	
	public Fact getEventRelatedBelief() {
		
		return beliefFact;
	}
	
	public Fact getEventRelatedEmotion() {
		
		return emotionFact;
	}
}
