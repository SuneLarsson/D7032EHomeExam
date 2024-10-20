package main.game.display;


import main.game.piles.PileManager;

/**
 * IGameField is an interface that represents the game field in the game.
 */
public interface IGameField {

    /**
     * Prints the Game Field
     * @param pileManager is the pile manager for the game
     * @return a string representation of the game field
     */
    String printGameField(PileManager pileManager);
    
}
