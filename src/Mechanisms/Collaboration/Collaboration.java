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

	public enum TASK_STATUS{DONE, FAILED, PENDING, BLOCKED, INPROGRESS, FUTURE};
	public enum FOCUS_TYPE{PRIMITIVE, NONPRIMITIVE};
	public enum TOP_LEVEL_TASK_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum TASK_PRECONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum TASK_POSTCONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum RECIPE_APPLICABILITY{APPLICABLE, INAPPLICABLE, UNKNOWN};
	
	private Disco disco;
	
	public Collaboration(MentalStates mentalState) {
		super(mentalState);
		disco = mentalState.getDisco();
	}
	
	public Collaboration(String strAgent, String strUser) {
		disco = new Interaction(new Agent(strAgent),new User(strUser),null).getDisco();
		System.out.println("Collaboration started! Disco = " + disco);
	}
	
	public Boolean isPlanAchieved(Plan plan) {
		
		if (getGoalPostconditionStatus(plan).equals(TASK_POSTCONDITION_STATUS.SATISFIED))
			return true;
		else if (getGoalPostconditionStatus(plan).equals(TASK_POSTCONDITION_STATUS.UNSATISFIED))
			return false;
		else
			return null;
	}
	
	public Boolean isGoalAchieved(Goal goal) {
		
		if (getGoalPostconditionStatus(goal.getPlan()).equals(TASK_POSTCONDITION_STATUS.SATISFIED))
			return true;
		else if (getGoalPostconditionStatus(goal.getPlan()).equals(TASK_POSTCONDITION_STATUS.UNSATISFIED))
			return false;
		else
			return null;
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
		
		Plan task = disco.getTop(disco.getFocus());
		String taskID = task.getType()+"@"+Integer.toHexString(System.identityHashCode(task));
		
		Goal goal = new Goal(task, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		// Also change the place where needs to hold the goal. To be globally accessible.
		return goal;
	}
	
	public TASK_STATUS getGoalPostconditionStatus(Plan plan) {
		
//		if (plan.isPrimitive()) {
//			if (plan.isO) {
//				if (plan.isSucceeded()) {
//					return TASK_STATUS.ACHIEVED;
//				}
//				else if (plan.isFailed()) {
//					return TASK_STATUS.FAILED;
//				}
//				else if (!plan.isSucceeded() && !plan.isFailed()) {
//					return TASK_STATUS.UNKNOWN;
//				}
//			}
//		}
//		else {
//			if (plan.isComplete()) {
//				if (plan.isSucceeded()) {
//					return TASK_STATUS.ACHIEVED;
//				}
//				else if (plan.isFailed()) {
//					return TASK_STATUS.FAILED;
//				}
//				else if (!plan.isSucceeded() && !plan.isFailed()) {
//					return TASK_STATUS.UNKNOWN;
//				}
//				else if (plan.isStarted() && !plan.isDone() && !plan.isFailed()) {
//					return TASK_STATUS.INPROGRESS;
//				}
//			}
			
			if (plan.isDone())
				return TASK_STATUS.DONE;
			else if (plan.isFailed())
				return TASK_STATUS.FAILED;
			else if (plan.isPrimitive())
				return TASK_STATUS.FUTURE;
			else if(plan.isStarted())
				return TASK_STATUS.INPROGRESS;
			else
				return TASK_STATUS.FUTURE;
			
//			return plan.isDone() ? TASK_STATUS.ACHIEVED :
//			     plan.isFailed() ? TASK_STATUS.FAILED :
//			     // must not have been executed yet
//			     plan.isPrimitive() ? TASK_STATUS.UNKNOWN :
//			     plan.isStarted() ? TASK_STATUS.INPROGRESS :
//			    	 TASK_STATUS.UNKNOWN;
//		}
		
//		return TASK_STATUS.UNKNOWN;
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
	
	public TASK_PRECONDITION_STATUS getGoalPreconditionStatus(Goal goal) {
		
		return TASK_PRECONDITION_STATUS.SATISFIED;
	}
	
	public RECIPE_APPLICABILITY getRecipeApplicability(Goal goal) { return RECIPE_APPLICABILITY.APPLICABLE; }
	
	public List<Plan> getPredecessors(Goal goal) {
		
		return goal.getPlan().getPredecessors();
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
