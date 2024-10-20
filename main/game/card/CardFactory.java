package main.game.card;

import main.game.setupgame.GameState;

/**
 * CardFactory is a factory class that creates a card factory based on the game mode.
 * 
 */

public class CardFactory {
    public static ICardFactory createCardFactory(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new SaladCardFactory();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
}
