import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

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

	protected static final String strMentalStatesTemplates = "templates/mental-states/mental-states-templates.clp";
	
	public static void main(String[] args) {
	    try {
	    	Rete JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
		    Fact beliefFact = new Fact("belief", JessEngine);
		    beliefFact.setSlotValue("belief", new Value("astronaut-frustrated", RU.STRING));
		    JessEngine.assertFact(beliefFact);
			
		    Fact intentionFact = new Fact("intention", JessEngine);
		    intentionFact.setSlotValue("intention", new Value("acknowledge-emotion", RU.STRING));
		    JessEngine.assertFact(intentionFact);
		    
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		    
		    Graph<Fact, String> graph = new DirectedSparseGraph<Fact, String>();
		    graph.addVertex(beliefFact);
		    graph.addVertex(intentionFact);
		    graph.addEdge("Bel2Int", beliefFact, intentionFact, EdgeType.DIRECTED);
		    
		    System.out.println("The graph = " + graph.toString());
		    
		    Layout<Fact, String> layout = new CircleLayout<>(graph);
		    layout.setSize(new Dimension(300,300));
		    BasicVisualizationServer<Fact, String> vv = new BasicVisualizationServer<Fact, String>(layout);
		    vv.setPreferredSize(new Dimension(350, 350));
		    
		    Transformer<Fact, Paint> beliefPaint = new Transformer<Fact, Paint>() {
		    	public Paint transform(Fact fact) {
		    		if (fact.getName().contains("belief"))
		    			return Color.RED;
		    		else if (fact.getName().contains("intention"))
		    			return Color.GREEN;
		    		return Color.YELLOW;
		    	}
		    };
		    
		    vv.getRenderContext().setVertexFillPaintTransformer(beliefPaint);
		    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		    
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
