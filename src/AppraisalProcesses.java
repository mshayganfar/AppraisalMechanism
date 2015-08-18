import java.util.HashMap;
import java.util.Map;

import jess.Fact;
import jess.JessException;

public class AppraisalProcesses {

	protected Collaboration collaboration;
	
	public AppraisalProcesses() {
		
		collaboration = new Collaboration();
	}
	
	protected double getBeliefUtility(Fact beliefFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("strength", getBeliefStrengthWeight());
		weights.put("accuracy", getBeliefAccuracyWeight());
		weights.put("frequency", getBeliefFrequencyWeight());
		weights.put("recency", getBeliefRecencyWeight());
		weights.put("saliency", getBeliefSaliencyWeight());
		weights.put("persistence", getBeliefPersistenceWeight());
		
		return (((weights.get("strength") * getBeliefStrength(beliefFact)) + (weights.get("accuracy") * getBeliefAccuracy(beliefFact)) 
				+ (weights.get("frequency") * getBeliefFrequency(beliefFact)) + (weights.get("recency") * getBeliefRecency(beliefFact)) 
				+ (weights.get("saliency")*getBeliefSaliency(beliefFact)) + (weights.get("persistence")*getBeliefPersistence(beliefFact))) / 6.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getBeliefStrengthWeight()    { return 1.0; }
	private double getBeliefAccuracyWeight()    { return 1.0; }
	private double getBeliefFrequencyWeight()   { return 1.0; }
	private double getBeliefRecencyWeight()     { return 1.0; }
	private double getBeliefSaliencyWeight()    { return 1.0; }
	private double getBeliefPersistenceWeight() { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getBeliefStrength(Fact beliefFact) {
		
		String strBeliefStrength = null;
		
		try {
			strBeliefStrength = beliefFact.getSlotValue("strength").toString();
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		if (strBeliefStrength.equals("HIGH")) return 1.0;
		if (strBeliefStrength.equals("MEDIUM")) return 0.5;
		if (strBeliefStrength.equals("LOW")) return 0.25;
		
		return 0.0;
	}
	
	private double getBeliefAccuracy(Fact beliefFact) { 
		
		String strBeliefAccuracy = null;
		
		try {
			strBeliefAccuracy = beliefFact.getSlotValue("accuracy").toString();
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		if (strBeliefAccuracy.equals("HIGH")) return 1.0;
		if (strBeliefAccuracy.equals("MEDIUM")) return 0.5;
		if (strBeliefAccuracy.equals("LOW")) return 0.25;
		
		return 0.0;
	}
	
	private double getBeliefFrequency(Fact beliefFact) { 
		
		String strBeliefFrequency = null;
		
		try {
			strBeliefFrequency = beliefFact.getSlotValue("frequency").toString();
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		if (strBeliefFrequency.equals("HIGH")) return 1.0;
		if (strBeliefFrequency.equals("MEDIUM")) return 0.5;
		if (strBeliefFrequency.equals("LOW")) return 0.25;
		
		return 0.0;
	}
	
	private double getBeliefRecency(Fact beliefFact) { 
		
		String strBeliefRecency = null;
		
		try {
			strBeliefRecency = beliefFact.getSlotValue("recency").toString();
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		if (strBeliefRecency.equals("HIGH")) return 1.0;
		if (strBeliefRecency.equals("MEDIUM")) return 0.5;
		if (strBeliefRecency.equals("LOW")) return 0.25;
		
		return 0.0; 
	}
	
	private double getBeliefSaliency(Fact beliefFact) { 
		
		String strBeliefSaliency = null;
		
		try {
			strBeliefSaliency = beliefFact.getSlotValue("saliency").toString();
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		if (strBeliefSaliency.equals("HIGH")) return 1.0;
		if (strBeliefSaliency.equals("MEDIUM")) return 0.5;
		if (strBeliefSaliency.equals("LOW")) return 0.25;
		
		return 0.0;
	}
	
	private double getBeliefPersistence(Fact beliefFact) { 
		
		String strBeliefPersistence = null;
		
		try {
			strBeliefPersistence = beliefFact.getSlotValue("persistence").toString();
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		if (strBeliefPersistence.equals("HIGH")) return 1.0;
		if (strBeliefPersistence.equals("MEDIUM")) return 0.5;
		if (strBeliefPersistence.equals("LOW")) return 0.25;
		
		return 0.0;
	}
	
	// The return value can be computed according to the emotional status of the human.
	protected double getHumanEmotionalThreshold() { return 0.0; }
	
	protected double getIntentionUtility(Fact intentionFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("temporal-status", getIntentionTemporalStatusWeight());
		weights.put("direct-experience", getIntentionDirectExperienceWeight());
		weights.put("certainty", getIntentionCertaintyWeight());
		weights.put("ambivalence", getIntentionAmbivalenceWeight());
		weights.put("affective-cognitive-consistency", getIntentionAffectiveCognitiveConsistencyWeight());
		
		return (((weights.get("temporal-status") * getIntentionTemporalStatus(intentionFact)) + (weights.get("direct-experience") * getIntentionDirectExperience(intentionFact)) 
				+ (weights.get("certainty") * getIntentionCertainty(intentionFact)) + (weights.get("ambivalence") * getIntentionAmbivalence(intentionFact)) 
				+ (weights.get("affective-cognitive-consistency")*getIntentionAffectiveCognitiveConsistency(intentionFact))) / 5.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getIntentionTemporalStatusWeight()                { return 1.0; }
	private double getIntentionDirectExperienceWeight()              { return 1.0; }
	private double getIntentionCertaintyWeight()                     { return 1.0; }
	private double getIntentionAmbivalenceWeight()                   { return 1.0; }
	private double getIntentionAffectiveCognitiveConsistencyWeight() { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getIntentionTemporalStatus(Fact intentionFact)    			 { return 1.0; }
	private double getIntentionDirectExperience(Fact intentionFact)    		     { return 1.0; }
	private double getIntentionCertainty(Fact intentionFact)   				     { return 1.0; }
	private double getIntentionAmbivalence(Fact intentionFact)     			     { return 1.0; }
	private double getIntentionAffectiveCognitiveConsistency(Fact intentionFact) { return 1.0; }
	
	protected double getMotiveUtility(Fact motiveFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("insistence", getMotiveInsistenceWeight());
		weights.put("importance", getMotiveImportanceWeight());
		weights.put("urgency", getMotiveUrgencyWeight());
		weights.put("intensity", getMotiveIntensityWeight());
		weights.put("failure-disruptiveness", getMotiveFailureDisruptivenessWeight());
		
		return (((weights.get("insistence") * getMotiveInsistence(motiveFact)) + (weights.get("importance") * getMotiveImportance(motiveFact)) 
				+ (weights.get("urgency") * getMotiveUrgency(motiveFact)) + (weights.get("intensity") * getMotiveIntensity(motiveFact)) 
				+ (weights.get("failure-disruptiveness")*getMotiveFailureDisruptiveness(motiveFact))) / 5.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getMotiveInsistenceWeight()            { return 1.0; }
	private double getMotiveImportanceWeight()            { return 1.0; }
	private double getMotiveUrgencyWeight()               { return 1.0; }
	private double getMotiveIntensityWeight()             { return 1.0; }
	private double getMotiveFailureDisruptivenessWeight() { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getMotiveInsistence(Fact motiveFact)    	       { return 1.0; }
	private double getMotiveImportance(Fact motiveFact)    		   { return 1.0; }
	private double getMotiveUrgency(Fact motiveFact)   			   { return 1.0; }
	private double getMotiveIntensity(Fact motiveFact)     	       { return 1.0; }
	private double getMotiveFailureDisruptiveness(Fact motiveFact) { return 1.0; }
	
	protected double getGoalUtility(Fact goalFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("proximity", getGoalProximityWeight());
		weights.put("specificity", getGoalSpecificityWeight());
		weights.put("difficulty", getGoalDifficultyWeight());
		
		return (((weights.get("proximity") * getGoalProximity(goalFact)) + (weights.get("specificity") * getGoalSpecificity(goalFact)) 
				+ (weights.get("difficulty") * getGoalDifficulty(goalFact))) / 3.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getGoalProximityWeight()   { return 1.0; }
	private double getGoalSpecificityWeight() { return 1.0; }
	private double getGoalDifficultyWeight()  { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getGoalProximity(Fact goalFact)   { return 1.0; }
	private double getGoalSpecificity(Fact goalFact) { return 1.0; }
	private double getGoalDifficulty(Fact goalFact)  { return 1.0; }
	
	protected double getEmotionInstanceUtility(Fact emotionInstanceFact) {
		
		try {
			switch (emotionInstanceFact.getSlotValue("emotion-instance").toString()) {
				case "JOY":
					return 1.0;
				case "ANGER":
					return -1.0;
				case "HOPE":
					return 1.0;
				case "GUILT":
					return -1.0;
				case "PRIDE":
					return 1.0;
				case "SHAME":
					return -1.0;
				case "WORRY":
					return -1.0;
				case "FRUSTRATION":
					return -1.0;
				case "NEUTRAL":
					return 0.0;
			}
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		return 0.0;
	}
	
	// Min = 0.0 and Max = 1.0
	protected double getBeliefWeight()          { return 1.0; }
	protected double getIntentionWeight()       { return 1.0; }
	protected double getMotiveWeight()          { return 1.0; }
	protected double getGoalWeight()            { return 1.0; }
	protected double getEmotionInstanceWeight() { return 1.0; }
}
