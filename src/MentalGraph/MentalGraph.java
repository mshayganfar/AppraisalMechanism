package MentalGraph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.ArrayList;
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
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


public class MentalGraph {
	
	private static Graph<Fact, Edge> graph = null;
	
	public MentalGraph() {
		this.graph = new DirectedSparseGraph<Fact, Edge>();
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
	    		
	    		//graph.addVertex(factTemp);
	    		factTarget = findTargetVertex(JessEngine, factSource);

	    		if(factTarget != null) {
	    			strEdgePart1 = factSource.getSlotValue("id").toString().charAt(1) + "2" + factTarget.getSlotValue("id").toString().charAt(1);
		    		strEdgePart2 = factSource.getSlotValue("id").toString().substring(factSource.getSlotValue("id").toString().indexOf("-"), factSource.getSlotValue("id").toString().length()-1);
	    			graph.addEdge(new Edge(factSource, factTarget, strEdgePart1 + strEdgePart2), factSource, factTarget);
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
		
		Layout<Fact, Edge> layout = new CircleLayout<>(graph);
	    layout.setSize(new Dimension(300,300));
	    
	    VisualizationViewer<Fact, Edge> vv = new VisualizationViewer<Fact, Edge>(layout);
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
	    			return Color.CYAN;
	    		return Color.BLACK;
	    	}
	    };
	    
	    vv.getRenderContext().setVertexFillPaintTransformer(factPaint);
	    vv.getRenderContext().setEdgeLabelTransformer(new Transformer<Edge, String>() {
			// It was: ToStringLabeller<String>();
	    	@Override
			public String transform(Edge edge) {
				return edge.getEdgeLabel();
			} 
	    });

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
	
	public List<Edge> getShortestPath(Fact sourceVertex, Fact sinkVertex) {
		
		return new DijkstraShortestPath<Fact, Edge>(graph).getPath(sourceVertex, sinkVertex); 
	}
	
	public List<Fact> getShortestPathVertices(Fact sourceVertex, Fact sinkVertex) {
		
		int i = 0;
		List<Fact> vertices = new ArrayList<Fact>();
		
		List<Edge> edges = new DijkstraShortestPath<Fact, Edge>(graph).getPath(sourceVertex, sinkVertex);
		
		if (edges.size() > 0) {
			for (i = 0 ; i < edges.size() ; i++) {
				vertices.add(edges.get(i).getEdgeSource());
			}
			vertices.add(edges.get(i-1).getEdgeSink());
		}
		
		return vertices;
	}
	
	public class Edge {
		
		private Fact sourceFact;
		private Fact sinkFact;
		private double weight;
		private String edgeLabel;
		
		public Edge(Fact sourceFact, Fact sinkFact) {
			
			this.sourceFact = sourceFact;
			this.sinkFact   = sinkFact;
			this.weight     = 1.0;
		}

		public Edge(Fact sourceFact, Fact sinkFact, String edgeLabel) {
		
			this.sourceFact = sourceFact;
			this.sinkFact   = sinkFact;
			this.weight     = 1.0;
			this.edgeLabel  = edgeLabel;
		}
		
		public Edge(Fact sourceFact, Fact sinkFact, double dblWeight, String edgeLabel) {
			
			this.sourceFact = sourceFact;
			this.sinkFact   = sinkFact;
			this.weight     = dblWeight;
			this.edgeLabel  = edgeLabel;
		}
		
		public Fact getEdgeSource()  { return this.sourceFact; }
		public Fact getEdgeSink()    { return this.sinkFact; }
		public String getEdgeLabel() { return this.edgeLabel; }
	}
}