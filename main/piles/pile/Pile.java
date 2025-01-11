package main.piles.pile;

import java.util.ArrayList;

import main.card.Card;

/**
 * A pile of cards.
 */

public abstract class Pile implements IPile {

    private ArrayList<Card> cards;

    /**
     * Creates a new pile of cards.
     */
    public Pile () {
        this.cards = new ArrayList<Card>();

    }

    /**
     * Get the size of the pile.
     * @return Int The size of the pile.
     */
    @Override
    public int getPileSize() {
        return cards.size();
    }

    /**
     * Add a card to the pile.
     * @param card The card to add to the pile.
     */
    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Get all cards in the pile.
     * @return ArrayList<Card> All cards in the pile.
     */
    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    /**
     * Remove a card from the pile at index.
     * @param index The index of the card to remove.
     * @return Card The card that was removed.
     */
    @Override
    public Card removeCard(int index) {
        return cards.remove(index);
    }

}
