package main.game.players;

import java.util.ArrayList;
import main.game.card.Card;
import main.game.setupgame.GameState;

/**
 * Interface for all players
 */
public interface IPlayer {
    /**
     * Get the player's ID
     * @return int The player's ID
     */
    int getPlayerID();
    
    /**
     * Get the player's hand
     * @return ArrayList<Card> The player's hand
     */
    ArrayList<Card> getHand();
    
    /**
     * Get the player's score
     * @return int The player's score
     */
    int getScore();
    
    /**
     * Add a card to the player's hand
     * @param card The card to add
     */
    void addCard(Card card);
    
    /**
     * Set the player's score
     * @param score The score to set
     */
    void setScore(int score);
    
    /**
     * Take a turn in the game
     * @param gameState The current game state
     */
    void takeTurn(GameState gameState);

}
