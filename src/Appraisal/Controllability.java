package Appraisal;

import java.util.ArrayList;

import jess.Fact;

import Collaboration.Collaboration.AGENT_TYPE;
import Collaboration.Collaboration.FOCUS_TYPE;
import MetaInformation.Events;

public class Controllability extends AppraisalProcesses{

	public boolean isEventControllable(Events event) {
		
		double dblAgency = getAgencyValue(event);
		
		return true;
	}
	
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
			else if ((responsibleAgent.equals(AGENT_TYPE.OTHER)) || (responsibleAgent.equals(AGENT_TYPE.NONE)))
				return 0.0;
			else if (responsibleAgent.equals(AGENT_TYPE.BOTH))
				return 0.5;
		}
		else if (collaboration.getFocusType().equals(FOCUS_TYPE.NONPRIMITIVE)){
			ArrayList<Fact> taskContributersList = collaboration.getTaskContributers(event.getEventRelatedGoal());
			
			for (int i = 0 ; i < taskContributersList.size() ; i++) {
				responsibleAgent = collaboration.getTaskResponsibleAgent(taskContributersList.get(i));
				if (responsibleAgent.equals(AGENT_TYPE.SELF))
					intSelfResponsibilityCounter++;
				else if (responsibleAgent.equals(AGENT_TYPE.OTHER))
					intOtherResponsibilityCounter++;
				else if (responsibleAgent.equals(AGENT_TYPE.NONE))
					intNoneResponsibilityCounter++;
				else if (responsibleAgent.equals(AGENT_TYPE.BOTH))
					intBothResponsibilityCounter++;
			}
			return (double)(intSelfResponsibilityCounter + (double)(intBothResponsibilityCounter/2))/
						   (intOtherResponsibilityCounter + (double)(intBothResponsibilityCounter/2) + intNoneResponsibilityCounter);
		}
		
		return null;
	}
}
