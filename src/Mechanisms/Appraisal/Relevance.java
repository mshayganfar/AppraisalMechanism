package Mechanisms.Appraisal;

import MentalGraph.*;
import MetaInformation.Events;

public class Relevance extends AppraisalProcesses {
	
	public enum RELEVANCE {RELEVANT, IRRELEVANT};
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public RELEVANCE isEventRelevant(MentalGraph mentalGraph, Events event) {
		
		if (mentalGraph.getShortestPath(event.getEventRelatedBelief(), event.getEventRelatedGoal()).size() == 0)
			return RELEVANCE.IRRELEVANT;
		else {
			// This utility belongs to the belief and motive corresponded to the recent (new) event.
			double eventUtility = getEventUtility(event.getEventRelatedBelief(), mentalGraph.getPathMotive(mentalGraph.getShortestPathVertices(event.getEventRelatedBelief(), event.getEventRelatedGoal())));
			if(eventUtility > getHumanEmotionalThreshold())
				return RELEVANCE.RELEVANT;
			else
				return RELEVANCE.IRRELEVANT;
		}
	}
}
