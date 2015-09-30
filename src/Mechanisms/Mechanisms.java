package Mechanisms;

import edu.wpi.disco.Disco;
import MentalStates.MentalStates;
import MetaInformation.Turns;

import jess.JessException;
import jess.Rete;

public class Mechanisms {

	public enum AGENT{SELF, OTHER, BOTH, NONE};
	
	protected static Rete JessEngine = new Rete(); // Should be changed/removed!
	
	public MentalStates mentalStates = null;
	
	protected static final String strAppraisalModuleTemplates = "templates/mental-states/mental-states-templates.clp";
	
	protected Turns turn;
	
	protected Disco disco = null;
	
	public Mechanisms() {}
	
	public Mechanisms(MentalStates ms, Disco disco) {
		turn = new Turns();
		this.mentalStates = ms;
		this.disco = disco;
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