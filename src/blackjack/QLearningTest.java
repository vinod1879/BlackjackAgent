package blackjack;

public class QLearningTest {

    public static void main(String[] args) {
        
        int learning            = 100000;
        double startEpsilon     = 0.9;
        long trials = 0;
        int games = 100;
       
        QLearningPolicy qPolicy = new QLearningPolicy();
        Player agent = new Player(0, "Agent" , qPolicy );
        
        System.out.println("******** Training ********");
        
        double epsilon = startEpsilon;
        while (epsilon > 0.05) {
            
            System.out.println("Starting trials of " + learning + " epislodes:");
            System.out.println("Epsilon: " + epsilon);
            qPolicy.setEpsion(epsilon);
            trainAgent(agent, learning);
            
            epsilon -= 0.1;
            trials += learning;
        }
        
        System.out.println("******** Training Complete ********");
        
        System.out.println("********* Q-policy Derived after " + trials + " trials *********");
        
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
