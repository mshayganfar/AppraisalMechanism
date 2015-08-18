package Appraisal;

import java.util.Iterator;
import java.util.List;

import jess.Fact;
import jess.JessException;
import jess.Rete;

import Collaboration.Collaboration.FOCUS_STATUS;
import Collaboration.Collaboration.RECIPE_APPLICABILITY;
import Collaboration.Collaboration.TASK_PRECONDITION_STATUS;
import Collaboration.Collaboration.TOP_LEVEL_TASK_STATUS;
import MentalStates.*;
import MentalStates.MentalStates.BELIEF_TYPE;
import MentalStates.MentalStates.EVENT_TYPE;
import MentalGraph.*;

public class Desirability extends AppraisalProcesses{
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., 2 in B2-3.
	public boolean isEventDesirable(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, int intEventTurn) {
		
		double deltaUtility     = 0.0;
		double utteranceUtility = 0.0;
		double actionUtility    = 0.0;
		double emotionUtility   = 0.0;
		
//		List<Edge> shortestPathList = mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, "B1-1"), mentalStates.getFact(JessEngine, "G1-1"));
		
		if (mentalStates.getBeliefEventOrigin(JessEngine, "B1-1").equals(BELIEF_TYPE.EXTERNAL_EVENT.toString()))
		{
			utteranceUtility = getUtteranceUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE, intEventTurn);
			actionUtility    = getActionUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.ACTION, intEventTurn);
			emotionUtility   = getEmotionUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.EMOTION, intEventTurn);
			
			double dblUtteranceUtilityWeight = getUtteranceUtilityWeight();
			double dblActioUtilityWeight     = getActionUtilityWeight();
			double dblEmotionUtilityWeight   = getEmotionUtilityWeight();
			
			deltaUtility = (((utteranceUtility * dblUtteranceUtilityWeight) + (actionUtility * dblActioUtilityWeight) + (emotionUtility * dblEmotionUtilityWeight)) 
							/ (dblUtteranceUtilityWeight + dblActioUtilityWeight + dblEmotionUtilityWeight));
		}
		else if (mentalStates.getBeliefEventOrigin(JessEngine, "B1-1").equals(BELIEF_TYPE.INTERNAL_EVENT)){
			
			/** To Do: Think whether desirability is important for internal events, e.g., belief formation.*/
		}
		
		if (deltaUtility > getHumanEmotionalThreshold())
			return true;
		else
			return false;
	}
	
	private double getUtteranceUtilityWeight() { return 1.0; }
	private double getActionUtilityWeight()    { return 1.0; }
	private double getEmotionUtilityWeight()   { return 1.0; }
	
	private double getUtteranceUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, int intEventTurn) {
		
		return getMentalStatesUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE, intEventTurn);
	}
	
	private double getActionUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, int intEventTurn) {
		
		return getMentalStatesUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.ACTION, intEventTurn);
	}
	
	private double getEmotionUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, int intEventTurn) {
		
		return getMentalStatesUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.EMOTION, intEventTurn);
	}
	
	// 
	private double getMentalStatesUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, int intEventTurn) {
		
		int intMentalStateUtilityCounter = 0;
		String strBeliefID = null;
		double mentalStateUtilityValue = 0.0;

		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if ((targetFact.getName().contains("belief"))) {
					strBeliefID = targetFact.getSlotValue("id").toString();
					if (strBeliefID.toString().contains("B" + intEventTurn + "-"))
						if (mentalStates.getBeliefEventType(JessEngine, strBeliefID).equals(eventType.toString())) {
							mentalStateUtilityValue += getPathUtility(JessEngine, mentalStates, mentalGraph, strBeliefID);
							intMentalStateUtilityCounter++;
						}
				}
			} catch (JessException e) {
				e.printStackTrace();
			}
	    }
		
		if (intMentalStateUtilityCounter > 0)
			return ((double)mentalStateUtilityValue / intMentalStateUtilityCounter);
		else
			return 0.0;
	}
	
	// Returns a weighted average of all five mental states' utilities.
	public double getPathUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, String strBeliefID) {

		double dblBeliefUtilityValue          = 0.0;
		double dblIntentionUtilityValue       = 0.0;
		double dblMotiveUtilityValue          = 0.0;
		double dblGoalUtilityValue            = 0.0;
		double dblEmotionInstanceUtilityValue = 0.0;
		
		List<Fact> pathList = mentalGraph.getShortestPathVertices(mentalStates.getFact(JessEngine, strBeliefID), mentalStates.getFact(JessEngine, "\"G1-1\""));
		
		for (int i = 0 ; i < pathList.size() ; i++) {
			if (pathList.get(i).getName().toString().contains("belief")) dblBeliefUtilityValue = getBeliefUtility(pathList.get(i));
			if (pathList.get(i).getName().toString().contains("motive")) dblMotiveUtilityValue = getMotiveUtility(pathList.get(i));
			if (pathList.get(i).getName().toString().contains("intention")) dblIntentionUtilityValue = getIntentionUtility(pathList.get(i));
			if (pathList.get(i).getName().toString().contains("goal")) dblGoalUtilityValue = getGoalUtility(pathList.get(i));
			if (pathList.get(i).getName().toString().contains("emotion-instance")) dblEmotionInstanceUtilityValue = getEmotionInstanceUtility(pathList.get(i));
		}
		
		double dblBeliefWeight          = getBeliefWeight();
		double dblIntentionWeight       = getIntentionWeight();
		double dblMotiveWeight          = getMotiveWeight();
		double dblGoalWeight            = getGoalWeight();
		double dblEmotionInstanceWeight = getEmotionInstanceWeight();
		
		return (((dblBeliefWeight * dblBeliefUtilityValue) + (dblIntentionWeight * dblIntentionUtilityValue) + (dblMotiveWeight * dblMotiveUtilityValue)
				+ (dblGoalWeight * dblGoalUtilityValue) + (dblEmotionInstanceWeight * dblEmotionInstanceUtilityValue)) 
				/ (dblBeliefWeight + dblIntentionWeight + dblMotiveWeight + dblGoalWeight + dblEmotionInstanceWeight));
	}
	
	public double getCollaborationUtility() throws JessException {

		if (collaboration.getTopLevelTaskStatus().equals(TOP_LEVEL_TASK_STATUS.ACHIEVED)) return 1.0;
		if (collaboration.getTopLevelTaskStatus().equals(TOP_LEVEL_TASK_STATUS.BLOCKED)) return -1.0;
		if (collaboration.getTopLevelTaskStatus().equals(TOP_LEVEL_TASK_STATUS.INPROGRESS)) {
			if (collaboration.getFocusStatus().equals(FOCUS_STATUS.ACHIEVED)) return 0.75;
			if (collaboration.getFocusStatus().equals(FOCUS_STATUS.BLOCKED)) return -0.75;
			if (collaboration.getFocusStatus().equals(FOCUS_STATUS.INPROGRESS)) return 0.25;
			if (collaboration.getFocusStatus().equals(FOCUS_STATUS.UNKNOWN)) {
				if (collaboration.getTaskPreconditionStatus().equals(TASK_PRECONDITION_STATUS.SATISFIED)) return 0.5;
				if (collaboration.getTaskPreconditionStatus().equals(TASK_PRECONDITION_STATUS.UNSATISFIED)) return -0.75;
				if (collaboration.getTaskPreconditionStatus().equals(TASK_PRECONDITION_STATUS.UNKNOWN)) {
					if (collaboration.doesContibute(new Fact("Fake Intention", null)) == true) return -0.5;
					if (collaboration.doesContibute(new Fact("Fake Intention", null)) == false) {
						if (collaboration.getRecipeApplicability().equals(RECIPE_APPLICABILITY.APPLICABLE)) return -0.5;
						if (collaboration.getRecipeApplicability().equals(RECIPE_APPLICABILITY.INAPPLICABLE)) return -0.75;
						if (collaboration.getRecipeApplicability().equals(RECIPE_APPLICABILITY.UNKNOWN)) return -0.25;
					}
				}
			}
		}
		
		return 0.0;
	}
}
