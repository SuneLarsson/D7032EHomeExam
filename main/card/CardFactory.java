package main.card;

import main.game.gameState.GameState;

/**
 * CardFactory is a factory class that creates a card factory based on the game mode.
 * Gets game mode from GameState object.
 */

public class CardFactory {

    /**
     * Creates a card factory based on the game mode.
     *
     * @param gameState The current state of the game.
     * @return ICardFactory The card factory for the game mode.
     */
    public static ICardFactory createCardFactory(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new SaladCardFactory();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
}
