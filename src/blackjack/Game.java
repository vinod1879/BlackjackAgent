package blackjack;

import java.util.List;
import java.util.ArrayList;

import model.Card;
import model.Deck;

class Game {
    
    private final List<Hand> hands;
    private int turnIndex;
    private Deck deck;
    
    public Game(List<Player> ps) {
        
        this.hands = new ArrayList<Hand>();
        
        for (Player p : ps) {
            this.hands.add(new Hand(p));
        }
        this.hands.add(new Hand(new Player(0, "Dealer")));
        turnIndex = 0;
        deck = new Deck(true);
        
        initialize();
    }
    
    public Game(Player p) {
        
        this(makeList(p));
    }
    
    private static <E> List<E> makeList(E obj) {
        
        List<E> ps = new ArrayList<E>();
        ps.add(obj);
        
        return ps;
    }
    
    private void initialize () {
        
        for (Hand h : hands) {
            
            for (int i=0; i<2; i++) {

                h.addCard(deck.deal());
            }
        }
    }
    
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
    
    public boolean hasGameEnded () {
        
        for (Hand h : hands) {
            
            if (!h.isBust() && !h.isStay() && !h.isBlackjack()) {
                return false;
            }
        }
        
        return true;
    }
    
    public int getReward (Player p) {
        
        Hand h = getHandFromPlayer(p);
        Hand dealer = hands.get(hands.size() - 1);
                
        return calculateReward(h, dealer);
    }
    
    private int calculateReward (Hand p, Hand dealer) {
        
        if (p.isBlackjack() && !dealer.isBlackjack()) {
            return (int)(Configuration.BetAmount * 1.5);
        }
        else if (p.isBust() || (!dealer.isBust() && p.handValue() < dealer.handValue())) {
            return -Configuration.BetAmount;
        }
        else if (dealer.isBust() || p.handValue() > dealer.handValue()) {
            return Configuration.BetAmount;
        }
        
        return 0;
    }
    
    public List<Action> getNextActions () {
        
        List<Action> actions = new ArrayList<Action>();
        
        for (Action m : Action.values()) {
            actions.add(m);
        }
        
        return actions;
    }
    
    public Player getPlayerToAct () {
        return hands.get(turnIndex).getPlayer();
    }
    
    public void performAction (Player p, Action a) {
        
        Hand h = getHandFromPlayer(p);
        
        if (a == Action.Hit) {
            
            Card c = deck.deal();
            h.addCard(c);
            if (h.isBust()) {
                turnIndex = (turnIndex + 1) % hands.size();
            }
        }
        else if (a == Action.Stay) {
            h.setIsStay(true);
            turnIndex = (turnIndex + 1) % hands.size();
        }
    }
    
    public Hand getHandFromPlayer(Player p) {
        
        for (Hand h : hands) {
            if (p.equals(h.getPlayer())) {
                return h;
            }
        }
        throw new RuntimeException("Unknown player!");
    }
    
    public static void main(String[] args) {
        
        int ITERATIONS = 100;
        QLearningPolicy qPolicy = new QLearningPolicy();
        Player agent = new Player(0, "Agent" , qPolicy );
        int totalBet = 100 * ITERATIONS;
        int totalReward = 0;
        int wins = 0;
        int losses = 0;
        int draws = 0;
        
        for (int i=0; i < ITERATIONS; i++) {
            
            Game g = new Game(agent);
            
            while(!g.hasGameEnded()) {
             
                Player p = g.getPlayerToAct();
                List<Action> actions = g.getNextActions();
                
                Action chosen = p.chooseAction(actions, g);
                g.performAction(p, chosen);

                // need to add the policy observe part
            }
            
            // notify players
            g.printResult();
            int reward = g.getReward(agent);
            
            if (reward > 0) { wins++; }
            else if (reward < 0) { losses++; }
            else { draws++; }
            
            totalReward += g.getReward(agent);
        }
        
        System.out.println("**** Total Amount Bet: $" + totalBet);
        System.out.println("**** Total Amount Won: $" + totalReward);
        System.out.println("**** Total Wins: " + wins);
        System.out.println("**** Total Losses: " + losses);
        System.out.println("**** Total Draws: " + draws);
    }
}