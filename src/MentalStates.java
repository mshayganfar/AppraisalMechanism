import jess.Fact;
import jess.JessException;
import jess.RU;
import jess.Rete;
import jess.Value;


public class MentalStates {

	private Fact beliefFact          = null;
	private Fact MotiveFact          = null;
	private Fact IntentionFact       = null;
	private Fact GoalFact            = null;
	private Fact EmotionInstanceFact = null;
	
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
	
	public void assertIntention(Rete JessEngine, String strIntentionId, String strTask, String strEvent, String strAgent, String strIntention) {
		try {
			IntentionFact = new Fact("motive", JessEngine);
			IntentionFact.setSlotValue("id", new Value(strIntentionId, RU.STRING));
			IntentionFact.setSlotValue("task", new Value(strTask, RU.STRING));
			IntentionFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			IntentionFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			IntentionFact.setSlotValue("intention", new Value(strIntention, RU.STRING));
		    JessEngine.assertFact(IntentionFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertGoal(Rete JessEngine, String strGoalId, String strTask, String strEvent, String strAgent, String strGoal) {
		try {
			GoalFact = new Fact("motive", JessEngine);
			GoalFact.setSlotValue("id", new Value(strGoalId, RU.STRING));
			GoalFact.setSlotValue("task", new Value(strTask, RU.STRING));
			GoalFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			GoalFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			GoalFact.setSlotValue("goal", new Value(strGoal, RU.STRING));
		    JessEngine.assertFact(GoalFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertEmotionInstance(Rete JessEngine, String strEmotionInstanceId, String strTask, String strEvent, String strAgent, String strEmotionInstance) {
		try {
			EmotionInstanceFact = new Fact("emotion-instance", JessEngine);
			EmotionInstanceFact.setSlotValue("id", new Value(strEmotionInstanceId, RU.STRING));
			EmotionInstanceFact.setSlotValue("task", new Value(strTask, RU.STRING));
			EmotionInstanceFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			EmotionInstanceFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			EmotionInstanceFact.setSlotValue("emotion-instance", new Value(strEmotionInstance, RU.SYMBOL));
		    JessEngine.assertFact(EmotionInstanceFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}
