package Mechanisms.Appraisal;

import edu.wpi.disco.Disco;
import MentalGraph.*;
import MentalStates.Goal;
import MentalStates.MentalStates;
import MetaInformation.Events;

public class Relevance extends AppraisalProcesses {
	
	public enum RELEVANCE {RELEVANT, IRRELEVANT};
	
	public Relevance(MentalStates mentalStates, Disco disco) {
		super(mentalStates, disco);
	}
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public RELEVANCE isEventRelevant(Events event) {
		
		MentalGraph mentalGraph = mentalStates.getMentalGraph();
		
		if (mentalGraph.getShortestPath(event.getEventRelatedBelief(), event.getEventRelatedGoalFact()).size() == 0)
			return RELEVANCE.IRRELEVANT;
		else {
			// This utility belongs to the belief and motive corresponded to the recent (new) event.
			double eventUtility = getEventUtility(event.getEventRelatedBelief(), mentalGraph.getPathMotive(mentalGraph.getShortestPathVertices(event.getEventRelatedBelief(), event.getEventRelatedGoalFact())));
			if(eventUtility > getHumanEmotionalThreshold(event.getEventRelatedEmotion()))
				return RELEVANCE.RELEVANT;
			else
				return RELEVANCE.IRRELEVANT;
		}
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
		System.out.println(collaboration.getDisco().getFocus());
		System.out.println(collaboration.getDisco().getFocus().getGoal());
		System.out.println(collaboration.getDisco().getFocus().getParent().getGoal());
		
		Goal child   = new Goal(collaboration.getDisco().getFocus(), 1, "testID", null, AGENT.BOTH);
		Goal parent  = new Goal(collaboration.getDisco().getFocus(), 1, "testID", null, AGENT.BOTH);
		
		mentalStates.assertGoal(1, "G1-1", null, AGENT.SELF, child, parent);
	}
}
