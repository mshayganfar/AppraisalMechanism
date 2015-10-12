package Mechanisms.Collaboration;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Plan.Status;
import edu.wpi.cetask.TaskClass.Input;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;

import Mechanisms.Mechanisms;
import MentalState.Goal;
import MentalState.MentalState;
import MetaInformation.Events;

public class Collaboration extends Mechanisms {

	public enum GOAL_STATUS{ACHIEVED, FAILED, PENDING, BLOCKED, INPROGRESS, INAPPLICABLE};
	public enum FOCUS_TYPE{PRIMITIVE, NONPRIMITIVE};
	public enum RECIPE_APPLICABILITY{APPLICABLE, INAPPLICABLE, UNKNOWN};
	
	private Disco disco;
	private Plan prevFocus;
	
	public Collaboration(MentalState mentalState) {
		super(mentalState);
		disco = mentalState.getDisco();
		prevFocus = disco.getFocus();
	}
	
	public Collaboration(String strAgent, String strUser) {
		disco = new Interaction(new Agent(strAgent),new User(strUser),null).getDisco();
		System.out.println("Collaboration started! Disco = " + disco);
	}
	
	public boolean isPlanAchieved(Plan plan) {
		
		//if (getGoalStatus(plan).equals(GOAL_STATUS.ACHIEVED))
		if (plan.isSucceeded())
			return true;
		else
			return false;
	}
	
	public boolean isGoalAchieved(Goal goal) {
		
		if (getGoalStatus(goal.getPlan()).equals(GOAL_STATUS.ACHIEVED))
			return true;
		else
			return false;
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
	
	public boolean isGoalLive(Goal goal) {
		
		if (goal.getPlan().isLive())
			return true;
		else
			return false;
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
		
		Plan plan = disco.getTop(disco.getFocus());
		String taskID = plan.getType()+"@"+Integer.toHexString(System.identityHashCode(plan));
		
		Goal goal = new Goal(plan, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		// Also change the place where needs to hold the goal. To be globally accessible.
		return goal;
	}
	
	public GOAL_STATUS getGoalStatus(Plan plan) {
		
		Status postCondStatus = plan.getStatus();
		
		if (isPlanAchieved(plan))
			return GOAL_STATUS.ACHIEVED;
		else if (postCondStatus.equals(Status.FAILED))
			return GOAL_STATUS.FAILED;
		else if (postCondStatus.equals(Status.IN_PROGRESS))
			return GOAL_STATUS.INPROGRESS;
		else if (postCondStatus.equals(Status.BLOCKED))
			return GOAL_STATUS.BLOCKED;
		else if (postCondStatus.equals(Status.PENDING))
			return GOAL_STATUS.PENDING;
		else
			return GOAL_STATUS.INAPPLICABLE;
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
	
	public void updateCollaboraitonState() {
		prevFocus = disco.getFocus();
	}
	
	public void test() {
		
//		Fact childFact;
//		try {
//			//childFact = new Fact("My Fake Child Goal", JessEngine);
//			//Fact parentFact  = new Fact("My Fake Parent Goal", JessEngine);
//			
//			//Events event = new Events(new Fact("My Fake Belief", JessEngine), childFact, EVENT_TYPE.UTTERANCE);
//			
//		} catch (JessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(this.getDisco().getFocus());
		System.out.println(this.getDisco().getFocus().getGoal());
		System.out.println(this.getDisco().getFocus().getParent().getGoal());
		
		Goal child   = new Goal(this.getDisco().getFocus(), 1, "testID", null, AGENT.BOTH);
		Goal parent  = new Goal(this.getDisco().getFocus(), 1, "testID", null, AGENT.BOTH);
		
		mentalState.assertGoal(1, "G1-1", null, AGENT.SELF, child, parent);
	}
}
