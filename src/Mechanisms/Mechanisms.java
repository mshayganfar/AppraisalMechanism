package Mechanisms;

import edu.wpi.disco.Disco;
import MentalStates.MentalStates;
import MetaInformation.Turns;

import jess.JessException;
import jess.Rete;

public class Mechanisms {

	public enum AGENT{SELF, OTHER, BOTH, NONE};
	
	//protected static Rete JessEngine = new Rete(); // Should be changed/removed!
	
	public static MentalStates mentalStates = null;
	
	protected static final String strAppraisalModuleTemplates = "templates/mental-states/mental-states-templates.clp";
	
	protected Turns turn;
	
	protected Disco disco = null;
	
	public Mechanisms() {}
	
	public Mechanisms(MentalStates mentalStates, Disco disco) {
		turn = new Turns();
		this.mentalStates = mentalStates;
		this.disco = disco;
	}
	
	public static void initializeMentalStates() {
		try {
			mentalStates.getJessEngine().batch("modules/module-definitions.clp");
			mentalStates.getJessEngine().reset();
			mentalStates.getJessEngine().batch(strAppraisalModuleTemplates);
			
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}