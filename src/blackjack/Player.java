package blackjack;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.Rank;

public abstract class Player {
    
    private final int id;
    private final String name;
    private final List<Card> hand;
    private boolean stay;
    
    Player (int id, String name) {
        
        this.id = id;
        this.name = name;
        hand = new ArrayList<Card>();
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
    
    public boolean isStay () {
        return stay;
    }
    
    public void setIsStay (boolean s) {
        stay = s;
    }
    
    /**
     * @return true iff player's hand has gone bust
     */
    public boolean isBust () {
        return handValue() > 21;
    }
    
    /**
     * @return true iff player's hand is a blackjack
     */
    public boolean isBlackjack () {
        return hand.size() == 2 && handValue() == 21;
    }
    
    /**
     * @return true iff the player's hand value is 21
     */
    public boolean isTwentyOne () {
        return handValue() == 21;
    }
    
    /**
     * 
     * @param c
     */
    public void handCard(Card c) {
        hand.add(c);
    }
    
    /**
     * @return hand value of player
     */
    public int handValue () {
        
        int val = 0;
        boolean aceFound = false;
        
        for (Card c : hand) {
            
            if (c.getRank() == Rank.Ace) {
                aceFound = true;
            }
            
            val += c.baseValue();
        }
        
        if (aceFound && val < 21) {
            val += 10;
        }
        
        return val;
    }
    
    /**
     * String representation of Player
     */
    @Override
    public String toString () {
        return name;
    }
}
