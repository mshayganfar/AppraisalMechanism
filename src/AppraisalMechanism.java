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
	
	protected static final String strMentalStatesTemplates = "templates/mental-states/mental-states-templates.clp";
	
	private static Rete JessEngine = null;
	
	public static void main(String[] args) {
		
		MentalStates ms = new MentalStates();
		
	    try {
	    	JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
//			JessEngine.executeCommand("(load-facts facts/sensoryData.dat)");
			
			ms.assertBelief(JessEngine, "B1-1", "install-panel", "ee-au-01", "ROBOT", "astronaut-frustrated", "PRIVATE", "OTHER");
			ms.assertBelief(JessEngine, "B1-2", "install-panel", "ee-au-01", "ROBOT", "disfunctional-measurement-tool", "PRIVATE", "ENVIRONMENT");
			
			ms.assertMotive(JessEngine, "M1-1", "acknowledge-emotion");
			
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		} catch (JessException e) {
			e.printStackTrace();
		}

	    Graph<Fact, String> graph = new DirectedSparseGraph<Fact, String>();
	    
	    MentalGraph mg = new MentalGraph();
	    mg.createGraph(JessEngine, graph);
	    
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
