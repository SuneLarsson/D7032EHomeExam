package main.game.score;

public class CriteriaFactory {
    public static ICriteria createCriteria(String gameMode) {
        if (gameMode.equals("POINTSALAD")) {
            return new PointSaladCriteria();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
}
