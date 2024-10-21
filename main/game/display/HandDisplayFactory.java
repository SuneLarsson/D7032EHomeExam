package main.game.display;

import main.game.game.gameState.GameState;

public class HandDisplayFactory {
        public static IHandDisplay createCardFactory(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new SaladHandDisplay();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }

}
