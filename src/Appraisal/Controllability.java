package Appraisal;

import java.util.ArrayList;
import java.util.List;

import jess.Fact;
import jess.JessException;

import MentalGraph.MentalGraph;
import MetaInformation.Events;

public class Controllability extends AppraisalProcesses{

	public boolean isEventControllable(MentalGraph mentalGraph, Events event) {
		
		double dblAgency       = getAgencyValue(mentalGraph, event);
		double dblAutonomy     = getAutonomyValue(mentalGraph, event);
		double dblPredecessors = checkSucceededPredecessorsRatio(event);
		double dblInputs       = checkAvailableInputRatio(event);
		
		double utilityValue = (double)((dblAgency * getAgencyWeight()) + (dblAutonomy * getAutonomyWeight()) + 
						(dblPredecessors * getPredecessorRatioWeight()) + (dblInputs * getInputRatioWeight()))
						/(getAgencyWeight() + getAutonomyWeight() + getPredecessorRatioWeight() + getInputRatioWeight());
		
		if(utilityValue >= getHumanEmotionalThreshold())
			return true;
		else
			return false;
	}
	
	// Agency: The capacity, condition, or state of acting or of exerting power.
	private Double getAgencyValue(MentalGraph mentalGraph, Events event) {
		
		Fact pathMotive = null;
		
		Fact eventBelief = event.getEventRelatedBelief();
		Fact eventGoal   = event.getEventRelatedGoal();
		
		if(eventGoal == null)
			return 0.0;
		
		if(mentalGraph.getShortestPath(eventBelief, eventGoal).size() != 0)
		{
			pathMotive = mentalGraph.getPathMotive(mentalGraph.getShortestPathVertices(eventBelief, eventGoal));
			
			if (pathMotive != null) {
				try {
					if (pathMotive.getSlotValue("motive-type").equals("INTERNAL"))
						return 1.0;
					else
						return 0.0;
				} catch (JessException e) {
					e.printStackTrace();
				}
			}
			else 
				return 0.0;
		}
		else
			return 0.0;
		
		return 0.0;
	}
	
	// Autonomy: The quality or state of being self-governing. Self-directing freedom or self-governing state.
	private Double getAutonomyValue(MentalGraph mentalGraph, Events event) {
		
		double dblSelfCounter = 0;
		
		Fact eventGoal = event.getEventRelatedGoal();
		
		if(eventGoal == null)
			return 0.0;
		
		List<Fact> taskContributersList = collaboration.getContributingGoals(eventGoal);
		
		for (int i = 0; i < taskContributersList.size() ; i++)
			if(collaboration.getResponsibleAgent(taskContributersList.get(i)).equals("SELF"))
				dblSelfCounter++;
		
		return ((double)dblSelfCounter/taskContributersList.size());
	}
	
	private Double checkSucceededPredecessorsRatio(Events event) {
		
		double dblSucceededPredecessorCounter = 0.0;
		
		Fact eventGoal = event.getEventRelatedGoal();
		
		if(eventGoal == null)
			return 0.0;
		
		List<Fact> predecessorGoalList = collaboration.getPredecessors(eventGoal);
		
		if(predecessorGoalList.size() > 0) {
			for (int i = 0; i < predecessorGoalList.size() ; i++) {
				if(collaboration.isGoalAchieved(predecessorGoalList.get(i)))
					dblSucceededPredecessorCounter++;
			}
			
			return (double)dblSucceededPredecessorCounter/predecessorGoalList.size();
		}
		else
			return dblSucceededPredecessorCounter;
	}
	
	private Double checkAvailableInputRatio(Events event) {
		
		double dblAvailableInputCounter = 0.0;
		
		ArrayList<String> goalInputsList = collaboration.getInputs(event.getEventRelatedGoal());
		
		if(goalInputsList.size() > 0) {
			for (int i = 0; i < goalInputsList.size() ; i++) {
				if (!goalInputsList.get(i).equals(null))
					if(collaboration.isInputAvailable(goalInputsList.get(i).toString()))
						dblAvailableInputCounter++;
			}
			return ((double)dblAvailableInputCounter/goalInputsList.size());
		}
		else
			return dblAvailableInputCounter;
		
	}
	
	// Min = 0.0 and Max = 1.0
	private double getAgencyWeight()           { return 1.0; }
	private double getAutonomyWeight()         { return 1.0; }
	private double getPredecessorRatioWeight() { return 1.0; }
	private double getInputRatioWeight()       { return 1.0; }
}
