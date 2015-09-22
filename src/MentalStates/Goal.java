package MentalStates;

import jess.Fact;
import jess.JessException;
import jess.RU;
import jess.Value;
import Mechanisms.Mechanisms.AGENT;
import MetaInformation.Events;
import edu.wpi.cetask.Task;

public class Goal extends MentalStates{
	
	private int turn;
	private String id;
	private Events event;
	private AGENT agent;
	private Task goal;
	private Task parent;
	
	public Goal(Task goal, Task parent, int turn, String id, Events event, AGENT agent) {
		this.turn   = turn;
		this.id     = id;
		this.event  = event;
		this.agent  = agent;
		this.goal   = goal;
		this.parent = parent;
	}
	
	public Task getGoal() {
		return this.goal;
	}
}
