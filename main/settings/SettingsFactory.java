package main.settings;

import main.game.gameState.GameState;

/**
 * Factory class that selects the settings based on the game mode.
 */
public class SettingsFactory {

    /**
     * Selects the settings based on the game mode.
     * @param gameState the current game state
     * @return the settings for the game
     * @throws IllegalArgumentException if the game mode is not recognized
     */
    public static ISettings selectSettings(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new SaladSettings();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
}
