package model;

public class Card {
    
    private final Suit suit;
    private final Rank rank;
    
    /**
     * Enum-based constructor
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    Card (Suit suit, Rank rank) {
        
        this.suit = suit;
        this.rank = rank;
    }
    
    /**
     * Integer-based constructor
     * @param suit the suit index of the card
     * @param rank the rank index of the card
     */
    Card (int suit, int rank) {
        
        this.suit = suitFromInt(suit);
        this.rank = rankFromInt(rank);
    }
    
    /**
     * The String-representation of the Card
     */
    public String toString () {
        return suitAsString() + rankAsString();
    }
    
    // private
    private static Suit suitFromInt (int i) {
        
        switch (i) {
        case 0: return Suit.Clubs;
        case 1: return Suit.Diamonds;
        case 2: return Suit.Hearts;
        case 3: return Suit.Spades;
        default: throw new RuntimeException("Invalid Suit Index");
        }
    }
    
    // private
    private static Rank rankFromInt (int i) {
        
        switch (i) {
        case 0: return Rank.Deuce;
        case 1: return Rank.Three;
        case 2: return Rank.Four;
        case 3: return Rank.Five;
        case 4: return Rank.Six;
        case 5: return Rank.Seven;
        case 6: return Rank.Eight;
        case 7: return Rank.Nine;
        case 8: return Rank.Ten;
        case 9: return Rank.Jack;
        case 10: return Rank.Queen;
        case 11: return Rank.King;
        case 12: return Rank.Ace;
        default: throw new RuntimeException("Invalid Rank Index");
        }
    }
    
    // private
    private String suitAsString () {
        
        switch (suit) {
        case Clubs: return "♣";
        case Diamonds: return "♦";
        case Hearts: return "♥";
        case Spades: return "♠";
        default: throw new RuntimeException("Invalid Suit");
        }
    }
    
    // private
    private String rankAsString () {
        
        switch (rank) {
        case Deuce: return "2";
        case Three: return "3";
        case Four: return "4";
        case Five: return "5";
        case Six: return "6";
        case Seven: return "7";
        case Eight: return "8";
        case Nine: return "9";
        case Ten: return "10";
        case Jack: return "J";
        case Queen: return "Q";
        case King: return "K";
        case Ace: return "A";
        default: throw new RuntimeException("Invalid Rank");
        }
    }
}

/**
 * 
 * Enum to represent a Suit of a card
 *
 */
enum Suit {
    Clubs,
    Diamonds,
    Hearts,
    Spades
}

/**
 * 
 * Enum to represent a Rank of a card
 *
 */
enum Rank {
    Deuce,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Jack,
    Queen,
    King,
    Ace
}