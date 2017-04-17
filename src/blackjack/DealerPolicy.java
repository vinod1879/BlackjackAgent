package blackjack;

import java.util.List;

public class DealerPolicy implements Policy {

    @Override
    public Action chooseAction(Game gameState, Player p, List<Action> actions) {
        
        int bestValue = gameState.getBestHandValue();
        int myValue = gameState.getHandFromPlayer(p).handValue();
        
        if (myValue > bestValue || myValue > 16) {
            
            return Action.Stay;
        }
        
        return Action.Hit;
    }

    @Override
    public void observe(Game state, Action action, Player p, Game nextState, int reward) {
        // TODO Auto-generated method stub
    }

}
