package Appraisal;

import java.util.ArrayList;
import java.util.List;

import jess.Fact;
import jess.JessException;

import Collaboration.Collaboration.AGENT_TYPE;
import Collaboration.Collaboration.FOCUS_TYPE;
import MentalGraph.MentalGraph;
import MetaInformation.Events;

public class Controllability extends AppraisalProcesses{

	public boolean isEventControllable(MentalGraph mentalGraph,Events event) {
		
		double dblAgency       = getAgencyValue(event);
		double dblAutonomy     = getAutonomyValue(mentalGraph, event);
		double dblPredecessors = checkSucceededPredecessorsRatio(event);
		
		return true;
	}
	
	// Agency: The capacity, condition, or state of acting or of exerting power.
	private Double getAgencyValue(Events event) {
		
		int intSelfResponsibilityCounter = 0;
		int intOtherResponsibilityCounter = 0;
		int intBothResponsibilityCounter = 0;
		int intNoneResponsibilityCounter = 0;
		
		AGENT_TYPE responsibleAgent;
		
		if (collaboration.getFocusType().equals(FOCUS_TYPE.PRIMITIVE)) {
			responsibleAgent = collaboration.getTaskResponsibleAgent(event.getEventRelatedGoal());
			if (responsibleAgent.equals(AGENT_TYPE.SELF))
				return 1.0;
			else if (responsibleAgent.equals(AGENT_TYPE.BOTH))
				return 0.5;
			else if ((responsibleAgent.equals(AGENT_TYPE.OTHER)) || (responsibleAgent.equals(AGENT_TYPE.NONE)))
				return 0.0;
		}
		else if (collaboration.getFocusType().equals(FOCUS_TYPE.NONPRIMITIVE)){
			List<Fact> taskContributersList = collaboration.getTaskContributers(event.getEventRelatedGoal());
			
			for (int i = 0 ; i < taskContributersList.size() ; i++) {
				responsibleAgent = collaboration.getTaskResponsibleAgent(taskContributersList.get(i));
				if (responsibleAgent.equals(AGENT_TYPE.SELF))
					intSelfResponsibilityCounter++;
				else if (responsibleAgent.equals(AGENT_TYPE.OTHER))
					intOtherResponsibilityCounter++;
				else if (responsibleAgent.equals(AGENT_TYPE.BOTH))
					intBothResponsibilityCounter++;
				else if (responsibleAgent.equals(AGENT_TYPE.NONE))
					intNoneResponsibilityCounter++;
			}
			return (double)(intSelfResponsibilityCounter + (double)(intBothResponsibilityCounter/2))/
						   (intOtherResponsibilityCounter + (double)(intBothResponsibilityCounter/2) + intNoneResponsibilityCounter);
		}
		
		return null;
	}
	
	// Autonomy: The quality or state of being self-governing. Self-directing freedom or self-governing state.
	private Double getAutonomyValue(MentalGraph mentalGraph, Events event) {
		
		List<Fact> verticesList = mentalGraph.getShortestPathVertices(event.getEventRelatedBelief(), event.getEventRelatedGoal());
		
		for (int i = 0; i < verticesList.size() ; i++) {
			if(verticesList.get(i).toString().contains("MENTAL-STATE::motive")) {
				try {
					if(verticesList.get(i).getSlotValue("motive-type").toString().equals("INTERNAL"))
						return 1.0;
					else if(verticesList.get(i).getSlotValue("motive-type").toString().equals("EXTERNAL"))
						return 0.0;
				} catch (JessException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	private Double checkSucceededPredecessorsRatio(Events event) {
		
		double intSucceededPredecessorCounter = 0.0;
		
		List<Fact> predecessorGoalList = collaboration.getPredecessors(event.getEventRelatedGoal());
		
		if(predecessorGoalList.size() > 0)
			for (int i = 0; i < predecessorGoalList.size() ; i++) {
				if(collaboration.isGoalAchieved(predecessorGoalList.get(i)))
					intSucceededPredecessorCounter++;
			
			return (double)intSucceededPredecessorCounter/predecessorGoalList.size();
			}
		else
			return intSucceededPredecessorCounter;
		
		return null;
	}
	
	private Double checkAvailablePredecessorsRatio(Events event) {
		
		return null;
	}
}
