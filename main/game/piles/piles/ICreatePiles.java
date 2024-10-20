package main.game.piles.piles;

import main.game.card.Card;
import main.game.game.gameState.GameState;

import java.util.Map;
import java.util.ArrayList;

/**
 * Interface for creating piles.
 */

public interface ICreatePiles {

    /**
     * Creates the piles for the game.
     * @param gameState the current game state
     * @param decks the decks of cards
     */
    void createPiles(GameState gameState, Map<String, ArrayList<Card>> decks);



}