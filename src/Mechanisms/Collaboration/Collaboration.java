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
import Mechanisms.Appraisal.Expectedness.EXPECTEDNESS;
import MentalStates.Goal;
import MentalStates.MentalStates;
import MetaInformation.Events;

public class Collaboration extends Mechanisms {

	public enum TASK_STATUS{ACHIEVED, FAILED, PENDING, BLOCKED, INPROGRESS, INAPPLICABLE};
	public enum FOCUS_TYPE{PRIMITIVE, NONPRIMITIVE};
	public enum TOP_LEVEL_TASK_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum TASK_PRECONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum TASK_POSTCONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum RECIPE_APPLICABILITY{APPLICABLE, INAPPLICABLE, UNKNOWN};
	
	private Disco disco;
	private Plan prevFocus;
	
	public Collaboration(MentalStates mentalState) {
		super(mentalState);
		disco = mentalState.getDisco();
		prevFocus = disco.getFocus();
	}
	
	public Collaboration(String strAgent, String strUser) {
		disco = new Interaction(new Agent(strAgent),new User(strUser),null).getDisco();
		System.out.println("Collaboration started! Disco = " + disco);
	}
	
	public Boolean isPlanAchieved(Plan plan) {
		
		if (getGoalStatus(plan).equals(TASK_POSTCONDITION_STATUS.SATISFIED))
			return true;
		else if (getGoalStatus(plan).equals(TASK_POSTCONDITION_STATUS.UNSATISFIED))
			return false;
		else
			return null;
	}
	
	public Boolean isGoalAchieved(Goal goal) {
		
		if (getGoalStatus(goal.getPlan()).equals(TASK_POSTCONDITION_STATUS.SATISFIED))
			return true;
		else if (getGoalStatus(goal.getPlan()).equals(TASK_POSTCONDITION_STATUS.UNSATISFIED))
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
		
		Plan plan = disco.getTop(disco.getFocus());
		String taskID = plan.getType()+"@"+Integer.toHexString(System.identityHashCode(plan));
		
		Goal goal = new Goal(plan, turn.value(), taskID, event, AGENT.SELF); // Change the agent type by reading the value from Disco.
		// Also change the place where needs to hold the goal. To be globally accessible.
		return goal;
	}
	
	public TASK_STATUS getGoalStatus(Plan plan) {
		
		Status postCondStatus = plan.getStatus();
		
		if (isAchieved(plan))
			return TASK_STATUS.ACHIEVED;
		else if (postCondStatus.equals(Status.FAILED))
			return TASK_STATUS.FAILED;
		else if (postCondStatus.equals(Status.IN_PROGRESS))
			return TASK_STATUS.INPROGRESS;
		else if (postCondStatus.equals(Status.BLOCKED))
			return TASK_STATUS.BLOCKED;
		else if (postCondStatus.equals(Status.PENDING))
			return TASK_STATUS.PENDING;
		else
			return TASK_STATUS.INAPPLICABLE;
	}
	
	private boolean isAchieved(Plan plan) {
		
		if (plan.isSucceeded())
			return true;
		else
			return false;
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
	
	public EXPECTEDNESS isPlanExpected(Goal currentGoal, Goal eventGoal) {
		
		List<Plan> goalSuccessorList   = currentGoal.getPlan().getSuccessors();
		List<Plan> parentSuccessorList = currentGoal.getPlan().getParent().getSuccessors();
		
		if (disco.getFocus().equals(prevFocus)) {
			if (goalSuccessorList.size() > 0) {
				for (int i=0 ; i < goalSuccessorList.size() ; i++)
					if (goalSuccessorList.get(i).equals(eventGoal.getPlan()))
						if(goalSuccessorList.get(i).isLive())
							return EXPECTEDNESS.MOST_EXPECTED;
			}
			else if (parentSuccessorList.size() > 0) {
				for (int i=0 ; i < parentSuccessorList.size() ; i++)
					if (parentSuccessorList.get(i).equals(eventGoal.getPlan()))
						if(parentSuccessorList.get(i).isLive())
							return EXPECTEDNESS.MOST_EXPECTED;
			}
			else
				return EXPECTEDNESS.UNEXPECTED;
		}
		else {
			if (eventGoal.getPlan().getType().isPathFrom(disco.getTop(prevFocus).getType())) {
				if (eventGoal.getPlan().isLive())
					return EXPECTEDNESS.LESS_EXPECTED;
				else
					return EXPECTEDNESS.UNEXPECTED;
			}
			if (disco.getSegment().isInterruption()){
				if (eventGoal.getPlan().getType().isPathFrom(disco.getTop(prevFocus).getType()))
					return EXPECTEDNESS.UNEXPECTED;
				else
					return EXPECTEDNESS.MOST_UNEXPECTED;
			}
		}
		
		return EXPECTEDNESS.UNEXPECTED;
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
