package main.game.display;

import java.util.ArrayList;

import main.game.card.Card;
import main.game.game.gameState.GameState;

/**
 * IHandDisplay interface is used to display the hand of the player
 */
public interface IHandDisplay {

    /**
     * displayHand method is used to display the hand of the player
     * @param hand is the hand of the player
     * @param gameState is the current state of the game
     * @return the hand of the player as a string
     */
    String displayHand(ArrayList<Card> hand, GameState gameState);
    
} 
