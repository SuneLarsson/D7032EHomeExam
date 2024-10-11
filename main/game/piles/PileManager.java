package main.game.piles;

import java.util.ArrayList;

import main.game.card.ICard;


public class PileManager {
    // private ArrayList<ArrayList<ICard>> piles;
    private ArrayList<ArrayList<? extends ICard>> piles;

    public void createPiles() {
        piles = new ArrayList<>();
    }
    @SuppressWarnings("unchecked")
    public void addPile(ArrayList<? extends ICard> pile) {
        piles.add((ArrayList<ICard>) pile);
    }

    // public ArrayList<ICard> getPile(int index) {
    //     return piles.get(index);
    // }

    public ArrayList<? extends ICard> getPile(int index) {
        return piles.get(index);
    }

    public int getBiggestPileIndex(int excludeIndex) {
        int biggestPileIndex = -1;
        int biggestSize = 0;
        for (int i = 0; i < piles.size(); i++) {
            if (i != excludeIndex && piles.get(i).size() > biggestSize) {
                biggestSize = piles.get(i).size();
                biggestPileIndex = i;
            }
        }
        return biggestPileIndex;
    }

    public ICard removeCardFromPile(int pileIndex) {
        if (pileIndex >= 0 && pileIndex < piles.size() && !piles.get(pileIndex).isEmpty()) {
            return piles.get(pileIndex).remove(piles.get(pileIndex).size() - 1);
        }
        return null;
    }

    public boolean isPileEmpty(int index) {
        return piles.get(index).isEmpty();
    }
}
