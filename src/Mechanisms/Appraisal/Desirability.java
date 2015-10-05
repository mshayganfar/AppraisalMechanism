package Mechanisms.Appraisal;

import java.util.Iterator;

import jess.Fact;
import jess.JessException;

import Mechanisms.Collaboration.Collaboration.*;

import MentalStates.*;
import MentalStates.MentalStates.BELIEF_TYPE;
import MentalStates.MentalStates.FACT_TYPE;

import MetaInformation.Events;
import MetaInformation.Events.EVENT_TYPE;
import MetaInformation.Turns;
import edu.wpi.cetask.Plan;

public class Desirability extends AppraisalProcesses{
	
	public enum DESIRABILITY {HIGHEST_DESIRABLE, HIGH_DESIRABLE, MEDIUM_DESIRABLE, LOW_DESIRABLE, LOWEST_DESIRABLE, NEUTRAL, HIGHEST_UNDESIRABLE, HIGH_UNDESIRABLE, MEDIUM_UNDESIRABLE, LOW_UNDESIRABLE, LOWEST_UNDESIRABLE};
	
	// TO DO: This method needs to extract the ID of the belief asserted with respect to the new event, e.g., 2 in B2-3.
	public DESIRABILITY isEventDesirable(Events event) {
		
		Goal graphGoal    = mentalState.getMentalGraph().getGraphGoal();
		Goal topLevelGoal = collaboration.getTopLevelGoal(event);
		
		Plan graphGoalPlan    = graphGoal.getPlan();
		Plan topLevelGoalPlan = topLevelGoal.getPlan();
		
		if (collaboration.getGoalStatus(topLevelGoalPlan).equals(TASK_STATUS.ACHIEVED)) return DESIRABILITY.HIGHEST_DESIRABLE;
		else if (!collaboration.getGoalStatus(topLevelGoalPlan).equals(TASK_STATUS.FAILED)) return DESIRABILITY.HIGHEST_UNDESIRABLE;
		else if (!collaboration.getGoalStatus(topLevelGoalPlan).equals(TASK_STATUS.BLOCKED)) return DESIRABILITY.HIGH_UNDESIRABLE;
		else if (!collaboration.getGoalStatus(topLevelGoalPlan).equals(TASK_STATUS.INAPPLICABLE)) return DESIRABILITY.MEDIUM_UNDESIRABLE;
		else if (!collaboration.getGoalStatus(topLevelGoalPlan).equals(TASK_STATUS.PENDING)) return DESIRABILITY.NEUTRAL;
		else if (collaboration.getGoalStatus(topLevelGoalPlan).equals(TASK_STATUS.INPROGRESS)) {
			if (collaboration.getGoalStatus(graphGoalPlan).equals(TASK_STATUS.ACHIEVED)) return DESIRABILITY.HIGH_DESIRABLE;
			else if (!collaboration.getGoalStatus(graphGoalPlan).equals(TASK_STATUS.FAILED)) return DESIRABILITY.HIGH_UNDESIRABLE;
			else if (collaboration.getGoalStatus(graphGoalPlan).equals(TASK_STATUS.BLOCKED)) return DESIRABILITY.MEDIUM_UNDESIRABLE;
			else if (collaboration.getGoalStatus(graphGoalPlan).equals(TASK_STATUS.INAPPLICABLE)) return DESIRABILITY.LOW_UNDESIRABLE;
			else if (collaboration.getGoalStatus(graphGoalPlan).equals(TASK_STATUS.PENDING)) return DESIRABILITY.NEUTRAL;
			else if (collaboration.getGoalStatus(topLevelGoalPlan).equals(TASK_STATUS.INPROGRESS)) {
				
				Goal eventGoal = collaboration.recognizeGoal(event);
				
				if(eventGoal == null)
					return DESIRABILITY.HIGH_UNDESIRABLE;

				if (collaboration.doesContribute(eventGoal, graphGoal)) return DESIRABILITY.LOW_DESIRABLE;
				else if (!collaboration.doesContribute(eventGoal, graphGoal)) return DESIRABILITY.MEDIUM_UNDESIRABLE;
			}
		}
		
		return DESIRABILITY.NEUTRAL;
	}
	
	private double getUtteranceUtilityWeight() { return 1.0; }
	private double getActionUtilityWeight()    { return 1.0; }
	private double getEmotionUtilityWeight()   { return 1.0; }
	
	private double getUtteranceUtility(EVENT_TYPE eventType, Turns turn) {
		
		return getMentalStatesUtility(EVENT_TYPE.UTTERANCE, turn);
	}
	
	private double getActionUtility(EVENT_TYPE eventType, Turns turn) {
		
		return getMentalStatesUtility(EVENT_TYPE.ACTION, turn);
	}
	
	private double getEmotionUtility(EVENT_TYPE eventType, Turns turn) {
		
		return getMentalStatesUtility(EVENT_TYPE.EMOTION, turn);
	}
	
	private double getMentalStatesUtility(EVENT_TYPE eventType, Turns turn) {
		
		int intMentalStateUtilityCounter = 0;
		String strBeliefID = null;
		double mentalStateUtilityValue = 0.0;

		Fact targetFact = null;
		Iterator<Fact> factList = mentalState.getJessEngine().listFacts();
		
		while(factList.hasNext()) {
			try {
				targetFact = (Fact)factList.next();
				
				if ((targetFact.getName().contains("MENTAL-STATE::belief"))) {
					if (targetFact.getSlotValue("turn").toString().equals(turn.getLastTurn()))
						if (mentalState.getBeliefEventType(targetFact.getSlotValue("id").toString()).equals(eventType.toString())) {
							mentalStateUtilityValue += getPathUtility(targetFact, mentalState.extractGoal(turn.getLastTurn()));
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
	
	private double getMentalStatesUtility(Turns turn, Events event) {
		
		double deltaUtility     = 0.0;
		double utteranceUtility = 0.0;
		double actionUtility    = 0.0;
		double emotionUtility   = 0.0;
		
//		List<Edge> shortestPathList = mentalGraph.getShortestPath(mentalStates.getFact(JessEngine, "B1-1"), mentalStates.getFact(JessEngine, "G1-1"));
		
		try {
			if (mentalState.getBeliefEventOrigin(mentalState.getFactID(FACT_TYPE.BELIEF, event.getEventRelatedBelief().getSlotValue("belief").toString())).equals(BELIEF_TYPE.EXTERNAL_EVENT.toString()))
			{
				utteranceUtility = getUtteranceUtility(EVENT_TYPE.UTTERANCE, turn);
				actionUtility    = getActionUtility(EVENT_TYPE.ACTION, turn);
				emotionUtility   = getEmotionUtility(EVENT_TYPE.EMOTION, turn);
				
				double dblUtteranceUtilityWeight = getUtteranceUtilityWeight();
				double dblActioUtilityWeight     = getActionUtilityWeight();
				double dblEmotionUtilityWeight   = getEmotionUtilityWeight();
				
				deltaUtility = (((utteranceUtility * dblUtteranceUtilityWeight) + (actionUtility * dblActioUtilityWeight) + (emotionUtility * dblEmotionUtilityWeight)) 
								/ (dblUtteranceUtilityWeight + dblActioUtilityWeight + dblEmotionUtilityWeight));
			}
			else if (mentalState.getBeliefEventOrigin(mentalState.getFactID(FACT_TYPE.BELIEF, event.getEventRelatedBelief().getSlotValue("belief").toString())).equals(BELIEF_TYPE.INTERNAL_EVENT)){
				
				/** To Do: Think whether desirability is important for internal events, e.g., belief formation.*/
			}
		} catch (JessException e) {
			e.printStackTrace();
		}
		
		return deltaUtility;
	}
}
