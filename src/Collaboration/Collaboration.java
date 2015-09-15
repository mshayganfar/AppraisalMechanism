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
	
	public FOCUS_TYPE getFocusType() {
		
		return FOCUS_TYPE.PRIMITIVE;
	}
	
	public boolean isGoalAchieved(Fact factGoal) {
		
		return true;
	}
	
	public FOCUS_STATUS getFocusStatus() {
		
		return FOCUS_STATUS.ACHIEVED;
	}
	
	public TOP_LEVEL_TASK_STATUS getTopLevelTaskStatus() {
		
		return TOP_LEVEL_TASK_STATUS.ACHIEVED;
	}
	
	public TASK_PRECONDITION_STATUS getTaskPreconditionStatus() {
		
		return TASK_PRECONDITION_STATUS.SATISFIED;
	}
	
	public TASK_POSTCONDITION_STATUS getTaskPostconditionStatus() {
		
		return TASK_POSTCONDITION_STATUS.SATISFIED;
	}
	
	public String getTaskPreconditions(Fact factGoal) {
		
		return "Fake_Preconditions";
	}

	public RECIPE_APPLICABILITY getRecipeApplicability() {
		
		return RECIPE_APPLICABILITY.APPLICABLE;
	}
	
	public Boolean doesContibute(Fact intentionFact) {
		
		return true;
	}
	
	public List<Fact> getTaskContributers(Fact factNonprimitiveGoal) {
		
		List<Fact> contributerGoalList = new ArrayList<Fact>();
		
		// Extract all contributers here.
		
		return contributerGoalList;
	}
	
	public AGENT_TYPE getTaskResponsibleAgent(Fact factGoal) {
		
		return AGENT_TYPE.SELF;
	}
	
	public List<Fact> getPredecessors(Fact factGoal) {
		
		List<Fact> predecessorGoalList = new ArrayList<Fact>();
		
		// Extract all predecessor here.
		
		return predecessorGoalList;
	}
	
	public ArrayList getInputs(Fact factGoal) {
		
		ArrayList goalInputsList = new ArrayList();
		
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
