import java.util.Iterator;

import edu.uci.ics.jung.graph.Graph;

import jess.Fact;
import jess.JessException;
import jess.Rete;


public class MentalGraph {
	
	public void createGraph(Rete JessEngine, Graph<Fact, String> graph) {
		
		Fact factTemp = null, factSource = null, factTarget = null;
		
		Iterator<Fact> factList = JessEngine.listFacts();
		
	    while(factList.hasNext()) {
	    	factTemp = (Fact)factList.next();
	    	graph.addVertex(factTemp);
	    }
	    
	    factList = JessEngine.listFacts();
	    String strEdgePart1, strEdgePart2;
	    
	    while(factList.hasNext()) {
	    	try {
	    		factSource = (Fact)factList.next();
	    		
	    		graph.addVertex(factTemp);
	    		factTarget = findTargetNode(JessEngine, factSource);

	    		if(factTarget != null) {
	    			strEdgePart1 = factSource.getSlotValue("id").toString().charAt(1) + "2" + factTarget.getSlotValue("id").toString().charAt(1);
		    		strEdgePart2 = factSource.getSlotValue("id").toString().substring(factSource.getSlotValue("id").toString().indexOf("-"), factSource.getSlotValue("id").toString().length()-1);
	    			graph.addEdge(strEdgePart1 + strEdgePart2, factSource, factTarget);
	    		}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private Fact findTargetNode(Rete JessEngine, Fact factSource) {
		
		String strFactId;
		Fact factTarget = null;
		
		Iterator<Fact> factList = JessEngine.listFacts();
		
		try {
			if (factSource.getSlotValue("id").toString().contains("B")) {
				strFactId = factSource.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-"));
				System.out.println(strFactId);
				while(factList.hasNext()) {
		    		factTarget = (Fact)factList.next();
			    		
					if (factTarget.getSlotValue("id").toString().contains("M")) {
						if(factTarget.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-")).equals(strFactId))
							return factTarget;
					}
			    }
			}
			else if (factSource.getSlotValue("id").toString().contains("M")) {
				strFactId = factSource.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-"));
				while(factList.hasNext()) {
		    		factTarget = (Fact)factList.next();
		    		
					if (factTarget.getSlotValue("id").toString().contains("I")) {
						if(factTarget.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-")).equals(strFactId))
							return factTarget;
					}
			    }
			}
			else if (factSource.getSlotValue("id").toString().contains("I")) {
				strFactId = factSource.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-"));
				while(factList.hasNext()) {
		    		factTarget = (Fact)factList.next();
			    		
					if (factTarget.getSlotValue("id").toString().contains("G")) {
						if(factTarget.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-")).equals(strFactId))
							return factTarget;
					}
			    }
			}
			else if (factSource.getSlotValue("id").toString().contains("E")) {
				strFactId = factSource.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-"));
				while(factList.hasNext()) {
		    		factTarget = (Fact)factList.next();
			    		
					if (factTarget.getSlotValue("id").toString().contains("G")) {
						if(factTarget.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-")).equals(strFactId))
							return factTarget;
					}
			    }
			}

			return null;
		} catch (JessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
