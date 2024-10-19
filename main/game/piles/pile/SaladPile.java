package main.game.piles.pile;

import main.game.card.Card;
import main.game.piles.PileManager;

public class SaladPile extends Pile {
    private PileManager pileManager;
    private int pileIndex;
    private Card[] veggieCards = new Card[2];

    public SaladPile(PileManager pileManager, int pileIndex) {
        super();
        this.pileManager = pileManager;
        this.pileIndex = pileIndex;

    }
    @Override
    public void setupMarket() {
        this.veggieCards[0] = getCards().remove(0);
        this.veggieCards[1] = getCards().remove(0);
        this.veggieCards[0].flip();
        this.veggieCards[1].flip();
    }

    @Override
    public Card getPileCard(){
        if(isPileEmpty()) {
            if(otherPileSize()) {
                return getCards().get(0);
            } else {
                return null;
            }
         }
         return getCards().get(0);
    }

    @Override
    public Card buyPileCard(){
        if(isEmpty()) {
            if(otherPileSize()) {
                return getCards().get(0);
            } else {
                return null;
            }
        }
        return getCards().remove(0);
    }

   @Override
    public Card getMarketCard(int index){
        return veggieCards[index];
    }
    
    @Override
    public Card buyMarketCard(int index){
        Card aCard = veggieCards[index];
        if (getPileSize() <= 0) {
            int biggestPileIndex = pileManager.getBiggestPileIndex(pileIndex);
            if (pileManager.getPile(biggestPileIndex).getPileSize() > 0) {
                Card card = pileManager.removeCardFromPile(biggestPileIndex);
                if (card instanceof Card) {  // Check if card is Card
                    addCard(card);
                    veggieCards[index] = getCards().remove(0);
                    if (veggieCards[index].isPointSideUp()) {
                        veggieCards[index].flip();
                    }
                }
            } else {
                veggieCards[index] = null; 
            }
        } else {
            veggieCards[index] = getCards().remove(0);
            if (veggieCards[index].isPointSideUp()) {
                veggieCards[index].flip();
            }
        }
        return aCard;
    }

    @Override
    public boolean isEmpty() {
        // System.out.println("isEmpty: " + getCards().isEmpty() + " && " + veggieCards[0] + " == null && " + veggieCards[1] + " == null");
        return getCards().isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
    }

    public boolean isPileEmpty() {
        return getCards().isEmpty();
    }

    private boolean otherPileSize() {
        int biggestPileIndex = pileManager.getBiggestPileIndex(pileIndex);
        if (pileManager.getPile(biggestPileIndex).getPileSize() > 1) {
            Card card = pileManager.removeCardFromPile(biggestPileIndex);
            if (card instanceof Card) {  // Check if card is Card
                addCard(card);
            return true;
        }
        }
        return false;
    }
}
