import java.util.Iterator;
import java.util.List;

import jess.Fact;
import jess.JessException;
import jess.Rete;


public class Desirability extends AppraisalProcesses{
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public boolean isEventDesirable(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, int intEventTurn) {
		
		double deltaUtility = 0.0;
		
		List<String> shortestPathList = mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, "B1-1"), mentalStates.getFact(JessEngine, "G1-1"));
		
		if (mentalStates.getBeliefEventType(JessEngine, "B1-1").equals(BELIEF_TYPE.EXTERNAL_EVENT))
		{
			double utteranceUtility = getUtteranceUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE, intEventTurn);
//			double actionUtility    = getActionUtility();
//			double emotionUtility   = getEmotionUtility();
		}
		else if (mentalStates.getBeliefEventType(JessEngine, "B1-1").equals(BELIEF_TYPE.INTERNAL_EVENT)){
			
		}
		
		if (deltaUtility > getHumanEmotionalThreshold())
			return true;
		else
			return false;
	}
	
	private double getUtteranceUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, int intEventTurn) {
		
		return getMentalStateUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE, intEventTurn);
	}
	
//	private double getActionUtility() {
//		
//	}
//	
//	private double getEmotionUtility() {
//		
//	}
	
	private double getMentalStateUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, int intEventTurn) {
		
		String strBeliefID = null;
		double mentalStateUtilityValue = 0.0;
		
		switch (eventType) {
			case UTTERANCE:
				Fact targetFact = null;
				Iterator<Fact> factList = JessEngine.listFacts();
				
				while(factList.hasNext()) {
					try {
						targetFact = (Fact)factList.next();
						
						if ((targetFact.getName().contains("belief"))) {
							strBeliefID = targetFact.getSlotValue("id").toString();
							if (strBeliefID.contains("B" + intEventTurn + "-"))
								if (mentalStates.getBeliefEventType(JessEngine, strBeliefID).equals("UTTERANCE")) {
									List<String> pathList = mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, strBeliefID), mentalStates.getFact(JessEngine, "G1-1"));
									if (pathList.size() > 0) mentalStateUtilityValue += getPathUtility(pathList);
								}
						}
					} catch (JessException e) {
						e.printStackTrace();
					}
			    }
				return mentalStateUtilityValue;
			case ACTION:
//				return ;
			case EMOTION:
//				return ;
		}
		
		return 0.0;
	}
	
	private double getPathUtility(List<String> pathList) {
		
		return 0.0;
	}
}
