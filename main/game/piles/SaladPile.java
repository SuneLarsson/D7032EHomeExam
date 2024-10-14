package main.game.piles;

import main.game.card.Card;

public class SaladPile extends Pile {
    private PileManager pileManager;
    private int pileIndex;
    private Card[] veggieCards = new Card[2];

    public SaladPile(PileManager pileManager, int pileIndex) {
        super();
        this.pileManager = pileManager;
        this.pileIndex = pileIndex;
        // veggieCards[0] = getCards().remove(0);
        // veggieCards[1] = getCards().remove(0);
        // veggieCards[0].flip();
        // veggieCards[1].flip();

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
        if(isEmpty()) {
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
        if(getCards().isEmpty()) {
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
        if (getPileSize() <= 1) {
            int biggestPileIndex = pileManager.getBiggestPileIndex(pileIndex);
            if (biggestPileIndex != -1) {
                // cards.add(pileManager.removeCardFromPile(biggestPileIndex));
                // veggieCards[index] = cards.remove(0);
                // if (veggieCards[index].isCriteriaSideUp()) {
                //     veggieCards[index].flip();
                // }
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
        return getCards().isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
    }

    private boolean otherPileSize() {
        int biggestPileIndex = pileManager.getBiggestPileIndex(pileIndex);
        if (biggestPileIndex != -1) {
            Card card = pileManager.removeCardFromPile(biggestPileIndex);
            if (card instanceof Card) {  // Check if card is Card
                addCard(card);
            return true;
        }
        }
        return false;
    }
}
