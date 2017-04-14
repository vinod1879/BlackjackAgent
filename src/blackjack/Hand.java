package blackjack;

import java.util.List;
import java.util.ArrayList;

import model.Card;
import model.Rank;

public class Hand {

    private final Player player;
    private final List<Card> cards;
    private boolean stay;
    
    Hand(Player p) {
        
        player = p;
        cards = new ArrayList<Card>();
    }
    
    public void addCard(Card c) {
        
        cards.add(c);
    }
    
    public Player getPlayer () {
        return player;
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
        return cards.size() == 2 && handValue() == 21;
    }
    
    /**
     * @return true iff the player's hand value is 21
     */
    public boolean isTwentyOne () {
        return handValue() == 21;
    }
    
    /**
     * @return hand value of player
     */
    public int handValue () {
        
        int val = 0;
        boolean aceFound = false;
        
        for (Card c : cards) {
            
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
    
    @Override
    public String toString () {
        String str = "Hand: ";
        
        for (Card c : cards) {
            str += c.toString() + "\n";
        }
        
        str += "value: " + handValue();
        
        return str;
    }
}
