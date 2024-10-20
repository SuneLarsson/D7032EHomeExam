package main.game.piles.pile;

import java.util.ArrayList;
import main.game.card.Card;

/**
 * Interface for the Pile class.
 */
public interface IPile {

    /**
     * Returns the size of the pile.
     * @return Int The size of the pile.
     */
    int getPileSize();

    /**
     * Adds a card to the pile.
     * @param card The card to add to the pile.
     */
    void addCard(Card card);

    /**
     * Returns the cards in the pile.
     * @return ArrayList<Card> The cards in the pile.
     */
    ArrayList<Card> getCards();

    /**
     * Removes a card from the pile.
     * @param index The index of the card to remove.
     * @return Card The card that was removed.
     */
    Card removeCard(int index);

    /**
     * Returns the card at the top of the pile.
     * @return Card The card at the top of the pile.
     */
    Card getPileCard();
    
    /**
     * Removes the card at the top of the pile.
     * @return Card The card removed from the top of the pile.
     */
    Card buyPileCard();
    
    /**
     * Returns the card at index in the market.
     * @param index The index of the card in the market.
     * @return Card The card at index in the market.
     */
    Card getMarketCard(int index);

    /**
     * Removes the card at index from the market.
     * @param index The index of the card to remove.
     * @return Card The card removed from the market.
     */
    Card buyMarketCard(int index);
    
    /**
     * Checks if the pile is empty.
     * @return boolean True if the pile is empty, false otherwise.
     */
    boolean isEmpty();
    
    /**
     * Sets up the market.
     */
    void setupMarket();
}
