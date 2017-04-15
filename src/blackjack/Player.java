package blackjack;

import java.util.List;

public class Player {
    
    private final int           id;
    private final String        name;
    private final Policy        policy;
    
    public Player (int id, String name, Policy policy) {
        
        this.id = id;
        this.name = name;
        this.policy = policy;
    }
    
    public Player (int id, String name) {
        
        this.id = id;
        this.name = name;
        this.policy = new DealerPolicy();
    }
    
    /**
     * @return id of player
     */
    public int getId () {
        return id;
    }
    
    /**
     * @return name of player
     */
    public String getName () {
        return name;
    }
    
    public Action chooseAction(List<Action> actions, Game g) {
        
        return this.policy.chooseAction(g, this, actions);
    }
    
    public void observe(Game g, Action action, Game nextState, int reward) {
        this.policy.observe(g, action, nextState, reward);
    }
    
    /**
     * String representation of Player
     */
    @Override
    public String toString () {
        return name;
    }
}
