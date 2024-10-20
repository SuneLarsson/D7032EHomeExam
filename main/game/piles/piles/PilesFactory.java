package main.game.piles.piles;

/**
 * Factory class for creating piles
 */
public class PilesFactory {

    /**
     * Decides which pile factory to create based on the game mode
     * @param gameMode the game mode to create piles for
     * @return ICreatPiles Factory to create piles    
     */
    public static ICreatePiles createPilesFactory(String gameMode) {
        if (gameMode.equals("POINTSALAD")) {
            return new CreateSaladPiles();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
    
}
