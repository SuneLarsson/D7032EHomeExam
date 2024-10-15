package main.game.piles;

import java.util.ArrayList;

import main.game.card.Card;



public class PileManager {
    // private ArrayList<ArrayList<Card>> piles;
    private ArrayList<Pile> piles;

    public PileManager() {
        this.piles = new ArrayList<>();
    }

    public void addPile(Pile pile) {
        this.piles.add(pile);
    }

    public ArrayList<Pile> getPiles() {
        return piles;
    }
    
    public Pile getPile(int index) {
        return piles.get(index);
    }

    public int getBiggestPileIndex(int excludeIndex) {
        // int biggestPileIndex = -1;
        // int biggestSize = 0;
        // for (int i = 0; i < piles.size(); i++) {
        //     if (i != excludeIndex && piles.get(i).getPileSize() > biggestSize) {
        //         biggestSize = piles.get(i).getPileSize();
        //         biggestPileIndex = i;
        //     }
        // }
        // return biggestPileIndex;

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

    public Card removeCardFromPile(int pileIndex) {
        if (pileIndex >= 0 && pileIndex < piles.size() && !piles.get(pileIndex).isEmpty()) {
            return piles.get(pileIndex).removeCard(piles.get(pileIndex).getPileSize() - 1);
        }
        return null;
    }

    public boolean isPileEmpty(int index) {
        return piles.get(index).isEmpty();
    }
}
