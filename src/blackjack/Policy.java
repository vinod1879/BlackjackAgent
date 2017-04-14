package blackjack;

import java.util.List;

public interface Policy {
    
    public Action chooseAction (Game gameState, Player p, List<Action> actions);
    public void observe(Game state, Action action, Game nextState, int reward);
}
