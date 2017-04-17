package blackjack;

import java.util.List;

public class QLearningTest {

    public static void main(String[] args) {
        
        int learning = 1000000;
        int games = 100;
       
        QLearningPolicy qPolicy = new QLearningPolicy();
        Player agent = new Player(0, "Agent" , qPolicy );
        
        trainAgent(agent, learning);
        
        System.out.println("********* Q-policy Derived after " + learning + " trials *********");
        
        qPolicy.printQValues();
        qPolicy.setEpsion(0.0);
        
        System.out.println("********* Playing Real Games with " + games + " hands *********");
        
        playRealgames(agent, 100);
    }
    
    public static void trainAgent (Player agent, int ITERATIONS) {
        
        for (int i=0; i < ITERATIONS; i++) {
            
            Game g = new Game(agent);
            
            g = Game.playRound(g);
        }
    }
    
    public static void playRealgames (Player agent, int ITERATIONS) {
        
        int totalBet = 100 * ITERATIONS;
        int totalReward = 0;
        int wins = 0;
        int losses = 0;
        int draws = 0;
        
        for (int i=0; i < ITERATIONS; i++) {
            
            Game g = new Game(agent);
            
            g = Game.playRound(g);
            
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
