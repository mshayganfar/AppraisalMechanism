package MentalStates;

import Mechanisms.Mechanisms.AGENT;
import MetaInformation.Events;
import edu.wpi.cetask.Plan;

public class Goal extends MentalStates{
	
	private int turn;
	private String id;
	private Events event;
	private AGENT agent;
	private Plan plan;
	
	public Goal(Plan goal, int turn, String id, Events event, AGENT agent) {
		this.turn   = turn;
		this.id     = id;
		this.event  = event;
		this.agent  = agent;
		this.plan   = goal;
	}
	
	public Plan getPlan() {
		return this.plan;
	}
}
