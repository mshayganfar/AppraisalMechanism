import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import jess.*;

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

	protected static final String strMentalStatesTemplates = "templates/mental-states/mental-states-templates.clp";
	
	public static void main(String[] args) {
	    try {
	    	Rete JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
		    Fact fact1 = new Fact("belief", JessEngine);
		    fact1.setSlotValue("belief", new Value("astronaut-frustrated", RU.STRING));
		    JessEngine.assertFact(fact1);
			
		    Fact fact2 = new Fact("belief", JessEngine);
		    fact2.setSlotValue("belief", new Value("astronaut-sadness", RU.STRING));
		    JessEngine.assertFact(fact2);
		    
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		    
		    Graph<Fact, String> graph = new DirectedSparseGraph<Fact, String>();
		    graph.addVertex(fact1);
		    graph.addVertex(fact2);
		    graph.addEdge("Edge-1", fact1, fact2, EdgeType.DIRECTED);
		    
		    System.out.println("The graph = " + graph.toString());
		    
//		    SimpleGraphView sgv = new SimpleGraphView();
		    Layout<Fact, String> layout = new CircleLayout<>(graph);
		    layout.setSize(new Dimension(300,300));
		    BasicVisualizationServer<Fact, String> vv = new BasicVisualizationServer<Fact, String>(layout);
		    vv.setPreferredSize(new Dimension(350, 350));
		    
		    JFrame frame = new JFrame("My Graph View");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.getContentPane().add(vv);
		    frame.pack();
		    frame.setVisible(true);
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
