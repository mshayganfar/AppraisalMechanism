package Mechanisms.Appraisal;

import java.util.Iterator;

import jess.Fact;
import jess.JessException;
import jess.Rete;

import Mechanisms.Collaboration.Collaboration.*;

import MentalStates.*;
import MentalStates.MentalStates.BELIEF_TYPE;
import MentalStates.MentalStates.FACT_TYPE;

import MentalGraph.*;

import MetaInformation.Events;
import MetaInformation.Events.EVENT_TYPE;
import MetaInformation.Turns;

public class Desirability extends AppraisalProcesses{
	
	public enum DESIRABILITY {HIGHEST_DESIRABLE, HIGH_DESIRABLE, MEDIUM_DESIRABLE, LOW_DESIRABLE, NEUTRAL, HIGHEST_UNDESIRABLE, HIGH_UNDESIRABLE, MEDIUM_UNDESIRABLE, LOW_UNDESIRABLE};
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., 2 in B2-3.
	public DESIRABILITY isEventDesirable(MentalGraph mentalGraph, Events event) {
		
		Fact graphGoal    = mentalGraph.getGraphGoal();
		Fact topLevelGoal = collaboration.getTopLevelGoal();
		
		if (collaboration.getGoalStatus(topLevelGoal).equals(TOP_LEVEL_TASK_STATUS.ACHIEVED)) return DESIRABILITY.HIGHEST_DESIRABLE;
		if (collaboration.getGoalStatus(topLevelGoal).equals(TOP_LEVEL_TASK_STATUS.BLOCKED)) return DESIRABILITY.HIGHEST_UNDESIRABLE;
		if (collaboration.getGoalStatus(topLevelGoal).equals(TOP_LEVEL_TASK_STATUS.INPROGRESS)) {
			if (collaboration.getGoalStatus(graphGoal).equals(FOCUS_STATUS.ACHIEVED)) return DESIRABILITY.HIGH_DESIRABLE;
			if (collaboration.getGoalStatus(graphGoal).equals(FOCUS_STATUS.BLOCKED)) return DESIRABILITY.HIGH_UNDESIRABLE;
			if (collaboration.getGoalStatus(graphGoal).equals(FOCUS_STATUS.INPROGRESS)) return DESIRABILITY.MEDIUM_DESIRABLE;
			if (collaboration.getGoalStatus(graphGoal).equals(FOCUS_STATUS.UNKNOWN)) {
				
				Fact eventGoal = collaboration.recognizeGoal(event);
				
				if(eventGoal == null)
					return DESIRABILITY.HIGH_UNDESIRABLE;

				if (collaboration.getGoalPreconditionStatus(eventGoal).equals(TASK_PRECONDITION_STATUS.SATISFIED)) return DESIRABILITY.LOW_DESIRABLE;
				if (collaboration.getGoalPreconditionStatus(eventGoal).equals(TASK_PRECONDITION_STATUS.UNSATISFIED)) return DESIRABILITY.LOW_UNDESIRABLE;
				if (collaboration.getGoalPreconditionStatus(eventGoal).equals(TASK_PRECONDITION_STATUS.UNKNOWN)) {
					
					if (collaboration.doesContibute(eventGoal, graphGoal)) return DESIRABILITY.NEUTRAL;
					if (!collaboration.doesContibute(eventGoal, graphGoal)) return DESIRABILITY.MEDIUM_UNDESIRABLE;
				}
			}
		}
		
		return DESIRABILITY.NEUTRAL;
	}
	
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
}
