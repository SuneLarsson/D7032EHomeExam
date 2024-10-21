package main.game.display;

import main.game.game.gameState.GameState;

/**
 * Factory class that creates the game field based on the game mode.
 */
public class GameFieldFactory {

    /**
     * Returns the game field based on the game mode.
     * @param gameState The current game state.
     * @return The game field.
     */
    public static IGameField getGameField(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new SaladGameField();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
}
