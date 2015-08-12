import java.util.HashMap;
import java.util.Map;

import jess.Fact;
import jess.Rete;

public class RelevanceAppraisalProcess {
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., B1-1.
	public double isEventRelevant(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph) {
		
		if(mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, "B1-1"), mentalStates.getFact(JessEngine, "G1-1")).size() == 0)
			return 0;
		else {
			// This utility belongs to the belief corresponded to the recent (new) event.
			double beliefUtility = getBeliefUtility(mentalStates.getFact(JessEngine, "B1-1"));
			if(beliefUtility > getHumanEmotionalThreshold())
				return beliefUtility;
			else
				return 0;
		}
	}
	
	private double getBeliefUtility(Fact beliefFact) {
		
		Map<String, Double> weights =  new HashMap<String, Double>();
		
		weights.put("strength", getBeliefStrengthWeight());
		weights.put("accuracy", getBeliefAccuracyWeight());
		weights.put("frequency", getBeliefFrequencyWeight());
		weights.put("recency", getBeliefRecencyWeight());
		weights.put("saliency", getBeliefSaliencyWeight());
		weights.put("persistence", getBeliefPersistenceWeight());
		
		return (((weights.get("strength") * getBeliefStrength()) + (weights.get("accuracy") * getBeliefAccuracy()) + (weights.get("frequency") * getBeliefFrequency()) 
				+ (weights.get("recency") * getBeliefRecency()) + (weights.get("saliency")*getBeliefSaliency()) + (weights.get("persistence")*getBeliefPersistence()))/6.0);
	}
	
	// The return value can be computed according to the emotional status of the human.
	private double getHumanEmotionalThreshold() { return 0.0; }
	
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
}
