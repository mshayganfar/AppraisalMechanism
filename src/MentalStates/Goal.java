package MentalStates;

import Mechanisms.Mechanisms.AGENT;
import MetaInformation.Events;
import edu.wpi.cetask.Task;

public class Goal extends MentalStates{
	
	private Task goal;
	private int turn;
	private String id;
	private Events event;
	private AGENT agent;
	
	public Goal(Task goal, int turn, String id, Events event, AGENT agent) {
		this.goal  = goal;
		this.turn  = turn;
		this.id    = id;
		this.event = event;
		this.agent = agent;
	}
}
