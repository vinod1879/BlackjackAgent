package blackjack;

public class Action {

    private final Player player;
    private final Move move;
    
    Action (Player p, Move m) {
        this.player = p;
        this.move = m;
    }
    
    public Player getPlayer () {
        return this.player;
    }
    
    public Move getMove () {
        return this.move;
    }
}
