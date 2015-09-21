package Mechanisms;

import MetaInformation.Turns;

import jess.JessException;
import jess.Rete;

public class Mechanisms {

	public enum AGENT{SELF, OTHER, BOTH, NONE};
	
	protected static Rete JessEngine = new Rete();
	
	protected static final String strAppraisalModuleTemplates = "templates/mental-states/mental-states-templates.clp";
	
	protected Turns turn;
	
	public Mechanisms() {
		turn = new Turns();
	}
	
	public static void initializeMentalStates() {
		try {
			JessEngine.batch("modules/module-definitions.clp");
			JessEngine.reset();
			JessEngine.batch(strAppraisalModuleTemplates);
			
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}