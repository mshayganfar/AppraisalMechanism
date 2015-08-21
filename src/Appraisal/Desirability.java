package Appraisal;

import java.util.Iterator;

import jess.Fact;
import jess.JessException;
import jess.Rete;

import Collaboration.Collaboration.FOCUS_STATUS;
import Collaboration.Collaboration.RECIPE_APPLICABILITY;
import Collaboration.Collaboration.TASK_PRECONDITION_STATUS;
import Collaboration.Collaboration.TOP_LEVEL_TASK_STATUS;
import MentalStates.*;
import MentalStates.MentalStates.BELIEF_TYPE;
import MentalStates.MentalStates.FACT_TYPE;
import MentalGraph.*;
import MetaInformation.Events;
import MetaInformation.Events.EVENT_TYPE;
import MetaInformation.Turns;

public class Desirability extends AppraisalProcesses{
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., 2 in B2-3.
	public boolean isEventDesirable(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, Turns turn, Events event) {
		
//		double dblUtility = 0.0;
		
//		double dblMentalStatesUtility  = getMentalStatesUtility(JessEngine, mentalStates, mentalGraph, turn, event);
		double dblCollaborationUtility = 0.0;
		try {
			dblCollaborationUtility = getCollaborationUtility();
		} catch (JessException e) {
			e.printStackTrace();
		}		
		
//		dblUtility = ((dblMentalStatesUtility * getMentalStatesUtilityWeight()) + (dblCollaborationUtility * getCollaborationUtilityWeight()))
//					/(getMentalStatesUtilityWeight() + getCollaborationUtilityWeight());
		
		if (dblCollaborationUtility > getHumanEmotionalThreshold())
			return true;
		else
			return false;
	}
	
	private double getMentalStatesUtilityWeight()  { return 1.0; }
	private double getCollaborationUtilityWeight() { return 1.0; }
	
	private double getUtteranceUtilityWeight() { return 1.0; }
	private double getActionUtilityWeight()    { return 1.0; }
	private double getEmotionUtilityWeight()   { return 1.0; }
	
	private double getUtteranceUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, Turns turn) {
		
		return getMentalStatesUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE, turn);
	}
	
	private double getActionUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, Turns turn) {
		
		return getMentalStatesUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.ACTION, turn);
	}
	
	private double getEmotionUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, Turns turn) {
		
		return getMentalStatesUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.EMOTION, turn);
	}
	
	private double getMentalStatesUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, EVENT_TYPE eventType, Turns turn) {
		
		int intMentalStateUtilityCounter = 0;
		String strBeliefID = null;
		double mentalStateUtilityValue = 0.0;

		Fact targetFact = null;
		Iterator<Fact> factList = JessEngine.listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if ((targetFact.getName().contains("MENTAL-STATE::belief"))) {
					if (targetFact.getSlotValue("turn").toString().equals(turn.getLastTurn()))
						if (mentalStates.getBeliefEventType(JessEngine, targetFact.getSlotValue("id").toString()).equals(eventType.toString())) {
							mentalStateUtilityValue += getPathUtility(JessEngine, mentalStates, mentalGraph, targetFact, mentalStates.extractGoal(JessEngine, turn.getLastTurn()));
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
	
	private double getMentalStatesUtility(Rete JessEngine, MentalStates mentalStates, MentalGraph mentalGraph, Turns turn, Events event) {
		
		double deltaUtility     = 0.0;
		double utteranceUtility = 0.0;
		double actionUtility    = 0.0;
		double emotionUtility   = 0.0;
		
//		List<Edge> shortestPathList = mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, "B1-1"), mentalStates.getFact(JessEngine, "G1-1"));
		
		try {
			if (mentalStates.getBeliefEventOrigin(JessEngine, mentalStates.getFactID(JessEngine, FACT_TYPE.BELIEF, event.getEventRelatedBelief().getSlotValue("belief").toString())).equals(BELIEF_TYPE.EXTERNAL_EVENT.toString()))
			{
				utteranceUtility = getUtteranceUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.UTTERANCE, turn);
				actionUtility    = getActionUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.ACTION, turn);
				emotionUtility   = getEmotionUtility(JessEngine, mentalStates, mentalGraph, EVENT_TYPE.EMOTION, turn);
				
				double dblUtteranceUtilityWeight = getUtteranceUtilityWeight();
				double dblActioUtilityWeight     = getActionUtilityWeight();
				double dblEmotionUtilityWeight   = getEmotionUtilityWeight();
				
				deltaUtility = (((utteranceUtility * dblUtteranceUtilityWeight) + (actionUtility * dblActioUtilityWeight) + (emotionUtility * dblEmotionUtilityWeight)) 
								/ (dblUtteranceUtilityWeight + dblActioUtilityWeight + dblEmotionUtilityWeight));
			}
			else if (mentalStates.getBeliefEventOrigin(JessEngine, mentalStates.getFactID(JessEngine, FACT_TYPE.BELIEF, event.getEventRelatedBelief().getSlotValue("belief").toString())).equals(BELIEF_TYPE.INTERNAL_EVENT)){
				
				/** To Do: Think whether desirability is important for internal events, e.g., belief formation.*/
			}
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		return deltaUtility;
	}
	
	private double getCollaborationUtility() throws JessException {

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
