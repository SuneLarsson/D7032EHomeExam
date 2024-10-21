package main.game.piles.piles;

import main.game.game.gameState.GameState;

/**
 * Factory class for creating piles
 */
public class PilesFactory {

    /**
     * Decides which pile factory to create based on the game mode
     * @param gameMode the game mode to create piles for
     * @return ICreatPiles Factory to create piles    
     */
    public static ICreatePiles createPilesFactory(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new CreateSaladPiles();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
    
}
