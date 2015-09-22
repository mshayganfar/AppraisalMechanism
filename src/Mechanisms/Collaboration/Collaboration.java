package Mechanisms.Collaboration;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Task;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;

import Mechanisms.Mechanisms;
import MentalStates.Goal;
import MentalStates.MentalStates;
import MetaInformation.Events;

import jess.Fact;
import jess.JessException;

public class Collaboration extends Mechanisms {

	public enum FOCUS_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum FOCUS_TYPE{PRIMITIVE, NONPRIMITIVE};
	public enum TOP_LEVEL_TASK_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum TASK_PRECONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum TASK_POSTCONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum RECIPE_APPLICABILITY{APPLICABLE, INAPPLICABLE, UNKNOWN};
	
	private Disco disco;
	
	public Disco getDisco() { return disco; }
	
	public Collaboration(MentalStates ms, Disco disco) {
		super(ms, disco);
		this.disco = disco;
	}
	
	public Collaboration(String strAgent, String strUser) {
		disco = new Interaction(new Agent(strAgent),new User(strUser),null).getDisco();
		System.out.println("Collaboration started! Disco = " + disco);
	}
	
	public Boolean isGoalAchieved(Goal goal) {
		
		return goal.getGoal().isAchieved();
	}
	
	public Boolean isGoalFocused(Goal goal) {
		
		return true;
	}
	
	public Boolean isInputAvailable(String strInput) {
		
		return true;
	}
	
	public FOCUS_STATUS getGoalStatus(Goal graphGoal) {
		
		return FOCUS_STATUS.ACHIEVED;
	}
	
	public FOCUS_TYPE getGoalType() {
		
		return FOCUS_TYPE.PRIMITIVE;
	}
	
	private Goal getFocusedGoal(Events event) {
		Task task       = disco.getFocus().getGoal();
		Task parentTask = disco.getFocus().getParent().getGoal();
		String taskID   = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
		
		Goal goal = new Goal(task, parentTask, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		
		return goal;
	}
	
	public Goal getTopLevelGoal(Events event) {
		
		Task task = disco.getTop(disco.getFocus()).getGoal();
		String taskID = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
		
		Goal goal = new Goal(task, null, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		// Also change the place where needs to hold the goal. To be globally accessible.
		return goal;
	}
	
	public TASK_PRECONDITION_STATUS getGoalPreconditionStatus(Goal goal) {
		
		if (goal.getGoal().isApplicable() == null)
			return TASK_PRECONDITION_STATUS.UNKNOWN;
		else if (goal.getGoal().isApplicable())
			return TASK_PRECONDITION_STATUS.SATISFIED;
		else
			return TASK_PRECONDITION_STATUS.UNSATISFIED;
	}
	
	public TASK_POSTCONDITION_STATUS getGoalPostconditionStatus(Goal goal) {
		
		if (goal.getGoal().isApplicable() == null)
			return TASK_POSTCONDITION_STATUS.UNKNOWN;
		else if (goal.getGoal().isApplicable())
			return TASK_POSTCONDITION_STATUS.SATISFIED;
		else
			return TASK_POSTCONDITION_STATUS.UNSATISFIED;
	}
	
	public String getGoalPreconditions(Fact factGoal) {
		
		return "Fake_Preconditions";
	}

	public RECIPE_APPLICABILITY getRecipeApplicability() {
		
		return RECIPE_APPLICABILITY.APPLICABLE;
	}
	
	public Boolean doesContibute(Goal eventGoal, Goal graphGoal) {
		
		return true;
	}
	
	public List<Goal> getContributingGoals(Events event, Task parentTask) {
		
		Task task;
		String taskID;
		
		List<Goal> contributerGoalList = new ArrayList<Goal>();
		List<Plan> contributerPlanList = disco.getPlan(parentTask.getType()).getChildren();
		
		for (int i=0 ; i < contributerPlanList.size() ; i++) {
			task   = contributerPlanList.get(i).getGoal();
			taskID = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
			contributerGoalList.add(new Goal(task, parentTask, turn.value(), taskID, event, AGENT.SELF)); // Change the agent type by reading the value from Disco.
		}
		
		return contributerGoalList;
	}
	
	public AGENT getResponsibleAgent(Goal goal) {
		
		return AGENT.SELF;
	}
	
	public List<Goal> getPredecessors(Goal goal) {
		
		List<Goal> predecessorGoalList = new ArrayList<Goal>();
		
		// Extract all predecessor here.
		
		return predecessorGoalList;
	}
	
	public ArrayList<String> getInputs(Goal factGoal) {
		
		ArrayList<String> goalInputsList = new ArrayList<String>();
		
		// Extract all inputs here.
		
		return goalInputsList;
	}
	
	public Goal recognizeGoal(Events event) {
		return null; // This needs to be implemented.............................................
	}
}
