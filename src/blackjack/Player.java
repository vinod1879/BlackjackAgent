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
        
//        if (this.policy instanceof QLearningPolicy && !this.getName().equals("Dealer")) {
//            printObservation(g, action, this, nextState, reward);
//        }
        this.policy.observe(g, action, this, nextState, reward);
    }
    
    private static void printObservation(Game g, Action action, Player p, Game nextState, int reward) {
        
        String str = "Observing: ";
        
        Hand pHand = g.getHandFromPlayer(p);
        
        str += p.toString() ;
        str += " takes action: " + action;
        str += "\nwith hand " + pHand;
        str += "\nleading to hand " + nextState.getHandFromPlayer(p);
        str += "\nearning reward " + reward;
        
        if (pHand.handValue() == 18 && pHand.numberOfAces() == 0 && action == Action.Hit) {
        
            if (reward > 0) {
                
                System.out.println("!!!!");
            }
            System.out.println(str);
        }
    }
    
    /**
     * String representation of Player
     */
    @Override
    public String toString () {
        return name;
    }   
}
