import edu.wpi.cetask.Task;
import edu.wpi.cetask.TaskModel;
import edu.wpi.disco.Agent;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import jess.*;

import Mechanisms.Appraisal.*;
import MentalStates.*;
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
	
	protected static Rete JessEngine = null;
	
	public static void main(String[] args) {
		
		JessEngine = new Rete();
		
		MentalStates ms = new MentalStates(JessEngine);
		//ms.initializeMentalStates(); //Test this!
		
		//MentalGraph mg = new MentalGraph(ms);
		
		Turns turn = new Turns();
		
		Interaction interaciton = new Interaction(new Agent("agent"), new User("user"),
												  args.length > 0 && args[0].length() > 0 ? args[0] : null);
		interaciton.start(true);
		
		Disco disco = interaciton.getDisco();
		
		System.out.println(disco);
		
		TaskModel taskModel = disco.load("/TaskModel/ABC.xml");
		Task task = taskModel.getTaskClass("d").newInstance();
		
		System.out.println(task.toString());
		System.out.println(taskModel.toString());

//		interaciton.done(false, Propose.Should.newInstance(disco, false, task), null);
		interaciton.occurred(false, task, null);
		
		//interaciton.getSystem().respond(interaciton, true, false, false);
		
		Relevance rap = new Relevance(ms, interaciton.getDisco());
		
	    try {
	    	//JessEngine = new Rete();
	    	
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			JessEngine.batch("rules/rules.clp");
			
//			JessEngine.executeCommand("(load-facts facts/sensoryData.dat)");
			
			ms.assertBelief("turn:1", "B1-1", "install-panel", "ee-au-01", "UTTERANCE", "EXTERNAL_EVENT", "ROBOT", "PRIVATE", "UTTERANCE", "astronaut-frustrated", "HIGH", "LOW", "HIGH", "MEDIUM", "HIGH", "LOW");
			ms.assertBelief("turn:1", "B1-2", "install-panel", "ee-au-01", "UTTERANCE", "INTERNAL_EVENT", "ROBOT", "PRIVATE", "ENVIRONMENT", "disfunctional-measurement-tool", "LOW", "LOW", "MEDIUM", "HIGH", "MEDIUM", "HIGH");
			ms.assertMotive("turn:1", "M1-1", "install-panel", "ee-au-01", "ROBOT", "acknowledge-emotion", "ACTIVE", "INTERNAL");
			ms.assertIntention("turn:1", "I1-1", "install-panel", "ee-au-01", "ROBOT", "acknowledge-emotion");

			rap.test();
			
			ms.assertEmotionInstance("turn:1", "E1-1", "install-panel", "ee-au-01", "HUMAN", "FRUSTRATION");
		    JessEngine.run();
			
		    JessEngine.eval("(facts)");
		} catch (JessException e) {
			e.printStackTrace();
		}

	    ms.getMentalGraph().createGraph();

	    Events event = new Events(ms.getFact("\"B1-1\""), ms.getFact("\"G1-1\""), ms.getFact("\"E1-1\""), EVENT_TYPE.ACTION);
	    
	    System.out.println(event.getEventRelatedBelief());
	    
	    System.out.println(rap.isEventRelevant(event));
	    
	    Desirability desirability = new Desirability();
	    //System.out.println(desirability.isEventDesirable(mg, event));
	    
	    Expectedness expectedness = new Expectedness();
	    System.out.println(expectedness.isEventExpected(event));
	    
	    Controllability controllability = new Controllability();
	    //System.out.println(controllability.isEventControllable(mg, event));
	}
}
