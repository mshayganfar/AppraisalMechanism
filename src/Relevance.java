import jess.Rete;

public class Relevance extends AppraisalProcesses {
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public double isEventRelevant(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph) {
		
		if(mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, "B1-1"), mentalStates.getFact(JessEngine, "G1-1")).size() == 0)
			return 0;
		else {
			// This utility belongs to the belief corresponded to the recent (new) event.
			double beliefUtility = getBeliefUtility(mentalStates.getFact(JessEngine, "B1-1"));
			if(beliefUtility > getHumanEmotionalThreshold())
				return beliefUtility;
			else
				return 0;
		}
	}
}
