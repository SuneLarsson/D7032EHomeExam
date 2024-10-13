package main.game.piles;

import main.game.card.Card;

public class SaladPile extends Pile {
    private PileManager pileManager;
    private int pileIndex;

    public SaladPile(PileManager pileManager, int pileIndex) {
        super();
        this.pileManager = pileManager;
        this.pileIndex = pileIndex;
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
    public Card getMarketCard (int index){
        return getPlayCard(index);
    }

    @Override
    public Card buyMarketCard(int index){
        Card aCard = getPlayCard(index);
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
                    setPlayCard(index, removeCard(0));
                    if (getPlayCard(index).isPointSideUp()) {
                        getPlayCard(index).flip();
                    }
            }
            } else {
                setPlayCard(index, null); 
            }
        } else {
            setPlayCard(index, removeCard(0));
            if (getPlayCard(index).isPointSideUp()) {
                getPlayCard(index).flip();
            }
        }
        return aCard;
    }

    // @Override
    // public boolean isEmpty() {
    //     return cards.isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
    // }

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
