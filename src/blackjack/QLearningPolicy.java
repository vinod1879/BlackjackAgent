package blackjack;

import java.util.List;

public class QLearningPolicy implements Policy {
    
    //Map<CardCountingState, Integer> qValues;

    @Override
    public Action chooseAction(Game gameState, Player p, List<Action> actions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void observe(Game state, Action action, Game nextState, int reward) {
        // TODO Auto-generated method stub
        Player p = state.getPlayerToAct();
        Hand h = state.getHandFromPlayer(p);
        int value = h.handValue();
    }
}

