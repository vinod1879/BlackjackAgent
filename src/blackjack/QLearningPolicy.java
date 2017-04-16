package blackjack;

import java.util.*;

public class QLearningPolicy implements Policy {
    
    //Map<CardCountingState, Integer> qValues;

    private static Map<PointsState,Double> qValues = new TreeMap<PointsState, Double>();
    private static double epsilon  = 0.05;
    BlackjackUtil util = new BlackjackUtil();

    @Override
    public Action chooseAction(Game gameState, Player p, List<Action> actions) {
        // TODO Auto-generated method stub
        List<Action> legalActions = gameState.getNextActions();
        Action action = Action.Hit;
        if (!legalActions.isEmpty()){
            if(util.getNumber()< epsilon){
                action = util.getRandomAction(legalActions);
            }
            else{
            action = getActionFromQValues(gameState);
            }
        }
        return action;
    }

    @Override
    public void observe(Game state, Action action, Game nextState, int reward) {
        // TODO Auto-generated method stub

        PointsState ps = getPointState(state,action);

        double qValue = getQValue(ps);
        double nextStateValue = getValueFromQValues(nextState);

        qValue = qValue + Configuration.alpha*((reward + nextStateValue)- qValue);

        qValues.put(ps,qValue);
    }

    private double getQValue(PointsState ps){

        double initQValue = 0.0;
        if(qValues.containsKey(ps)) {
            return qValues.get(ps);
        }
        qValues.put(ps,initQValue);
        return initQValue;
    }

    private PointsState getPointState(Game state, Action action){

        Player p = state.getPlayerToAct();
        Hand h = state.getHandFromPlayer(p);
        int points = h.handValue();

        return new PointsState(points,action);

    }

    private Double getValueFromQValues(Game state){
        double maxValue = 0.0;
        List<Action> legalActions = state.getNextActions();

        if (!legalActions.isEmpty()){
            for (Action action: legalActions){
             PointsState ps = getPointState(state,action);
             double psQValue = getQValue(ps);
             if (psQValue > maxValue){
                 maxValue = psQValue;
             }
            }
        }
        return maxValue;

    }

    private Action getActionFromQValues(Game state){
        // default action
        Action maxAction = Action.Hit;
        double maxValue = 0.0;
        List<Action> legalActions = state.getNextActions();

        if (!legalActions.isEmpty()){
            for (Action action: legalActions){
                PointsState ps = getPointState(state,action);
                double psQValue = getQValue(ps);
                if (psQValue > maxValue){
                    maxValue = psQValue;
                    maxAction = action;
                }
            }
        }
        return maxAction;

    }
    
    public void printQValues () {
        
        Set<PointsState> keys = qValues.keySet();
        for(PointsState key : keys) {
            
            System.out.println(key.toString() + " " + qValues.get(key));
        }
    }
}

