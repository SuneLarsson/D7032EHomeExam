package main.game.piles.Old;

import java.util.ArrayList;

import main.game.card.Card;



public class PileManager {
    // private ArrayList<ArrayList<Card>> piles;
    private ArrayList<ArrayList<Card>> piles;

    public void pileManager() {
        this.piles = new ArrayList<>();
    }

    public void addPile(ArrayList<Card> pile) {
        piles.add((ArrayList<Card>) pile);
    }

    public ArrayList<ArrayList<Card>> getPiles() {
        return piles;
    }
    
    public ArrayList<Card> getPile(int index) {
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

    public Card removeCardFromPile(int pileIndex) {
        if (pileIndex >= 0 && pileIndex < piles.size() && !piles.get(pileIndex).isEmpty()) {
            return piles.get(pileIndex).remove(piles.get(pileIndex).size() - 1);
        }
        return null;
    }

    public boolean isPileEmpty(int index) {
        return piles.get(index).isEmpty();
    }
}
