import java.util.Iterator;

import edu.uci.ics.jung.graph.Graph;

import jess.Fact;
import jess.JessException;
import jess.Rete;


public class MentalGraph {
	
	public void addEdges(Rete JessEngine, Graph<Fact, String> graph) {
		
		Fact factTemp = null;
		
		Iterator<Fact> factList = JessEngine.listFacts();
		
	    while(factList.hasNext()) {
	    	factTemp = (Fact)factList.next();
	    	
	    	graph.addVertex(factTemp);
	    	
	    	try {
				System.out.println(factTemp.getSlotValue("id").toString().contains("B"));
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private Fact findTargetNode(Iterator<Fact> factList, Fact fact) {
		
		String strId;
		Fact factTemp = null;
		
		try {
			if (fact.getSlotValue("id").toString().contains("B")) {
				strId = fact.getSlotValue("id").toString().substring(1, fact.getSlotValue("id").toString().indexOf("-"));
				while(factList.hasNext()) {
		    		factTemp = (Fact)factList.next();
			    		
					if (factTemp.getSlotValue("id").toString().contains("M")) {
						if(factTemp.getSlotValue("id").toString().regionMatches(1, strId, 0, strId.length()))
							return factTemp;
					}
			    }
				return null;
			}
			else if (fact.getSlotValue("id").toString().contains("M")) {
				strId = fact.getSlotValue("id").toString().substring(1, fact.getSlotValue("id").toString().indexOf("-"));
				while(factList.hasNext()) {
		    		factTemp = (Fact)factList.next();
		    		
					if (factTemp.getSlotValue("id").toString().contains("I")) {
						if(factTemp.getSlotValue("id").toString().regionMatches(1, strId, 0, strId.length()))
							return factTemp;
					}
			    }
				return null;
			}
			else if (fact.getSlotValue("id").toString().contains("I")) {
				strId = fact.getSlotValue("id").toString().substring(1, fact.getSlotValue("id").toString().indexOf("-"));
				while(factList.hasNext()) {
		    		factTemp = (Fact)factList.next();
			    		
					if (factTemp.getSlotValue("id").toString().contains("G")) {
						if(factTemp.getSlotValue("id").toString().regionMatches(1, strId, 0, strId.length()))
							return factTemp;
					}
			    }
				return null;
			}
			else if (fact.getSlotValue("id").toString().contains("E")) {
				strId = fact.getSlotValue("id").toString().substring(1, fact.getSlotValue("id").toString().indexOf("-"));
				while(factList.hasNext()) {
		    		factTemp = (Fact)factList.next();
			    		
					if (factTemp.getSlotValue("id").toString().contains("G")) {
						if(factTemp.getSlotValue("id").toString().regionMatches(1, strId, 0, strId.length()))
							return factTemp;
					}
			    }
				return null;
			}
			else {
				return null;
			}
		} catch (JessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
