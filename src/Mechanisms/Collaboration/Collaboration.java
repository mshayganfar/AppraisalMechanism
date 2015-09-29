package Mechanisms.Collaboration;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.TaskClass.Input;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;

import Mechanisms.Mechanisms;
import MentalStates.Goal;
import MentalStates.MentalStates;
import MetaInformation.Events;

public class Collaboration extends Mechanisms {

	public enum FOCUS_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum FOCUS_TYPE{PRIMITIVE, NONPRIMITIVE};
	public enum TOP_LEVEL_TASK_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum TASK_PRECONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum TASK_POSTCONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum RECIPE_APPLICABILITY{APPLICABLE, INAPPLICABLE, UNKNOWN};
	
	private Disco disco;
	
	public Collaboration(MentalStates mentalStates, Disco disco) {
		super(mentalStates, disco);
		this.disco = disco;
	}
	
	public Collaboration(String strAgent, String strUser) {
		disco = new Interaction(new Agent(strAgent),new User(strUser),null).getDisco();
		System.out.println("Collaboration started! Disco = " + disco);
	}
	
	public Boolean isPlanAchieved(Plan plan) {
		
		return plan.isDone(); // Needs to be revised.
	}
	
	public Boolean isGoalAchieved(Goal goal) {
		
		return goal.getPlan().isDone(); // Needs to be revised.
	}
	
	public boolean isGoalFocused(Goal goal) {
		
		if (getDisco().getFocus().equals(goal.getPlan()))
			return true;
		else
			return false;
	}
	
	public boolean isInputAvailable(Goal goal, Input input) {
		
		if(!goal.getPlan().getGoal().isDefinedSlot(input.getName()))
			return false;
		
		return true;
	}
	
	public Disco getDisco() { return disco; }
	
	public FOCUS_TYPE getGoalType(Goal goal) {
		
		if(goal.getPlan().getGoal().isPrimitive())
			return FOCUS_TYPE.PRIMITIVE;
		else
			return FOCUS_TYPE.NONPRIMITIVE;
	}
	
	public Goal getFocusedGoal(Events event) {
		Plan task       = disco.getFocus();
		String taskID   = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
		
		Goal goal = new Goal(task, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		
		return goal;
	}
	
	public Goal getTopLevelGoal(Events event) {
		
		Plan task = disco.getTop(disco.getFocus());
		String taskID = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
		
		Goal goal = new Goal(task, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		// Also change the place where needs to hold the goal. To be globally accessible.
		return goal;
	}
	
	public TASK_PRECONDITION_STATUS getGoalPreconditionStatus(Goal goal) {
		
		if (goal.getPlan().isLive())
			return TASK_PRECONDITION_STATUS.SATISFIED;
		else
			return TASK_PRECONDITION_STATUS.UNSATISFIED;
	}
	
	public TASK_POSTCONDITION_STATUS getGoalPostconditionStatus(Goal goal) {
		
		if (goal.getPlan().isDone())
			return TASK_POSTCONDITION_STATUS.SATISFIED;
		else
			return TASK_POSTCONDITION_STATUS.UNSATISFIED;
	}
	
	public boolean doesContribute(Goal contributingGoal, Goal contributedGoal) {
		
		if (contributedGoal.getPlan().equals(contributingGoal.getPlan().getParent()))
			return true;
		else
			return false;
	}
	
	public List<Goal> getContributingGoals(Events event, Plan parentGoal) {
		
		Plan goal;
		String id;
		
		List<Goal> contributerGoalList = new ArrayList<Goal>();
		List<Plan> contributerPlanList = parentGoal.getChildren();
		
		for (int i=0 ; i < contributerPlanList.size() ; i++) {
			goal = contributerPlanList.get(i);
			id = goal.getType()+"@"+Integer.toHexString(System.identityHashCode(goal));
			contributerGoalList.add(new Goal(goal, turn.value(), id, event, AGENT.SELF)); // Change the agent type by reading the value from Disco.
		}
		
		return contributerGoalList;
	}
	
	public AGENT getResponsibleAgent(Goal goal) {
		
		if(!goal.getPlan().getGoal().isPrimitive())
			return AGENT.BOTH;
		else if (goal.getPlan().getGoal().getExternal())
			return AGENT.SELF;
		else
			return AGENT.OTHER;
	}
	
	public List<Input> getInputs(Goal goal) {
		
		return goal.getPlan().getGoal().getType().getInputs();
	}
	
	public Goal recognizeGoal(Events event) {
		return null; // This needs to be implemented.............................................
	}
	
	public RECIPE_APPLICABILITY getRecipeApplicability(Goal goal) { return RECIPE_APPLICABILITY.APPLICABLE; }
	
	public List<Plan> getPredecessors(Goal goal) {
		
		return goal.getPlan().getPredecessors();
	}
}
