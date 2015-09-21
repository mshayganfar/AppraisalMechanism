import edu.wpi.disco.Agent;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import jess.*;

import Mechanisms.Appraisal.*;
import MentalStates.*;
import MentalGraph.*;
import MetaInformation.*;
import MetaInformation.Events.EVENT_TYPE;

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
		Turns turn = new Turns();
		
	    try {
	    	JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
//			JessEngine.executeCommand("(load-facts facts/sensoryData.dat)");
			
			ms.assertBelief(JessEngine, "turn:1", "B1-1", "install-panel", "ee-au-01", "UTTERANCE", "EXTERNAL_EVENT", "ROBOT", "PRIVATE", "UTTERANCE", "astronaut-frustrated", "HIGH", "LOW", "HIGH", "MEDIUM", "HIGH", "LOW");
			ms.assertBelief(JessEngine, "turn:1", "B1-2", "install-panel", "ee-au-01", "UTTERANCE", "INTERNAL_EVENT", "ROBOT", "PRIVATE", "ENVIRONMENT", "disfunctional-measurement-tool", "LOW", "LOW", "MEDIUM", "HIGH", "MEDIUM", "HIGH");
			ms.assertMotive(JessEngine, "turn:1", "M1-1", "install-panel", "ee-au-01", "ROBOT", "acknowledge-emotion", "ACTIVE", "INTERNAL");
			ms.assertIntention(JessEngine, "turn:1", "I1-1", "install-panel", "ee-au-01", "ROBOT", "acknowledge-emotion");
			ms.assertGoal(JessEngine, "turn:1", "G1-1", "install-panel", "ee-au-01", "ROBOT", "fix-problem");
			ms.assertEmotionInstance(JessEngine, "turn:1", "E1-1", "install-panel", "ee-au-01", "HUMAN", "FRUSTRATION");
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		} catch (JessException e) {
			e.printStackTrace();
		}

	    mg.createGraph(JessEngine);

	    Events event = new Events(ms.getFact(JessEngine, "\"B1-1\""), ms.getFact(JessEngine, "\"G1-1\""), EVENT_TYPE.ACTION);
	    
	    System.out.println(event.getEventRelatedBelief());
	    
	    System.out.println(rap.isEventRelevant(mg, event));
	    
	    Desirability desirability = new Desirability();
	    //System.out.println(desirability.isEventDesirable(mg, event));
	    
	    Expectedness expectedness = new Expectedness();
	    System.out.println(expectedness.isEventExpected(mg, event));
	    
	    Controllability controllability = new Controllability();
	    //System.out.println(controllability.isEventControllable(mg, event));
	    
	    new Interaction(
	            new Agent("agent"), 
	            new User("user"),
	            args.length > 0 && args[0].length() > 0 ? args[0] : null)
	         .start(true);
	}
}
