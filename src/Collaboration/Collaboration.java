package Collaboration;

import java.util.ArrayList;
import java.util.List;

import MetaInformation.Events;

import jess.Fact;
import jess.JessException;

public class Collaboration {

	public enum FOCUS_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum FOCUS_TYPE{PRIMITIVE, NONPRIMITIVE};
	public enum TOP_LEVEL_TASK_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum TASK_PRECONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum TASK_POSTCONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum RECIPE_APPLICABILITY{APPLICABLE, INAPPLICABLE, UNKNOWN};
	public enum AGENT_TYPE{SELF, OTHER, BOTH, NONE};
	
	public FOCUS_TYPE getGoalType() {
		
		return FOCUS_TYPE.PRIMITIVE;
	}
	
	public boolean isGoalAchieved(Fact factGoal) {
		
		return true;
	}
	
	public boolean isGoalFocused(Fact factGoal) {
		
		return true;
	}
	
	public boolean isInputAvailable(String strInput) {
		
		return true;
	}
	
	public FOCUS_STATUS getGoalStatus(Fact graphGoal) {
		
		return FOCUS_STATUS.ACHIEVED;
	}
	
	public Fact getTopLevelGoal() {
		
		try {
			return new Fact("Fake Goal", null);
		} catch (JessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TASK_PRECONDITION_STATUS getGoalPreconditionStatus(Fact factGoal) {
		
		return TASK_PRECONDITION_STATUS.SATISFIED;
	}
	
	public TASK_POSTCONDITION_STATUS getGoalPostconditionStatus(Fact factGoal) {
		
		return TASK_POSTCONDITION_STATUS.SATISFIED;
	}
	
	public String getGoalPreconditions(Fact factGoal) {
		
		return "Fake_Preconditions";
	}

	public RECIPE_APPLICABILITY getRecipeApplicability() {
		
		return RECIPE_APPLICABILITY.APPLICABLE;
	}
	
	public Boolean doesContibute(Fact eventGoal, Fact graphGoal) {
		
		return true;
	}
	
	public List<Fact> getContributingGoals(Fact factNonprimitiveGoal) {
		
		List<Fact> contributerGoalList = new ArrayList<Fact>();
		
		// Extract all contributers here.
		
		return contributerGoalList;
	}
	
	public AGENT_TYPE getResponsibleAgent(Fact factGoal) {
		
		return AGENT_TYPE.SELF;
	}
	
	public List<Fact> getPredecessors(Fact factGoal) {
		
		List<Fact> predecessorGoalList = new ArrayList<Fact>();
		
		// Extract all predecessor here.
		
		return predecessorGoalList;
	}
	
	public ArrayList<String> getInputs(Fact factGoal) {
		
		ArrayList<String> goalInputsList = new ArrayList<String>();
		
		// Extract all inputs here.
		
		return goalInputsList;
	}
	
	public Fact recognizeGoal(Events event) {
		try {
			return new Fact("Fake Goal", null);
		} catch (JessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
