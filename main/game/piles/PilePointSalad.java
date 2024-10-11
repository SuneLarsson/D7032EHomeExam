package main.game.piles;


import java.util.ArrayList;

import main.game.card.ICard;
import main.game.card.PointSaladCard;


public class PilePointSalad implements IPile{
    private ArrayList<PointSaladCard> cards = new ArrayList<PointSaladCard>();
    private PointSaladCard[] veggieCards = new PointSaladCard[2];
    private PileManager pileManager;
    private int pileIndex;


    @SuppressWarnings("unchecked")
    public PilePointSalad(PileManager pileManager, int pileIndex) {
        this.pileManager = pileManager;
        this.pileIndex = pileIndex;
        this.cards = (ArrayList<PointSaladCard>) pileManager.getPile(pileIndex);
        // this.cards = pileManager.getPile(pileIndex);
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);
        // UPPREPNIGN kanske hjälp funktion
        if (this.veggieCards[0].isCriteriaSideUp()) {
            this.veggieCards[0].flip();
        }
        if (this.veggieCards[1].isCriteriaSideUp()) {
            this.veggieCards[0].flip();
        }
    }


    @Override
    public PointSaladCard getPileCard(){
        if(cards.isEmpty()) {
           if(otherPileSize()) {
               return cards.get(0);
           } else {
               return null;
           }
        }
        return cards.get(0);
    }

    @Override
    public PointSaladCard buyPileCard(){
        if(cards.isEmpty()) {
            if(otherPileSize()) {
                return cards.get(0);
            } else {
                return null;
            }
        }
        return cards.remove(0);
    }

    @Override
    public PointSaladCard getMarketCard (int index){
        return veggieCards[index];
    }

    @Override
    public PointSaladCard buyMarketCard(int index){
        PointSaladCard aCard = veggieCards[index];
        if (cards.size() <= 1) {
            int biggestPileIndex = pileManager.getBiggestPileIndex(pileIndex);
            if (biggestPileIndex != -1) {
                // cards.add(pileManager.removeCardFromPile(biggestPileIndex));
                // veggieCards[index] = cards.remove(0);
                // if (veggieCards[index].isCriteriaSideUp()) {
                //     veggieCards[index].flip();
                // }
                ICard card = pileManager.removeCardFromPile(biggestPileIndex);
                if (card instanceof PointSaladCard) {  // Check if card is PointSaladCard
                    cards.add((PointSaladCard) card);  // Safe cast
                    veggieCards[index] = cards.remove(0);
                    if (veggieCards[index].isCriteriaSideUp()) {
                        veggieCards[index].flip();
                    }
            }
            } else {
                veggieCards[index] = null;
            }
        } else {
            veggieCards[index] = cards.remove(0);
            if (veggieCards[index].isCriteriaSideUp()) {
                veggieCards[index].flip();
            }
        }
        return aCard;
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
    }

    private boolean otherPileSize() {
        int biggestPileIndex = pileManager.getBiggestPileIndex(pileIndex);
        if (biggestPileIndex != -1) {
            ICard card = pileManager.removeCardFromPile(biggestPileIndex);
            if (card instanceof PointSaladCard) {  // Check if card is PointSaladCard
                cards.add((PointSaladCard) card);
            return true;
        }
        }
        return false;
    }
}