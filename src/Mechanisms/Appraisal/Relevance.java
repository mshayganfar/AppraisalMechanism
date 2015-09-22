package Mechanisms.Appraisal;

import edu.wpi.disco.Disco;
import jess.Fact;
import jess.JessException;
import jess.Rete;
import Mechanisms.Mechanisms.AGENT;
import MentalGraph.*;
import MentalStates.Goal;
import MentalStates.MentalStates;
import MetaInformation.Events;
import MetaInformation.Events.EVENT_TYPE;

public class Relevance extends AppraisalProcesses {
	
	public enum RELEVANCE {RELEVANT, IRRELEVANT};
	
	public Relevance(MentalStates ms, Disco disco) {
		super(ms, disco);
	}
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public RELEVANCE isEventRelevant(MentalGraph mentalGraph, Events event) {
		
		if (mentalGraph.getShortestPath(event.getEventRelatedBelief(), event.getEventRelatedGoalFact()).size() == 0)
			return RELEVANCE.IRRELEVANT;
		else {
			// This utility belongs to the belief and motive corresponded to the recent (new) event.
			double eventUtility = getEventUtility(event.getEventRelatedBelief(), mentalGraph.getPathMotive(mentalGraph.getShortestPathVertices(event.getEventRelatedBelief(), event.getEventRelatedGoalFact())));
			if(eventUtility > getHumanEmotionalThreshold())
				return RELEVANCE.RELEVANT;
			else
				return RELEVANCE.IRRELEVANT;
		}
	}
	
	public void test(Rete JessEngine, MentalStates ms) {
		
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
		System.out.println(collaboration.getDisco().getFocus());
		System.out.println(collaboration.getDisco().getFocus().getGoal());
		System.out.println(collaboration.getDisco().getFocus().getParent().getGoal());
		
		Goal child   = new Goal(collaboration.getDisco().getFocus().getGoal(), collaboration.getDisco().getFocus().getParent().getGoal(), 1, "testID", null, AGENT.BOTH);
		Goal parent  = new Goal(collaboration.getDisco().getFocus().getGoal(), null, 1, "testID", null, AGENT.BOTH);
		
		ms.assertGoal(JessEngine, 1, "G1-1", null, AGENT.SELF, child, parent);
	}
}
