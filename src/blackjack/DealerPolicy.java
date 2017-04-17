package blackjack;

import java.util.List;

public class DealerPolicy implements Policy {

    @Override
    public Action chooseAction(Game gameState, Player p, List<Action> actions) {
        
        for (Action action: actions) {
            
            if (action == Action.Hit && 
                gameState.getHandFromPlayer(p).handValue() < 17) {
                
                return action;
            }
        }
        
        return Action.Stay;
    }

    @Override
    public void observe(Game state, Action action, Player p, Game nextState, int reward) {
        // TODO Auto-generated method stub
    }

}
