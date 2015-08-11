import java.util.Iterator;
import java.util.Map;

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
	
	public void assertBelief(Rete JessEngine, String strBeliefID, String strTask, String strEvent, String strAgent, String strBeliefType, String strBeliefAbout, String strBelief) {
		try {
			beliefFact = new Fact("belief", JessEngine);
			beliefFact.setSlotValue("id", new Value(strBeliefID, RU.STRING));
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
	
	public void assertMotive(Rete JessEngine, String strMotiveID, String strTask, String strEvent, String strAgent, String strMotive) {
		try {
			MotiveFact = new Fact("motive", JessEngine);
			MotiveFact.setSlotValue("id", new Value(strMotiveID, RU.STRING));
			MotiveFact.setSlotValue("task", new Value(strTask, RU.STRING));
			MotiveFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			MotiveFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			MotiveFact.setSlotValue("motive", new Value(strMotive, RU.STRING));
		    JessEngine.assertFact(MotiveFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertIntention(Rete JessEngine, String strIntentionID, String strTask, String strEvent, String strAgent, String strIntention) {
		try {
			IntentionFact = new Fact("intention", JessEngine);
			IntentionFact.setSlotValue("id", new Value(strIntentionID, RU.STRING));
			IntentionFact.setSlotValue("task", new Value(strTask, RU.STRING));
			IntentionFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			IntentionFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			IntentionFact.setSlotValue("intention", new Value(strIntention, RU.STRING));
		    JessEngine.assertFact(IntentionFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertGoal(Rete JessEngine, String strGoalID, String strTask, String strEvent, String strAgent, String strGoal) {
		try {
			GoalFact = new Fact("goal", JessEngine);
			GoalFact.setSlotValue("id", new Value(strGoalID, RU.STRING));
			GoalFact.setSlotValue("task", new Value(strTask, RU.STRING));
			GoalFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			GoalFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			GoalFact.setSlotValue("goal", new Value(strGoal, RU.STRING));
		    JessEngine.assertFact(GoalFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertEmotionInstance(Rete JessEngine, String strEmotionInstanceID, String strTask, String strEvent, String strAgent, String strEmotionInstance) {
		try {
			EmotionInstanceFact = new Fact("emotion-instance", JessEngine);
			EmotionInstanceFact.setSlotValue("id", new Value(strEmotionInstanceID, RU.STRING));
			EmotionInstanceFact.setSlotValue("task", new Value(strTask, RU.STRING));
			EmotionInstanceFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			EmotionInstanceFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			EmotionInstanceFact.setSlotValue("emotion-instance", new Value(strEmotionInstance, RU.SYMBOL));
		    JessEngine.assertFact(EmotionInstanceFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public Fact getFact(Rete JessEngine, String strFactID) {
		
		Fact factTarget = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				factTarget = (Fact)factList.next();
				
				if (factTarget.getSlotValue("id").toString().substring(1, factTarget.getSlotValue("id").toString().length()-1).equals(strFactID)) {
					return factTarget;
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	public void modifyBelief(Fact beliefFact, Map<String, String> beliefParameters) {
		try {
			if(beliefParameters.containsKey("id")) beliefFact.setSlotValue("id", new Value(beliefParameters.get("id"), RU.STRING));
			if(beliefParameters.containsKey("task")) beliefFact.setSlotValue("task", new Value(beliefParameters.get("task"), RU.STRING));
			if(beliefParameters.containsKey("event")) beliefFact.setSlotValue("event", new Value(beliefParameters.get("event"), RU.STRING));
			if(beliefParameters.containsKey("agent")) beliefFact.setSlotValue("agent", new Value(beliefParameters.get("agent"), RU.STRING));
			if(beliefParameters.containsKey("belief-type")) beliefFact.setSlotValue("belief-type", new Value(beliefParameters.get("belief-type"), RU.STRING));
			if(beliefParameters.containsKey("belief-about")) beliefFact.setSlotValue("belief-about", new Value(beliefParameters.get("belief-about"), RU.STRING));
			if(beliefParameters.containsKey("belief")) beliefFact.setSlotValue("belief", new Value(beliefParameters.get("belief"), RU.STRING));
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void modifyIntention(Fact intentionFact, Map<String, String> intentionParameters) {
		try {
			if(intentionParameters.containsKey("id")) intentionFact.setSlotValue("id", new Value(intentionParameters.get("id"), RU.STRING));
			if(intentionParameters.containsKey("task")) intentionFact.setSlotValue("task", new Value(intentionParameters.get("task"), RU.STRING));
			if(intentionParameters.containsKey("event")) intentionFact.setSlotValue("event", new Value(intentionParameters.get("event"), RU.STRING));
			if(intentionParameters.containsKey("agent")) intentionFact.setSlotValue("agent", new Value(intentionParameters.get("agent"), RU.STRING));
			if(intentionParameters.containsKey("intention")) intentionFact.setSlotValue("intention", new Value(intentionParameters.get("intention"), RU.STRING));
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}
