package Collaboration;

import jess.Fact;

public class Collaboration {

	public enum FOCUS_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum TOP_LEVEL_TASK_STATUS{ACHIEVED, BLOCKED, INPROGRESS, UNKNOWN};
	public enum TASK_PRECONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum TASK_POSTCONDITION_STATUS{SATISFIED, UNSATISFIED, UNKNOWN};
	public enum RECIPE_APPLICABILITY{APPLICABLE, INAPPLICABLE, UNKNOWN};
	
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
	
	public RECIPE_APPLICABILITY getRecipeApplicability() {
		
		return RECIPE_APPLICABILITY.APPLICABLE;
	}
	
	public Boolean doesContibute(Fact intentionFact) {
		
		return true;
	}
}
