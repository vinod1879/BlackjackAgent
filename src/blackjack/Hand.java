package blackjack;

import java.util.List;
import java.util.ArrayList;

import model.Card;
import model.Rank;

public class Hand implements Cloneable {

    private final Player player;
    private final List<Card> cards;
    private boolean stay;
    
    Hand(Player p) {
        
        player = p;
        cards = new ArrayList<Card>();
    }
    
    private Hand (Player p, List<Card> cs, boolean stay) {
        
        this.player = p;
        this.cards = cs;
        this.stay = stay;
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
    public Hand clone () {
        
        List<Card> clonedCards = new ArrayList<Card>();
        
        for (Card c : this.cards) {
            clonedCards.add(c.clone());
        }
        
        return new Hand(this.player, clonedCards, this.stay);
    }
    
    @Override
    public String toString () {
        String str = "Hand: ";
        
        for (Card c : cards) {
            str += c.toString() + " ";
        }
        
        str += "value: " + handValue();
        
        return str;
    }
}
