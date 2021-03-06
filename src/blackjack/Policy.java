package blackjack;

import java.util.List;

public interface Policy {
    
    public Action chooseAction (Game gameState, Player p, List<Action> actions);
    public void observe(Game state, Action action, Player p, Game nextState, int reward);
}
