package Turns;

public class Turns {
	
	private int count;
	
	public Turns() {
		count = 1;
	}
	
	public void increment() {
		count++;
	}
	
	public String toString() {
		return ("turn:" + count);
	}
	
	public int value() {
		return count;
	}
	
	public int value(String strTurn) {
		return Integer.valueOf(strTurn.substring(5));
	}
	
	public String getLastTurn() {
		return ("\"turn:" + count + "\"");
	}
	
	public String getPreviousTurn() {
		return (count == 1) ? ("\"turn:" + count + "\"") : ("\"turn:" + (count-1) + "\"");
	}
}
