package Mechanisms;

import edu.wpi.disco.Disco;
import MentalStates.MentalStates;
import MetaInformation.Turns;

public class Mechanisms {

	public enum AGENT{SELF, OTHER, BOTH, NONE};
	
	protected MentalStates mentalStates;
	
	protected Turns turn;
	
	protected Disco disco = null;
	
	public Mechanisms() {}
	
	public Mechanisms(MentalStates mentalStates, Disco disco) {
		turn = new Turns();
		this.mentalStates = mentalStates;
		this.disco = disco;
	}
}