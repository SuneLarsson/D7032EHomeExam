package main.game.score;

import main.game.gameState.GameState;

/**
 * Factory class that creates the criteria based on the game mode.
 */
public class CriteriaFactory {

    /**
     * Method that creates the criteria based on the game mode.
     * @param gameMode The game mode.
     * @return ICriteria The criteria for the game.
     * @throws IllegalArgumentException If the game mode is not recognized.
     */
    public static ICriteria createCriteria(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new PointSaladCriteria();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
}
