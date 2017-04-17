package blackjack;

import java.util.*;

public class QLearningPolicy implements Policy {
    
    //Map<CardCountingState, Integer> qValues;

    private static Map<PointsState,Double> qValues = new TreeMap<PointsState, Double>();
    private double epsilon  = 0.05;
    BlackjackUtil util = new BlackjackUtil();
    
   
    @Override
    public Action chooseAction(Game gameState, Player p, List<Action> actions) {
        
        List<Action> legalActions = actions;
        
        if (actions.isEmpty())
            throw new RuntimeException("No actions to perform!");

        if(util.getNumber() < epsilon){
            return util.getRandomAction(legalActions);
        }
        else{
            return getActionFromQValues(gameState, p, legalActions);
        }
    }
    
    public void setEpsion (double ep) {
        epsilon = ep;
    }

    @Override
    public void observe(Game state, Action action, Player p, Game nextState, int reward) {
        // TODO Auto-generated method stub

        PointsState ps = getPointState(state, p, action);

        double qValue = getQValue(ps);
        double nextStateValue = getValueFromQValues(nextState, p);

        qValue = qValue + Configuration.alpha*((reward + nextStateValue)- qValue);

        qValues.put(ps,qValue);
    }

    private double getQValue(PointsState ps){

        if(qValues.containsKey(ps)) {
            return qValues.get(ps);
        }
        
        return 0.0;
    }

    private Double getValueFromQValues(Game state, Player p){
        double maxValue = 0.0;
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
        Action maxAction = Action.Hit;
        double maxValue = -1.0;

        for (Action action: legalActions){
            PointsState ps = getPointState(state, p, action);
            double psQValue = getQValue(ps);
            
            if (psQValue > maxValue){
                maxValue = psQValue;
                maxAction = action;
            }
        }
        
        if (maxValue == 0.0) {
            return util.getRandomAction(legalActions);
        }
        
        return maxAction;
    }
    
    public void printQValues () {
        
        Set<PointsState> keys = qValues.keySet();
        for(PointsState key : keys) {
            
            System.out.println(key.toString() + " " + qValues.get(key));
        }
    }
    
    private PointsState getPointState(Game state, Player p, Action action){

        Hand h = state.getHandFromPlayer(p);
        int points = h.handValueWithoutAces();

        return new PointsState(h.numberOfAces(), points, action);
    }
}

