package blackjack;

import java.util.List;
import java.util.ArrayList;

import model.Card;
import model.Deck;

class Game {
    
    private final List<Player> players;
    private Player turnOwner;
    private Deck deck;
    
    public Game(List<Player> ps) {
        
        this.players = ps;
        turnOwner = ps.get(1);
        deck = new Deck(true);
        
        initialize();
    }
    
    private void initialize () {
        
        for (Player p : players) {
            
            for (int i=0; i<2; i++) {
                
                Card c = deck.deal();
                p.handCard(c);
            }
        }        
    }
    
    public List<Action> getNextActions () {
        
        List<Action> actions = new ArrayList<Action>();
        
        for (Move m : Move.values()) {
            
            actions.add(new Action(turnOwner, m));
        }
        
        return actions;
    }
    
    public void performAction (Action a) {
        
        for (Player p : players) {
            
            if (a.getPlayer().equals(p)) {
             
                if (a.getMove() == Move.Hit) {
                    
                    Card c = deck.deal();
                    p.handCard(c);
                }
                else if (a.getMove() == Move.Stay) {
                    p.setIsStay(true);
                }
         
                //updateTurn(p);
                break;
            }
        }
    }
}