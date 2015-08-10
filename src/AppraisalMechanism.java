import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import jess.*;

import org.apache.commons.collections15.Transformer;

/*
 * Copyright (c) 2015, Mahni Shayganfar
 * 
 * This program uses the JESS (Java Expert System Shell) as a rule-based 
 * system to introduce different types of mental states (e.g., beliefs).
 * It also uses the JUNG (Java Universal Network/Graph Framework) to create
 * the corresponding graphs according to mental state.
 * 
 * This program is designed to compute values of different appraisal variables. 
 * 
 */

public class AppraisalMechanism {
	
//	private enum AGENT_TYPE {ROBOT, HUMAN};

	protected static final String strMentalStatesTemplates = "templates/mental-states/mental-states-templates.clp";
	
	public static void main(String[] args) {
		
		Rete JessEngine = null;
		Fact beliefFact1 = null, beliefFact2 = null, intentionFact = null;
		
	    try {
	    	JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
//			JessEngine.executeCommand("(load-facts facts/sensoryData.dat)");
			
		    beliefFact1 = new Fact("belief", JessEngine);
		    beliefFact1.setSlotValue("id", new Value("B1-1", RU.STRING));
		    beliefFact1.setSlotValue("task", new Value("install-panel", RU.STRING));
		    beliefFact1.setSlotValue("event", new Value("ee-au-01", RU.STRING));
		    beliefFact1.setSlotValue("agent", new Value("ROBOT", RU.SYMBOL));
		    beliefFact1.setSlotValue("belief", new Value("astronaut-frustrated", RU.STRING));
		    beliefFact1.setSlotValue("belief-type", new Value("PRIVATE", RU.SYMBOL));
		    beliefFact1.setSlotValue("belief-about", new Value("OTHER", RU.SYMBOL));
		    JessEngine.assertFact(beliefFact1);
			
		    beliefFact2 = new Fact("belief", JessEngine);
		    beliefFact2.setSlotValue("id", new Value("B1-2", RU.STRING));
		    beliefFact2.setSlotValue("task", new Value("install-panel", RU.STRING));
		    beliefFact2.setSlotValue("event", new Value("ee-au-01", RU.STRING));
		    beliefFact2.setSlotValue("agent", new Value("ROBOT", RU.SYMBOL));
		    beliefFact2.setSlotValue("belief", new Value("disfunctional-measurement-tool", RU.STRING));
		    beliefFact2.setSlotValue("belief-type", new Value("PRIVATE", RU.SYMBOL));
		    beliefFact2.setSlotValue("belief-about", new Value("ENVIRONMENT", RU.SYMBOL));
		    JessEngine.assertFact(beliefFact2);
		    
		    intentionFact = new Fact("intention", JessEngine);
		    intentionFact.setSlotValue("id", new Value("I1-1", RU.STRING));
		    intentionFact.setSlotValue("intention", new Value("acknowledge-emotion", RU.STRING));
		    JessEngine.assertFact(intentionFact);
		    
		    System.out.println(beliefFact2.getSlotValue("id"));
		    
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		} catch (JessException e) {
			e.printStackTrace();
		}

	    Graph<Fact, String> graph = new DirectedSparseGraph<Fact, String>();
//	    graph.addVertex(beliefFact);
//	    graph.addVertex(intentionFact);
//	    graph.addEdge("Bel2Int", beliefFact, intentionFact, EdgeType.DIRECTED);
	    
//	    System.out.println("The graph = " + graph.toString());
	    
	    MentalGraph mg = new MentalGraph();
	    mg.createGraph(JessEngine, graph);
	    
//	    java.util.Iterator factList = JessEngine.listFacts();
//	    
//	    while(factList.hasNext()) {
//	    	graph.addVertex((Fact)factList.next());
//	    }
	    
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
}
