package main.game.piles.Old;


import java.util.ArrayList;

import main.game.card.Card;



public class PilePointSalad implements IPile{
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Card[] veggieCards = new Card[2];
    private PileManager pileManager;
    private int pileIndex;

    @Override
    public int getPileSize() {
        return cards.size();
    }

    public PilePointSalad(PileManager pileManager, int pileIndex) {
        this.pileManager = pileManager;
        this.pileIndex = pileIndex;
        this.cards = (ArrayList<Card>) pileManager.getPile(pileIndex);
        // this.cards = pileManager.getPile(pileIndex);
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);
        // UPPREPNIGN kanske hjälp funktion
        if (this.veggieCards[0].isPointSideUp()) {
            this.veggieCards[0].flip();
        }
        if (this.veggieCards[1].isPointSideUp()) {
            this.veggieCards[0].flip();
        }
    }


    @Override
    public Card getPileCard(){
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
    public Card buyPileCard(){
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
    public Card getMarketCard (int index){
        return veggieCards[index];
    }

    @Override
    public Card buyMarketCard(int index){
        Card aCard = veggieCards[index];
        if (cards.size() <= 1) {
            int biggestPileIndex = pileManager.getBiggestPileIndex(pileIndex);
            if (biggestPileIndex != -1) {
                // cards.add(pileManager.removeCardFromPile(biggestPileIndex));
                // veggieCards[index] = cards.remove(0);
                // if (veggieCards[index].isCriteriaSideUp()) {
                //     veggieCards[index].flip();
                // }
                Card card = pileManager.removeCardFromPile(biggestPileIndex);
                if (card instanceof Card) {  // Check if card is Card
                    cards.add((Card) card);  // Safe cast
                    veggieCards[index] = cards.remove(0);
                    if (veggieCards[index].isPointSideUp()) {
                        veggieCards[index].flip();
                    }
            }
            } else {
                veggieCards[index] = null;
            }
        } else {
            veggieCards[index] = cards.remove(0);
            if (veggieCards[index].isPointSideUp()) {
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
            Card card = pileManager.removeCardFromPile(biggestPileIndex);
            if (card instanceof Card) {  // Check if card is Card
                cards.add((Card) card);
            return true;
        }
        }
        return false;
    }
}