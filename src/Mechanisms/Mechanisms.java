package Mechanisms;

import MentalState.MentalState;
import MetaInformation.Turns;

public class Mechanisms {

	public enum AGENT{SELF, OTHER, BOTH, NONE};
	
	public MentalState mentalState;
	
	protected Turns turn;
	
	public Mechanisms() {}
	
	public Mechanisms(MentalState mentalState) {
		turn = new Turns();
		this.mentalState = mentalState;
	}
}