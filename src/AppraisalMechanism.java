import edu.uci.ics.jung.graph.*;

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
	
	private static Rete JessEngine = null;
	private static Graph<Fact, String> graph = null;
	private static MentalStates ms = null;
	
	public static void main(String[] args) {
		
		ms = new MentalStates();
		graph = new DirectedSparseGraph<Fact, String>();
		
	    try {
	    	JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
//			JessEngine.executeCommand("(load-facts facts/sensoryData.dat)");
			
			ms.assertBelief(JessEngine, "B1-1", "install-panel", "ee-au-01", "ROBOT", "PRIVATE", "OTHER", "astronaut-frustrated");
			ms.assertBelief(JessEngine, "B1-2", "install-panel", "ee-au-01", "ROBOT", "PRIVATE", "ENVIRONMENT", "disfunctional-measurement-tool");
			ms.assertMotive(JessEngine, "M1-1", "install-panel", "ee-au-01", "ROBOT", "acknowledge-emotion");
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		} catch (JessException e) {
			e.printStackTrace();
		}

	    MentalGraph mg = new MentalGraph();
	    mg.createGraph(JessEngine, graph);
	}
}
