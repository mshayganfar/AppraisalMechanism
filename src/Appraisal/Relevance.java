package Appraisal;

import jess.Rete;

import MentalStates.*;
import MentalGraph.*;
import MetaInformation.Events;

public class Relevance extends AppraisalProcesses {
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public double isEventRelevant(MentalGraph mentalGraph, Events event) {
		
		if(mentalGraph.getShortestPath(event.getEventRelatedBelief(), event.getEventRelatedGoal()).size() == 0)
			return 0;
		else {
			// This utility belongs to the belief corresponded to the recent (new) event.
			double beliefUtility = getBeliefUtility(event.getEventRelatedBelief());
			if(beliefUtility > getHumanEmotionalThreshold())
				return beliefUtility;
			else
				return 0;
		}
	}
}
