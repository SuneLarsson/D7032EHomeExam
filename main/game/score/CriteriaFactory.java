package main.game.score;

/**
 * Factory class that creates the criteria based on the game mode.
 */
public class CriteriaFactory {

    /**
     * Method that creates the criteria based on the game mode.
     * @param gameMode The game mode.
     * @return ICriteria The criteria based on the game mode.
     */
    public static ICriteria createCriteria(String gameMode) {
        if (gameMode.equals("POINTSALAD")) {
            return new PointSaladCriteria();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
}
