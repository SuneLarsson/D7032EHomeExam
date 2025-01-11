package main.piles;

import java.util.ArrayList;

import main.card.Card;
import main.piles.pile.Pile;

/**
 * Manages all the piles in the game.
 */
public class PileManager {
    private ArrayList<Pile> piles;


    public PileManager() {
        this.piles = new ArrayList<>();
    }

    /**
     * Add a pile to the game.
     * @param pile The pile to add.
     */
    public void addPile(Pile pile) {
        this.piles.add(pile);
    }

    /**
     * Get all the piles in the game.
     * @return ArrayList<Pile> All the piles in the game.
     */
    public ArrayList<Pile> getPiles() {
        return piles;
    }
    
    /**
     * Get the pile at index.
     * @param index The index of the pile.
     * @return Pile The pile at index.
     */
    public Pile getPile(int index) {
        return piles.get(index);
    }

    /**
     * Get the index of the pile with the most cards exluding the pile at excludeIndex.
     * @param excludeIndex The index of the pile to exclude.
     * @return int The index of the pile with the most cards.
     */
    public int getBiggestPileIndex(int excludeIndex) {
        int biggestPileIndex = 0;
        int biggestSize = 0;
        for(int i = 0; i < piles.size(); i++) {
            if(i != excludeIndex && piles.get(i).getPileSize() > biggestSize) {
                biggestSize = piles.get(i).getPileSize();
                biggestPileIndex = i;
            }
        }
        return biggestPileIndex;
    }

    /**
     * Remove a card from the pile at pileIndex.
     * @param pileIndex The index of the pile to remove the card from.
     * @return Card The card that was removed.
     */
    public Card removeCardFromPile(int pileIndex) {
        if (pileIndex >= 0 && pileIndex < piles.size() && !piles.get(pileIndex).isEmpty()) {
            return piles.get(pileIndex).removeCard(piles.get(pileIndex).getPileSize() - 1);
        }
        return null;
    }

    /**
     * Check if the pile at index is empty.
     * @param index The index of the pile to check.
     * @return boolean True if the pile is empty, false otherwise.
     */
    public boolean isPileEmpty(int index) {
        return piles.get(index).isEmpty();
    }
}
