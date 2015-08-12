import java.util.Iterator;
import java.util.List;

import jess.Fact;
import jess.JessException;
import jess.Rete;


public class Desirability extends AppraisalProcesses{
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public boolean isEventDesirable(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph) {
		
		double deltaUtility = 0.0;
		
		List<String> shortestPathList = mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, "B1-1"), mentalStates.getFact(JessEngine, "G1-1"));
		
		if (mentalStates.getFactEventType(JessEngine, "B1-1").equals(BELIEF_TYPE.EXTERNAL_EVENT))
		{
			double utteranceUtility = getUtteranceUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE);
//			double actionUtility    = getActionUtility();
//			double emotionUtility   = getEmotionUtility();
		}
		else if (mentalStates.getFactEventType(JessEngine, "B1-1").equals(BELIEF_TYPE.INTERNAL_EVENT)){
			
		}
		
		if (deltaUtility > getHumanEmotionalThreshold())
			return true;
		else
			return false;
	}
	
	private double getUtteranceUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType) {
		
		return getMentalStateUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE);
	}
	
//	private double getActionUtility() {
//		
//	}
//	
//	private double getEmotionUtility() {
//		
//	}
	
	private double getMentalStateUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType) {
		
		switch (eventType) {
			case UTTERANCE:
				Fact targetFact = null;
				Iterator<Fact> factList = JessEngine.listFacts();
				
				while(factList.hasNext()) {
					try {
						targetFact = (Fact)factList.next();
						
						if ((targetFact.getName().contains("belief")) /*&& (targetFact.getFActEventType, e.g., utterance)*/) {
							mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, targetFact.getSlotValue("id").toString()), mentalStates.getFact(JessEngine, "G1-1"));
						}
					} catch (JessException e) {
						e.printStackTrace();
					}
			    }
				return 0;
			case ACTION:
//				return ;
			case EMOTION:
//				return ;
		}
		
		return 0.0;
	}
}
