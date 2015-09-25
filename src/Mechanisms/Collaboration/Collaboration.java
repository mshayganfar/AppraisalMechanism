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
	
	public Boolean isGoalAchieved(Goal goal) {
		
		return goal.getGoal().isAchieved();
	}
	
	public boolean isGoalFocused(Goal goal) {
		
		if (getDisco().getFocus().equals(goal.getGoal()))
			return true;
		else
			return false;
	}
	
	public boolean isInputAvailable(Goal goal) {
		
		List<Input> inputList = goal.getGoal().getType().getInputs();
		
		for (int i=0 ; i < inputList.size() ; i++) {
			if(!goal.getGoal().getGoal().isDefinedSlot(inputList.get(i).toString()))
				return false;
		}
		
		return true;
	}
	
	public Disco getDisco() { return disco; }
	
	public FOCUS_STATUS getGoalStatus(Goal goal) {
		
		return FOCUS_STATUS.ACHIEVED;
	}
	
	public FOCUS_TYPE getGoalType(Goal goal) {
		
		return FOCUS_TYPE.PRIMITIVE;
	}
	
	public Goal getFocusedGoal(Events event) {
		Plan task       = disco.getFocus();
		Plan parentTask = disco.getFocus().getParent();
		String taskID   = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
		
		Goal goal = new Goal(task, parentTask, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		
		return goal;
	}
	
	public Goal getTopLevelGoal(Events event) {
		
		Plan task = disco.getTop(disco.getFocus());
		String taskID = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
		
		Goal goal = new Goal(task, null, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		// Also change the place where needs to hold the goal. To be globally accessible.
		return goal;
	}
	
	public TASK_PRECONDITION_STATUS getGoalPreconditionStatus(Goal goal) {
		
		if (goal.getGoal().isLive())
			return TASK_PRECONDITION_STATUS.SATISFIED;
		else
			return TASK_PRECONDITION_STATUS.UNSATISFIED;
	}
	
	public TASK_POSTCONDITION_STATUS getGoalPostconditionStatus(Goal goal) {
		
		if (goal.getGoal().isDone())
			return TASK_POSTCONDITION_STATUS.SATISFIED;
		else
			return TASK_POSTCONDITION_STATUS.UNSATISFIED;
	}
	
	public RECIPE_APPLICABILITY getRecipeApplicability(Goal goal) {
		
		return RECIPE_APPLICABILITY.APPLICABLE;
	}
	
	public Boolean doesContibute(Goal contributingGoal, Goal contributedGoal) {
		
		return true;
	}
	
	public List<Goal> getContributingGoals(Events event, Plan parentGoal) {
		
		Plan task;
		String taskID;
		
		List<Goal> contributerGoalList = new ArrayList<Goal>();
		List<Plan> contributerPlanList = disco.getPlan(parentGoal.getType()).getChildren();
		
		for (int i=0 ; i < contributerPlanList.size() ; i++) {
			task   = contributerPlanList.get(i);
			taskID = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
			contributerGoalList.add(new Goal(task, parentGoal, turn.value(), taskID, event, AGENT.SELF)); // Change the agent type by reading the value from Disco.
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
	
	public List<String> getInputs(Goal goal) {
		
		List<String> goalInputsList = new ArrayList<String>();
		
		// Extract all inputs here.
		
		return goalInputsList;
	}
	
	public Goal recognizeGoal(Events event) {
		return null; // This needs to be implemented.............................................
	}
}
