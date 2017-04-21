package blackjack;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import model.Card;
import model.Deck;

class Game implements Cloneable {

    private final List<Hand> hands; // the hands involved in the game
    private int turnIndex;          // the index of the hand whose turn it is to move
    private Deck deck;              // the deck of cards used in the game

    /**
     * Constructor for Game
     * @param ps - the list of players in the game (other than the dealer)
     */
    public Game(List<Player> ps) {

        this.hands = new ArrayList<Hand>();

        for (Player p : ps) {
            this.hands.add(new Hand(p));
        }
        this.hands.add(new Hand(new Player(0, "Dealer")));
        turnIndex = 0;
        deck = new Deck(2, true);

        initialize();
    }

    public List<Hand> getHands() {
        return hands;
    }
    
    public List<Card> getVisibleCards () {
        
        return deck.getDealtCards();
    }

    /**
     * Constructor for Game
     * @param p - the player in the game (other than the dealer)
     */
    public Game(Player p) {

        this(makeList(p));
    }

    private static <E> List<E> makeList(E obj) {

        List<E> ps = new ArrayList<E>();
        ps.add(obj);

        return ps;
    }

    private Game(List<Hand> hands, int turnIndex, Deck deck) {
        this.hands = hands;
        this.turnIndex = turnIndex;
        this.deck = deck;
    }
    
    public int getBestHandValue () {
        
        int value = 0;
        for (int i=0; i<(hands.size()-1); i++) {
            
            int currentValue = hands.get(i).handValue();
            
            if (currentValue > value && currentValue <= 21) {
                value = currentValue;
            }
        }
        
        return value;
    }

    /**
     * @param p - a player involved in the game
     * @return the player's hand
     */
    public Hand getHandFromPlayer(Player p) {

        for (Hand h : hands) {
            if (p.equals(h.getPlayer())) {
                return h;
            }
        }
        throw new RuntimeException("Unknown player!");
    }

    /**
     * @return true iff the game has ended
     */
    public boolean hasGameEnded () {

        for (Hand h : hands) {

            if (!h.isBust() && !h.isStay() && !h.isBlackjack() && !h.isTwentyOne()) {
                return false;
            }
        }

        return true;
    }
    
    public boolean isLowOnCards () {
        
        int requiredCards = 10 * hands.size();
        
        return deck.remainingCards() < requiredCards;
    }
    
    public void redeck () {
        deck.reset();
    }

    /**
     * @param p - a player
     * @return - the reward earned by the player
     */
    public int getReward (Player p) {

        if (hasGameEnded()) {

            Hand h = getHandFromPlayer(p);
            Hand dealer = hands.get(hands.size() - 1);

            return calculateReward(h, dealer);
        }

        return 0;
    }

    /**
     * @return the player whose turn it is to act
     */
    public Player getPlayerToAct () {
        return hands.get(turnIndex).getPlayer();
    }

    /**
     * @return list of actions available to the player to act
     */
    public List<Action> getNextActions () {

        List<Action> actions = new ArrayList<Action>();

        for (Action m : Action.values()) {
            actions.add(m);
        }

        return actions;
    }

    /**
     * @param p - the player to act
     * @param a - the action performed by the player to act
     */
    public Game performAction (Player p, Action a) {

        Game clonedGame = this.clone();

        Hand h = clonedGame.getHandFromPlayer(p);

        if (a == Action.Hit) {

            Card c = clonedGame.deck.deal();
            h.addCard(c);
            if (h.isBust() || h.isTwentyOne() || h.isBlackjack()) {
                clonedGame.turnIndex = (clonedGame.turnIndex + 1) % clonedGame.hands.size();
            }
        }
        else if (a == Action.Stay) {
            h.setIsStay(true);
            clonedGame.turnIndex = (clonedGame.turnIndex + 1) % clonedGame.hands.size();
        }

        return clonedGame;
    }
    
    public Game redealToDealer () {
        
        Game clonedGame = this.clone();
        
        Hand dealerHand = clonedGame.hands.get(clonedGame.hands.size() - 1);
        dealerHand.clearCards();
        dealerHand.addCard(clonedGame.deck.deal());
        dealerHand.addCard(clonedGame.deck.deal());

        return clonedGame;
    }

    @Override
    public Game clone() {

        List<Hand> clonedHands = new ArrayList<Hand>();

        for (Hand h : this.hands) {
            clonedHands.add(h.clone());
        }
        Deck clonedDeck =   this.deck.clone();

        return new Game(clonedHands, this.turnIndex, clonedDeck);
    }

    // Initializes the game, by dealing two cards to each player
    // ending with the dealer
    private void initialize () {

        turnIndex = 0;
        for (Hand h : hands) {

            for (int i=0; i<2; i++) {

                h.addCard(deck.deal());
            }
            
            if (h.isTwentyOne() || h.isBlackjack()) {
                turnIndex = (turnIndex + 1) % hands.size();
            }
        }
    }
    
    public void reset () {
        
        for (Hand h : hands) {
            
            h.clearCards();
        }
        
        initialize();
    }

    // Prints the result of the game, with each player's hand
    // and whether each player won/lost/drew
    public void printResult () {

        System.out.println("------------------------------------");

        for (Hand h : hands) {
            Player p = h.getPlayer();

            System.out.print(p + "'s " + h + "\n");
        }

        Hand dealer = hands.get(hands.size() - 1);
        for (Hand h : hands) {

            if (h != dealer) {

                Player p = h.getPlayer();
                int r = getReward(p);
                String result;

                if (r > 0) { result = " wins"; }
                else if (r < 0) { result = " loses"; }
                else { result = " draws"; }

                System.out.println(p.toString() + result);
            }
        }

        System.out.println("------------------------------------");
    }

    // Calculates and returns the reward earned by the player whose hand
    // is h, when the dealer's hand is `dealerHand`
    private int calculateReward (Hand p, Hand dealerHand) {

        if (p.isBlackjack() && !dealerHand.isBlackjack()) {
            return (int)(Configuration.BetAmount * 1.5);
        }
        else if (p.isBust() || (!dealerHand.isBust() && p.handValue() < dealerHand.handValue())) {
            return -Configuration.BetAmount;
        }
        else if (dealerHand.isBust() || p.handValue() > dealerHand.handValue()) {
            return Configuration.BetAmount;
        }

        return 0;
    }

    public static Game playRound (Game g) {
        
        Map<Player, Game> previousGameDict = new HashMap<Player, Game>();
        Map<Player, Game> nextGameDict = new HashMap<Player, Game>();
        Map<Player, Action> actionsDict = new HashMap<Player, Action>();

        while(!g.hasGameEnded()) {

            Player p = g.getPlayerToAct();
            List<Action> actions = g.getNextActions();

            Action chosen = p.chooseAction(actions, g);
            Game nextState = g.performAction(p, chosen);

            previousGameDict.put(p, g);
            actionsDict.put(p, chosen);
            nextGameDict.put(p, nextState);
            
            for(Hand h : g.getHands()) {

                Player ep = h.getPlayer();

                if (previousGameDict.containsKey(ep)) {

                    int reward = nextState.getReward(ep);
                    ep.observe(previousGameDict.get(ep), actionsDict.get(ep), nextGameDict.get(ep), reward);
                }
            }

            g = nextState;
        }
        
        return g;
    }
}