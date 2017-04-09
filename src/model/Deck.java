package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    
    private List<Card> undealtCards;
    private List<Card> dealtCards;
    
    /**
     * Constructor for a new Deck
     * @param shuffle - whether the Deck should be shuffled
     */
    public Deck (boolean shuffle) {
        
        undealtCards = createDeck();
        dealtCards = new ArrayList<Card>();
        
        if (shuffle) {
            shuffle();
        }
    }
    
    /**
     * Shuffles the deck of undealt cards
     */
    public void shuffle () {
        
        List<Card> cList = new ArrayList<Card>();
        Random rand = new Random();
        while(!undealtCards.isEmpty()) {
            
            int i = rand.nextInt(undealtCards.size());
            
            Card c = undealtCards.remove(i);
            cList.add(c);
        }
        
        undealtCards = cList;
    }
    
    /**
     * Deals one card and moves it to the un-dealt stack, so it isn't
     * dealt again.
     * @return a Card
     */
    public Card deal () {
        
        Card c = undealtCards.remove(0);
        dealtCards.add(c);
        
        return c;
    }
    
    /**
     * Creates a Deck in sorted order
     * @return a Deck in sorted order
     */
    static List<Card> createDeck () {
        
        List<Card> cList = new ArrayList<Card>();
     
        for (int suit=0; suit < 4; suit++) {
            
            for (int rank=0; rank < 13; rank++) {
                
                cList.add(new Card(suit, rank));
            }
        }
        
        return cList;
    }
    
    // For internal testing
    public static void main(String[] args) {
        
        Deck d = new Deck(true);

        for (Card c : d.undealtCards) {
            System.out.print(c + " ");
        }
    }
}
