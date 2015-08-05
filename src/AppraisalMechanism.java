import jess.*;

/*
 * Copyright (c) 2015, Mahni Shayganfar
 * 
 * This program uses the JESS (Java Expert System Shell) as a rule-based 
 * system and to introduce different types of mental states (e.g., beliefs).
 * It also uses the JUNG (Java Universal Network/Graph Framework) to create
 * the corresponding graphs according to mental state.
 * 
 * This program is designed to compute values of different appraisal variables. 
 * 
 */

public class AppraisalMechanism {

	protected static final String strMentalStatesTemplates = "templates/mental-states/mental-states-templates.clp";
	
	public static void main(String[] args) {
//		try
//        {
//          Rete r = new Rete();
//          r.executeCommand("(deffunction square (?n) (return (* ?n ?n ?n)))");
//          Value v = r.executeCommand("(square 9)");
//
//          System.out.println(v.intValue(r.getGlobalContext()));
//        }
//      catch (JessException e)
//        {
//          System.err.println(e);
//        }
		
	    try {
	    	Rete JessEngine = new Rete();
	    	JessEngine.batch("modules/module-definitions.clp");
			JessEngine.batch(strMentalStatesTemplates);
			
//			JessEngine.eval("(deftemplate MENTAL-STATE::point \"A 2D point\" (slot x) (slot y))");
//			Fact f = new Fact("point", JessEngine);
//		    f.setSlotValue("x", new Value(37, RU.INTEGER));
//		    f.setSlotValue("y", new Value(49, RU.INTEGER));
//		    JessEngine.assertFact(f);

		    Fact f = new Fact("belief", JessEngine);
		    f.setSlotValue("belief", new Value("astronaut-frustrated", RU.STRING));
		    JessEngine.assertFact(f);
		    
		    JessEngine.eval("(facts)");
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
