package blackjack;

import java.util.*;

public class QLearningPolicy implements Policy {
    
    //Map<CardCountingState, Integer> qValues;

    private static Map<PointsState, Double> qValues = new TreeMap<PointsState, Double>();
    private double epsilon  = 0.30;

    public void setEpsion (double ep) {
        epsilon = ep;
    }
   
    @Override
    public Action chooseAction(Game gameState, Player p, List<Action> legalActions) {
        
        if (legalActions.isEmpty())
            throw new RuntimeException("No actions to perform!");

        if(BlackjackUtil.getNumber() < epsilon){
            return BlackjackUtil.getRandomAction(legalActions);
        }
        else{
            return getActionFromQValues(gameState, p, legalActions);
        }
    }
    
    @Override
    public void observe(Game state, Action action, Player p, Game nextState, int reward) {

        PointsState ps = getPointState(state, p, action);
        PointsState nps = getPointState(nextState, p, action);

        double qValue = getQValue(ps);
        double nextStateValue ;
        if( ps.equals(nps) ){
            nextStateValue = getQValue(ps);
        }
        else {
            nextStateValue = getValueFromQValues(nextState, p);
        }
        
        double prevWeightedValue = (1.0 - Configuration.alpha) * qValue;//-32
        double weightedValue = Configuration.alpha * (reward + nextStateValue);//-20
        double newValue = prevWeightedValue + weightedValue;//-36

        qValues.put(ps, newValue);
    }

    private double getQValue(PointsState ps){

        if(qValues.containsKey(ps)) {
            return qValues.get(ps);
        }
        
        return 0.0;
    }

    private Double getValueFromQValues(Game state, Player p){
        double maxValue = Double.NEGATIVE_INFINITY;
        List<Action> legalActions = state.getNextActions();

        for (Action action: legalActions){
            PointsState ps = getPointState(state, p, action);
            double psQValue = getQValue(ps);
            if (psQValue > maxValue){
                maxValue = psQValue;
            }
        }
        
        return maxValue;

    }

    private Action getActionFromQValues(Game state, Player p, List<Action> legalActions){
        // default action
        Action maxAction = null;
        double maxValue = Double.NEGATIVE_INFINITY;

        for (Action action: legalActions){
            PointsState ps = getPointState(state, p, action);
            double psQValue = getQValue(ps);
            
            if (psQValue > maxValue){
                maxValue = psQValue;
                maxAction = action;
            }
        }
        
        if (maxAction == null) {
            return BlackjackUtil.getRandomAction(legalActions);
        }
        
        return maxAction;
    }
    
    public void printQValues () {
        
        Set<PointsState> keys = qValues.keySet();
        for(PointsState key : keys) {
            
            Double val = qValues.get(key);
            int intVal = val.intValue();
            
            System.out.println(key.toString() + " " + intVal);
        }
    }
    
    private PointsState getPointState(Game state, Player p, Action action){

        Hand h = state.getHandFromPlayer(p);

        return new PointsState(h.numberOfAces(), h.handValueWithoutAces(), action);
    }
}

