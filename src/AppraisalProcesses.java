import java.util.HashMap;
import java.util.Map;

import jess.Fact;


public class AppraisalProcesses {

	protected double getBeliefUtility(Fact beliefFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("strength", getBeliefStrengthWeight());
		weights.put("accuracy", getBeliefAccuracyWeight());
		weights.put("frequency", getBeliefFrequencyWeight());
		weights.put("recency", getBeliefRecencyWeight());
		weights.put("saliency", getBeliefSaliencyWeight());
		weights.put("persistence", getBeliefPersistenceWeight());
		
		return (((weights.get("strength") * getBeliefStrength()) + (weights.get("accuracy") * getBeliefAccuracy()) 
				+ (weights.get("frequency") * getBeliefFrequency()) + (weights.get("recency") * getBeliefRecency()) 
				+ (weights.get("saliency")*getBeliefSaliency()) + (weights.get("persistence")*getBeliefPersistence())) / 6.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getBeliefStrengthWeight()    { return 1.0; }
	private double getBeliefAccuracyWeight()    { return 1.0; }
	private double getBeliefFrequencyWeight()   { return 1.0; }
	private double getBeliefRecencyWeight()     { return 1.0; }
	private double getBeliefSaliencyWeight()    { return 1.0; }
	private double getBeliefPersistenceWeight() { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getBeliefStrength()    { return 1.0; }
	private double getBeliefAccuracy()    { return 1.0; }
	private double getBeliefFrequency()   { return 1.0; }
	private double getBeliefRecency()     { return 1.0; }
	private double getBeliefSaliency()    { return 1.0; }
	private double getBeliefPersistence() { return 1.0; }
	
	// The return value can be computed according to the emotional status of the human.
	protected double getHumanEmotionalThreshold() { return 0.0; }
	
	protected double getIntentionUtility(Fact beliefFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("temporal-status", getIntentionTemporalStatusWeight());
		weights.put("direct-experience", getIntentionDirectExperienceWeight());
		weights.put("certainty", getIntentionCertaintyWeight());
		weights.put("ambivalence", getIntentionAmbivalenceWeight());
		weights.put("affective-cognitive-consistency", getIntentionAffectiveCognitiveConsistencyWeight());
		
		return (((weights.get("temporal-status") * getIntentionTemporalStatus()) + (weights.get("direct-experience") * getIntentionDirectExperience()) 
				+ (weights.get("certainty") * getIntentionCertainty()) + (weights.get("ambivalence") * getIntentionAmbivalence()) 
				+ (weights.get("affective-cognitive-consistency")*getIntentionAffectiveCognitiveConsistency())) / 5.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getIntentionTemporalStatusWeight()                { return 1.0; }
	private double getIntentionDirectExperienceWeight()              { return 1.0; }
	private double getIntentionCertaintyWeight()                     { return 1.0; }
	private double getIntentionAmbivalenceWeight()                   { return 1.0; }
	private double getIntentionAffectiveCognitiveConsistencyWeight() { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getIntentionTemporalStatus()    			   { return 1.0; }
	private double getIntentionDirectExperience()    		   { return 1.0; }
	private double getIntentionCertainty()   				   { return 1.0; }
	private double getIntentionAmbivalence()     			   { return 1.0; }
	private double getIntentionAffectiveCognitiveConsistency() { return 1.0; }
	
	protected double getMotiveUtility(Fact beliefFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("insistence", getMotiveInsistenceWeight());
		weights.put("importance", getMotiveImportanceWeight());
		weights.put("urgency", getMotiveUrgencyWeight());
		weights.put("intensity", getMotiveIntensityWeight());
		weights.put("failure-disruptiveness", getMotiveFailureDisruptivenessWeight());
		
		return (((weights.get("insistence") * getMotiveInsistence()) + (weights.get("importance") * getMotiveImportance()) 
				+ (weights.get("urgency") * getMotiveUrgency()) + (weights.get("intensity") * getMotiveIntensity()) 
				+ (weights.get("failure-disruptiveness")*getMotiveFailureDisruptiveness())) / 5.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getMotiveInsistenceWeight()            { return 1.0; }
	private double getMotiveImportanceWeight()            { return 1.0; }
	private double getMotiveUrgencyWeight()               { return 1.0; }
	private double getMotiveIntensityWeight()             { return 1.0; }
	private double getMotiveFailureDisruptivenessWeight() { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getMotiveInsistence()    	    { return 1.0; }
	private double getMotiveImportance()    		{ return 1.0; }
	private double getMotiveUrgency()   			{ return 1.0; }
	private double getMotiveIntensity()     	    { return 1.0; }
	private double getMotiveFailureDisruptiveness() { return 1.0; }
	
	protected double getGoalUtility(Fact beliefFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("proximity", getGoalProximityWeight());
		weights.put("specificity", getGoalSpecificityWeight());
		weights.put("difficulty", getGoalDifficultyWeight());
		
		return (((weights.get("proximity") * getGoalProximity()) + (weights.get("specificity") * getGoalSpecificity()) 
				+ (weights.get("difficulty") * getGoalDifficulty())) / 3.0);
	}
	
	// Min = 0.0 and Max = 1.0
	private double getGoalProximityWeight()   { return 1.0; }
	private double getGoalSpecificityWeight() { return 1.0; }
	private double getGoalDifficultyWeight()  { return 1.0; }
	
	// Min = 0.0 and Max = 1.0
	private double getGoalProximity()   { return 1.0; }
	private double getGoalSpecificity() { return 1.0; }
	private double getGoalDifficulty()  { return 1.0; }
}
