import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import jess.Fact;
import jess.JessException;
import jess.Rete;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.algorithms.shortestpath.ShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


public class MentalGraph {
	
	private static Graph<Fact, String> graph = null;
	
	public MentalGraph() {
		this.graph = new DirectedSparseGraph<Fact, String>();
	}
	
	public void createGraph(Rete JessEngine) {
		
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
	    		factTarget = findTargetVertex(JessEngine, factSource);

	    		if(factTarget != null) {
	    			strEdgePart1 = factSource.getSlotValue("id").toString().charAt(1) + "2" + factTarget.getSlotValue("id").toString().charAt(1);
		    		strEdgePart2 = factSource.getSlotValue("id").toString().substring(factSource.getSlotValue("id").toString().indexOf("-"), factSource.getSlotValue("id").toString().length()-1);
	    			graph.addEdge(strEdgePart1 + strEdgePart2, factSource, factTarget);
	    		}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
	    
	    initializeGraph();
	}
	
	private Fact findTargetVertex(Rete JessEngine, Fact factSource) {
		
		String strFactId;
		Fact factTarget = null;
		
		Iterator<Fact> factList = JessEngine.listFacts();
		
		try {
			if (factSource.getSlotValue("id").toString().contains("B")) {
				strFactId = factSource.getSlotValue("id").toString().substring(2, factSource.getSlotValue("id").toString().indexOf("-"));
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
	
	private void initializeGraph() {
		
		Layout<Fact, String> layout = new CircleLayout<>(graph);
	    layout.setSize(new Dimension(300,300));
	    
	    VisualizationViewer<Fact, String> vv = new VisualizationViewer<Fact, String>(layout);
	    vv.setPreferredSize(new Dimension(350, 350));
	    
	    Transformer<Fact, Paint> factPaint = new Transformer<Fact, Paint>() {
	    	public Paint transform(Fact fact) {
	    		if (fact.getName().contains("belief"))
	    			return Color.RED;
	    		else if (fact.getName().contains("intention"))
	    			return Color.GREEN;
	    		else if (fact.getName().contains("motive"))
	    			return Color.YELLOW;
	    		else if (fact.getName().contains("goal"))
	    			return Color.BLUE;
	    		else if (fact.getName().contains("emotion"))
	    			return Color.MAGENTA;
	    		return Color.BLACK;
	    	}
	    };
	    
	    vv.getRenderContext().setVertexFillPaintTransformer(factPaint);
	    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
	    
	    vv.setVertexToolTipTransformer(new ToStringLabeller<Fact>());
	    
	    DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
	    gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	    vv.setGraphMouse(gm);
	    
	    JFrame frame = new JFrame("Mental States (Graph View)");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(vv);
	    frame.pack();
	    frame.setVisible(true);
	}
	
	//Example: mg.removeVertex(JessEngine, ms.getFact(JessEngine, "G1-1"));
	public void removeVertex(Rete JessEngine, Fact targetFact) {
		try {
			JessEngine.retract(targetFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
		graph.removeVertex(targetFact);
	}
	
	public List<String> getShortestPath(Fact sourceVertex, Fact sinkVertex) {
		
		return new DijkstraShortestPath<Fact, String>(graph).getPath(sourceVertex, sinkVertex); 
	}
}
