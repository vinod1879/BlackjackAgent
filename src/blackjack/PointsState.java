package blackjack;

/**
 * Created by Admin on 15-Apr-17.
 */
public class PointsState {

    private int points;

    private Action action;

    public PointsState(int points, Action action) {
        this.points = points;
        this.action = action;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
