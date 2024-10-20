package main.game.game.gamelogic.turnlogic;

/**
 * TurnFactory is a factory class that creates a TurnLogic based on the game mode.
 * Gets game mode from GameState object.
 */

public class TurnFactory {

    /**
     * Creates a TurnLogic based on the game mode.
     *
     * @param gameMode The current game mode.
     * @return ITurnLogic The turn logic for the game mode.
     */
    public static ITurnLogic createTurnLogic(String gameMode) {
        if (gameMode.equals("POINTSALAD")) {
            return new SaladTurnLogic();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
    
}
