package blackjack;

/**
 * Created by Admin on 15-Apr-17.
 */
public class PointsState implements Comparable<PointsState> {

    private int numberOfAces;
    private int points;
    private Action action;
    private int cardCount;
    
    public PointsState(int aces, int points, Action action) {
        this.numberOfAces = aces;
        this.points = points;
        this.action = action;
        this.cardCount = 0;
    }
    
    public PointsState(int aces, int points, Action action, int cardCount) {
        
        this.numberOfAces = aces;
        this.points = points;
        this.action = action;
        this.cardCount = cardCount;
    }
    
    public int getNumberOfAces() {
        return numberOfAces;
    }

    public int getPoints() {
        return points;
    }

    public Action getAction() {
        return action;
    }
    
    @Override
    public String toString () {
        String str = "" + numberOfAces + "-" + points + "-" + action.toString()
                   + cardCount;
        
        return str;
    }
    
    @Override
    public boolean equals (Object obj) {
        
        if (obj instanceof PointsState) {
            
            PointsState other = (PointsState)obj;
            
            return other.numberOfAces == this.numberOfAces &&
                   other.points == this.points && 
                   other.action == this.action &&
                   other.cardCount == this.cardCount;
        }
        
        return false;
    }
    
    @Override
    public int hashCode () {
        int n = action == Action.Hit ? 10 : 20;
        
        return numberOfAces * 1000 + points * 100 + n + cardCount * 10000;
    }

    @Override
    public int compareTo(PointsState o) {
        
        PointsState other = (PointsState)o;
        
        if (this.numberOfAces < other.numberOfAces)
            return -1;
        else if (this.numberOfAces > other.numberOfAces)
            return 1;
        else {
            if (this.points < other.points)
                return -1;
            else if (this.points > other.points)
                return 1;
            else {
                
                if (this.cardCount < other.cardCount)
                    return -1;
                else if (this.cardCount > other.cardCount)
                    return 1;
                else {
                    if (this.action == Action.Hit && other.action == Action.Stay) 
                        return -1;
                    else if (this.action == Action.Stay && other.action == Action.Hit)
                        return 1;
                }
            }
        }
        
        return 0;
    }
}
