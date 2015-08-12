import jess.Rete;

public class AppraisalProcesses {
	
	public int isEventRelevant(Rete JessEngine, MentalStates metalStates, MentalGraph mentalGraph) {
		
		int pathSize = mentalGraph.getShortestPath(metalStates.getFact(JessEngine, "B1-1"), metalStates.getFact(JessEngine, "G1-1")).size();
		
		if(pathSize == 0)
			return 0;
		else
			return 1;
	}
}
