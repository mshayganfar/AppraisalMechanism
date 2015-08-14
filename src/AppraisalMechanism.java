import java.util.HashMap;
import java.util.Map;

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
	
	public static void main(String[] args) {
		
		Relevance rap = new Relevance();
		MentalStates ms = new MentalStates();
		MentalGraph mg = new MentalGraph();
		
	    try {
	    	JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
//			JessEngine.executeCommand("(load-facts facts/sensoryData.dat)");
			
			ms.assertBelief(JessEngine, "B1-1", "install-panel", "ee-au-01", "UTTERANCE", "EXTERNAL_EVENT", "ROBOT", "PRIVATE", "UTTERANCE", "astronaut-frustrated");
			ms.assertBelief(JessEngine, "B1-2", "install-panel", "ee-au-01", "UTTERANCE", "INTERNAL_EVENT", "ROBOT", "PRIVATE", "ENVIRONMENT", "disfunctional-measurement-tool");
			ms.assertMotive(JessEngine, "M1-1", "install-panel", "ee-au-01", "ROBOT", "acknowledge-emotion", "ACTIVE");
			ms.assertIntention(JessEngine, "I1-1", "install-panel", "ee-au-01", "ROBOT", "acknowledge-emotion");
			ms.assertGoal(JessEngine, "G1-1", "install-panel", "ee-au-01", "ROBOT", "fix-problem");
			ms.assertEmotionInstance(JessEngine, "E1-1", "install-panel", "ee-au-01", "HUMAN", "FRUSTRATION");
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		} catch (JessException e) {
			e.printStackTrace();
		}

	    mg.createGraph(JessEngine);
	    
	    System.out.println(ms.getBeliefType(JessEngine, "B1-1"));
	    
	    System.out.println(rap.isEventRelevant(JessEngine, ms, mg));
	    
	    Desirability d = new Desirability();
	    System.out.println(d.isEventDesirable(JessEngine, ms, mg, 1));
	}
}
