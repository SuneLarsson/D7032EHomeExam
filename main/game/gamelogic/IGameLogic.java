package main.game.gamelogic;

import main.game.gameState.GameState;

/**
 * Interface for the GameLogic class
 * Contains the game loop and the end game method
 */
public interface IGameLogic {

    /**
     * Method that contains the game loop, tells the players to take their turns
     * @param gameState Contains the current state of the game
     * @param turnlimit The number of turns the game will last at most
     */
    void gameLoop(GameState gameState, Integer turnlimit);
    
    /**
     * Method that ends the game and displays the winner
     * @param gameState Contains the current state of the game
     */
    void endGame(GameState gameState);

}