package blackjack;

import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 15-Apr-17.
 */
public class BlackjackUtil {

    public static double getNumber() {
        Random generator = new Random();
        double number = generator.nextDouble();
        return number;
    }

    public static Action getRandomAction(List<Action> legalActions){
        Random randomizer = new Random();
        return legalActions.get(randomizer.nextInt(legalActions.size()));
    }
}
