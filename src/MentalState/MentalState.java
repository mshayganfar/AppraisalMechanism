package MentalState;

import java.util.Iterator;
import java.util.Map;

import Mechanisms.Mechanisms.AGENT;
import MentalGraph.MentalGraph;
import MetaInformation.Events;

import edu.wpi.cetask.Plan;
import edu.wpi.disco.Disco;
import jess.Context;
import jess.Fact;
import jess.JessException;
import jess.RU;
import jess.Rete;
import jess.Value;

public class MentalState {

	private static Rete JessEngine;
	
	private Fact beliefFact          = null;
	private Fact motiveFact          = null;
	private Fact intentionFact       = null;
	private Fact goalFact            = null;
	private Fact emotionInstanceFact = null;
	
	private static final String strAppraisalModuleTemplates = "templates/mental-states/mental-states-templates.clp";
	
	private MentalGraph mentalGraph;
	private Disco disco;
	
	public enum FACT_TYPE {BELIEF, INTENTION, MOTIVE, GOAL, EMOTION_INSTANCE};
	public enum BELIEF_TYPE {EXTERNAL_EVENT, INTERNAL_EVENT, NONE};
	
	public MentalState(Rete JessEngine, Disco disco) {
		this.JessEngine  = JessEngine;
		this.disco = disco;
		this.mentalGraph = new MentalGraph(this);
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
	
	public void assertBelief(String strTurn, String strBeliefID, String strTask, String strEvent, String strEventType, String strEventOrigin, String strAgent, String strBeliefType, String strBeliefAbout, String strBelief, String strStrength, String strAccuracy, String strFrequency, String strRecency, String strSaliency, String strPersistence) {
		try {
			beliefFact = new Fact("belief", JessEngine);
			beliefFact.setSlotValue("turn", new Value(strTurn, RU.STRING));
			beliefFact.setSlotValue("id", new Value(strBeliefID, RU.STRING));
		    beliefFact.setSlotValue("task", new Value(strTask, RU.STRING));
		    beliefFact.setSlotValue("event", new Value(strEvent, RU.STRING));
		    beliefFact.setSlotValue("event-type", new Value(strEventType, RU.SYMBOL));
		    beliefFact.setSlotValue("event-origin", new Value(strEventOrigin, RU.SYMBOL));
		    beliefFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
		    beliefFact.setSlotValue("belief-type", new Value(strBeliefType, RU.SYMBOL));
		    beliefFact.setSlotValue("belief-about", new Value(strBeliefAbout, RU.SYMBOL));
		    beliefFact.setSlotValue("belief", new Value(strBelief, RU.STRING));
		    beliefFact.setSlotValue("strength", new Value(strStrength, RU.SYMBOL));
		    beliefFact.setSlotValue("accuracy", new Value(strAccuracy, RU.SYMBOL));
		    beliefFact.setSlotValue("frequency", new Value(strFrequency, RU.SYMBOL));
		    beliefFact.setSlotValue("recency", new Value(strRecency, RU.SYMBOL));
		    beliefFact.setSlotValue("saliency", new Value(strSaliency, RU.SYMBOL));
		    beliefFact.setSlotValue("persistence", new Value(strPersistence, RU.SYMBOL));
		    JessEngine.assertFact(beliefFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertMotive(String strTurn, String strMotiveID, String strTask, String strEvent, String strAgent, String strMotive, String strMotiveStatus, String strMotiveType) {
		try {
			motiveFact = new Fact("motive", JessEngine);
			motiveFact.setSlotValue("turn", new Value(strTurn, RU.STRING));
			motiveFact.setSlotValue("id", new Value(strMotiveID, RU.STRING));
			motiveFact.setSlotValue("task", new Value(strTask, RU.STRING));
			motiveFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			motiveFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			motiveFact.setSlotValue("motive", new Value(strMotive, RU.STRING));
			motiveFact.setSlotValue("motive-status", new Value(strMotiveStatus, RU.SYMBOL));
			motiveFact.setSlotValue("motive-type", new Value(strMotiveType, RU.SYMBOL));
		    JessEngine.assertFact(motiveFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertIntention(String strTurn, String strIntentionID, String strTask, String strEvent, String strAgent, String strIntention) {
		try {
			intentionFact = new Fact("intention", JessEngine);
			intentionFact.setSlotValue("turn", new Value(strTurn, RU.STRING));
			intentionFact.setSlotValue("id", new Value(strIntentionID, RU.STRING));
			intentionFact.setSlotValue("task", new Value(strTask, RU.STRING));
			intentionFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			intentionFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			intentionFact.setSlotValue("intention", new Value(strIntention, RU.STRING));
		    JessEngine.assertFact(intentionFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertGoal(Integer intTurn, String strGoalID, Events event, AGENT agent, Goal goal, Goal parent) {
		try {
			goalFact = new Fact("goal", JessEngine);
			goalFact.setSlotValue("turn", new Value(intTurn, RU.INTEGER));
			goalFact.setSlotValue("id", new Value(strGoalID, RU.STRING));
			goalFact.setSlotValue("event", new Value(event));
			goalFact.setSlotValue("agent", new Value(agent));
			goalFact.setSlotValue("goal", new Value(goal));
			goalFact.setSlotValue("parent", new Value(parent));
		    JessEngine.assertFact(goalFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void assertEmotionInstance(String strTurn, String strEmotionInstanceID, String strTask, String strEvent, String strAgent, String strEmotionInstance) {
		try {
			emotionInstanceFact = new Fact("emotion-instance", JessEngine);
			emotionInstanceFact.setSlotValue("turn", new Value(strTurn, RU.STRING));
			emotionInstanceFact.setSlotValue("id", new Value(strEmotionInstanceID, RU.STRING));
			emotionInstanceFact.setSlotValue("task", new Value(strTask, RU.STRING));
			emotionInstanceFact.setSlotValue("event", new Value(strEvent, RU.STRING));
			emotionInstanceFact.setSlotValue("agent", new Value(strAgent, RU.SYMBOL));
			emotionInstanceFact.setSlotValue("emotion-instance", new Value(strEmotionInstance, RU.SYMBOL));
		    JessEngine.assertFact(emotionInstanceFact);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public Fact getFact(String strFactID) {
		
		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();

				if (targetFact.getSlotValue("id").toString().equals(strFactID)) {
					return targetFact;
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	// Example: ms.getFactID(JessEngine, FACT_TYPE.GOAL, "fix-problem");
	//returns: Fact's ID in working memory.
	public String getFactID(FACT_TYPE factType, String strFact) {
		
		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if(targetFact.getName().contains("MENTAL-STATE::belief") && factType.equals(FACT_TYPE.BELIEF)) {
					if (targetFact.getSlotValue("belief").toString().equals(strFact))
						return targetFact.getSlotValue("id").toString();
				}
				else if(targetFact.getName().contains("MENTAL-STATE::intention") && factType.equals(FACT_TYPE.INTENTION)) {
					if (targetFact.getSlotValue("intention").toString().substring(1, targetFact.getSlotValue("intention").toString().length()-1).equals(strFact))
						return targetFact.getSlotValue("id").toString();
				}
				else if(targetFact.getName().contains("MENTAL-STATE::motive") && factType.equals(FACT_TYPE.MOTIVE)) {
					if (targetFact.getSlotValue("motive").toString().substring(1, targetFact.getSlotValue("motive").toString().length()-1).equals(strFact))
						return targetFact.getSlotValue("id").toString();
				}
				else if(targetFact.getName().contains("MENTAL-STATE::goal") && factType.equals(FACT_TYPE.GOAL)) {
					if (targetFact.getSlotValue("goal").toString().substring(1, targetFact.getSlotValue("goal").toString().length()-1).equals(strFact))
						return targetFact.getSlotValue("id").toString();
				}
				else if(targetFact.getName().contains("MENTAL-STATE::emotion-instance") && factType.equals(FACT_TYPE.EMOTION_INSTANCE)) {
					if (targetFact.getSlotValue("emotion-instance").toString().equals(strFact))
						return targetFact.getSlotValue("id").toString();
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	//Example (modifying vetices):
	
//    Fact tempFact = ms.getFact(JessEngine, "E1-1");
//    Map<String, String> emotionMap = new HashMap<String, String>();
//    emotionMap.put("id", "E2-1");
//    emotionMap.put("agent", "HUMAN");
//    if(tempFact != null) ms.modifyEmotionInstance(tempFact, emotionMap);
	
	public void modifyBelief(Fact beliefFact, Map<String, String> beliefParameters) {
		try {
			if(beliefParameters.containsKey("id")) beliefFact.setSlotValue("id", new Value(beliefParameters.get("id"), RU.STRING));
			if(beliefParameters.containsKey("task")) beliefFact.setSlotValue("task", new Value(beliefParameters.get("task"), RU.STRING));
			if(beliefParameters.containsKey("event")) beliefFact.setSlotValue("event", new Value(beliefParameters.get("event"), RU.STRING));
			if(beliefParameters.containsKey("event-type")) beliefFact.setSlotValue("event-type", new Value(beliefParameters.get("event-type"), RU.SYMBOL));
			if(beliefParameters.containsKey("event-origin")) beliefFact.setSlotValue("event-origin", new Value(beliefParameters.get("event-origin"), RU.SYMBOL));
			if(beliefParameters.containsKey("agent")) beliefFact.setSlotValue("agent", new Value(beliefParameters.get("agent"), RU.SYMBOL));
			if(beliefParameters.containsKey("belief-type")) beliefFact.setSlotValue("belief-type", new Value(beliefParameters.get("belief-type"), RU.SYMBOL));
			if(beliefParameters.containsKey("belief-about")) beliefFact.setSlotValue("belief-about", new Value(beliefParameters.get("belief-about"), RU.SYMBOL));
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
			if(intentionParameters.containsKey("agent")) intentionFact.setSlotValue("agent", new Value(intentionParameters.get("agent"), RU.SYMBOL));
			if(intentionParameters.containsKey("intention")) intentionFact.setSlotValue("intention", new Value(intentionParameters.get("intention"), RU.STRING));
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void modifyGoal(Fact goalFact, Map<String, String> goalParameters) {
		try {
			if(goalParameters.containsKey("id")) goalFact.setSlotValue("id", new Value(goalParameters.get("id"), RU.STRING));
			if(goalParameters.containsKey("task")) goalFact.setSlotValue("task", new Value(goalParameters.get("task"), RU.STRING));
			if(goalParameters.containsKey("event")) goalFact.setSlotValue("event", new Value(goalParameters.get("event"), RU.STRING));
			if(goalParameters.containsKey("agent")) goalFact.setSlotValue("agent", new Value(goalParameters.get("agent"), RU.SYMBOL));
			if(goalParameters.containsKey("goal")) goalFact.setSlotValue("goal", new Value(goalParameters.get("goal"), RU.STRING));
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void modifyMotive(Fact motiveFact, Map<String, String> motiveParameters) {
		try {
			if(motiveParameters.containsKey("id")) motiveFact.setSlotValue("id", new Value(motiveParameters.get("id"), RU.STRING));
			if(motiveParameters.containsKey("task")) motiveFact.setSlotValue("task", new Value(motiveParameters.get("task"), RU.STRING));
			if(motiveParameters.containsKey("event")) motiveFact.setSlotValue("event", new Value(motiveParameters.get("event"), RU.STRING));
			if(motiveParameters.containsKey("agent")) motiveFact.setSlotValue("agent", new Value(motiveParameters.get("agent"), RU.SYMBOL));
			if(motiveParameters.containsKey("motive")) motiveFact.setSlotValue("motive", new Value(motiveParameters.get("motive"), RU.STRING));
			if(motiveParameters.containsKey("motive-status")) motiveFact.setSlotValue("motive-status", new Value(motiveParameters.get("motive-status"), RU.SYMBOL));
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void modifyEmotionInstance(Fact emotionInstanceFact, Map<String, String> emotionInstanceParameters) {
		try {
			if(emotionInstanceParameters.containsKey("id")) emotionInstanceFact.setSlotValue("id", new Value(emotionInstanceParameters.get("id"), RU.STRING));
			if(emotionInstanceParameters.containsKey("task")) emotionInstanceFact.setSlotValue("task", new Value(emotionInstanceParameters.get("task"), RU.STRING));
			if(emotionInstanceParameters.containsKey("event")) emotionInstanceFact.setSlotValue("event", new Value(emotionInstanceParameters.get("event"), RU.STRING));
			if(emotionInstanceParameters.containsKey("agent")) emotionInstanceFact.setSlotValue("agent", new Value(emotionInstanceParameters.get("agent"), RU.SYMBOL));
			if(emotionInstanceParameters.containsKey("emotion-instance")) emotionInstanceFact.setSlotValue("emotion-instance", new Value(emotionInstanceParameters.get("emotion-instance"), RU.SYMBOL));
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public BELIEF_TYPE getBeliefType(String beliefFactID) {
		
		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if (targetFact.getName().contains("belief")) {
					if (targetFact.getSlotValue("id").toString().equals(beliefFactID)) {
						if (targetFact.getSlotValue("event-origin").equals(BELIEF_TYPE.EXTERNAL_EVENT.toString()))
							return BELIEF_TYPE.EXTERNAL_EVENT;
						else if (targetFact.getSlotValue("event-origin").equals(BELIEF_TYPE.INTERNAL_EVENT.toString()))
							return BELIEF_TYPE.INTERNAL_EVENT;
					}
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		return BELIEF_TYPE.NONE;
	}
	
	public String getBeliefEventOrigin(String beliefFactID) {
		
		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if (targetFact.getName().contains("belief")) {
					if (targetFact.getSlotValue("id").toString().equals(beliefFactID)) {
						return targetFact.getSlotValue("event-origin").toString();
					}
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	public String getBeliefEventType(String beliefFactID) {
		
		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if (targetFact.getName().contains("belief")) {
					if (targetFact.getSlotValue("id").toString().equals(beliefFactID)) {
						return targetFact.getSlotValue("event-type").toString();
					}
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	public Fact extractGoal(String strTurn) {
		
		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if (targetFact.getName().contains("MENTAL-STATE::goal")) {
					if (targetFact.getSlotValue("turn").toString().equals(strTurn)) {
						return targetFact;
					}
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	public Goal getEventRelatedGoal(Fact inputGoal) {
		
		try {
			Context context = JessEngine.getGlobalContext();
			
			Value turnFact   = inputGoal.getSlotValue("turn").resolveValue(context);
			Value idFact     = inputGoal.getSlotValue("id").resolveValue(context);
			Value eventFact  = inputGoal.getSlotValue("event").resolveValue(context);
			Value agentFact  = inputGoal.getSlotValue("agent").resolveValue(context);
			Value goalFact   = inputGoal.getSlotValue("goal").resolveValue(context);
			Value parentFact = inputGoal.getSlotValue("parent").resolveValue(context);

			int turn     = turnFact.intValue(context);
			String id    = idFact.stringValue(context);
			Events event = (Events)eventFact.javaObjectValue(context);
			AGENT agent  = (AGENT)agentFact.javaObjectValue(context);
			Plan goal    = (Plan)goalFact.javaObjectValue(context);
			Plan parent  = (Plan)parentFact.javaObjectValue(context);
			
			return new Goal(goal, turn, id, event, agent);
		} catch (JessException e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	public Rete getJessEngine() { return JessEngine; }
	
	public MentalGraph getMentalGraph() { return mentalGraph; }
	
	public Disco getDisco() { return disco; }
}
