import jess.Fact;
import jess.JessException;
import jess.RU;
import jess.Rete;
import jess.Value;


public class MentalStates {

	private Fact beliefFact = null;
	private Fact MotiveFact = null;
	
	public void assertBelief(Rete JessEngine, String strBeliefId, String strTask, String strEvent, String strAgent, String strBeliefType, String strBeliefAbout, String strBelief) {
		try {
			beliefFact = new Fact("belief", JessEngine);
			beliefFact.setSlotValue("id", new Value(strBeliefId, RU.STRING));
		    beliefFact.setSlotValue("task", new Value(strTask, RU.STRING));
		    beliefFact.setSlotValue("event", new Value(strEvent, RU.STRING));
		    beliefFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
		    beliefFact.setSlotValue("belief-type", new Value(strBeliefType, RU.SYMBOL));
		    beliefFact.setSlotValue("belief-about", new Value(strBeliefAbout, RU.SYMBOL));
		    beliefFact.setSlotValue("belief", new Value(strBelief, RU.STRING));
		    JessEngine.assertFact(beliefFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertMotive(Rete JessEngine, String strMotiveId, String strTask, String strEvent, String strAgent, String strMotive) {
		try {
			MotiveFact = new Fact("motive", JessEngine);
			MotiveFact.setSlotValue("id", new Value(strMotiveId, RU.STRING));
			MotiveFact.setSlotValue("task", new Value(strTask, RU.STRING));
			MotiveFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			MotiveFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			MotiveFact.setSlotValue("motive", new Value(strMotive, RU.STRING));
		    JessEngine.assertFact(MotiveFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}
