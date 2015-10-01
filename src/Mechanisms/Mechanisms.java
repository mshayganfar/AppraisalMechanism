package Mechanisms;

import MentalStates.MentalStates;
import MetaInformation.Turns;

public class Mechanisms {

	public enum AGENT{SELF, OTHER, BOTH, NONE};
	
	public MentalStates mentalState;
	
	protected Turns turn;
	
	public Mechanisms() {}
	
	public Mechanisms(MentalStates mentalState) {
		turn = new Turns();
		this.mentalState = mentalState;
	}
}