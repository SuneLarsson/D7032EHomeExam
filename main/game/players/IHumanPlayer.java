package main.game.players;

import main.game.game.gameState.GameState;

/**
 * Interface for human players
 * Holds methods for sending and receiving messages only used by human players
 */
public interface IHumanPlayer extends IPlayer {
    /**
     * Send a message to the player
     * @param message The message to send
     */
    void sendMessage(Object message);
    
    /**
     * Read a message from the player
     * @param gameState The current game state
     * @return String The message read
     */
    String readMessage(GameState gameState);
    
    // IPlayerActions getActions(GameState gameState, IPlayer player);
}