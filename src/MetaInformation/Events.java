package MetaInformation;

import jess.Fact;

public class Events {

	public enum EVENT_TYPE{UTTERANCE, ACTION, EMOTION};
	
	private Fact goal;
	private EVENT_TYPE eventType;
	
	public Events(Fact goal, EVENT_TYPE eventType) {
		this.goal = goal;
		this.eventType = eventType;
	}
	
	public EVENT_TYPE getEventType() {
		
		return eventType;
	}
	
	public Fact getEventRelatedGoal() {
		
		return goal;
	}
}
