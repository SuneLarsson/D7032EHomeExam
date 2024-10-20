package main.game.game.gamelogic;

import main.game.game.gameState.GameState;

/**
 * Factory class that creates the game logic based on the game mode.
 */
public class GameLogicFactory {
    /**
     * Creates the game logic based on the game mode.
     * @param gameState the current game state
     * @return the game logic
     */
    public static IGameLogic createGameLogic(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new SaladGameLogic(gameState);
        }
        return null;
    }
    
}
