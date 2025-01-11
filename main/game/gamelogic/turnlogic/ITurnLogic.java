package main.game.gamelogic.turnlogic;

import main.game.gameState.GameState;
import main.players.IPlayer;

/**
 * Interface representing the turn logic for a game.
 * This interface defines methods to control the flow of a player's turn,
 * including taking a turn and printing messages at the start and end of a turn.
 */

public interface ITurnLogic {

    /**
     * Executes the actions for a player's turn.
     *
     * @param gameState The current state of the game.
     * @param thisPlayer The player whose turn it is.
     */
    public void takeTurn(GameState gameState, IPlayer thisPlayer);

    /**
     * Prints the start of a turn.
     */
    public void startTurnPrint();

    /**
     * Prints the end of a turn.
     */
    public void endTurnPrint();
}