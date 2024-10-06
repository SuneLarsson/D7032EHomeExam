public interface iPile {
    public Card getPointCard();
    public Card buyPointCard();
    getplaycard();
    public boolean isEmpty();
}

public class PilePointSalad implements iPile{
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Card[] veggieCards = new Card[2];

    public PilePointSalad(ArrayList<Card> cards) {
        this.cards = cards;
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);
        this.veggieCards[0].criteriaSideUp = false;;
        this.veggieCards[1].criteriaSideUp = false;
    }
    @Override
    public Card getPointCard() {}

}

public class Pile {
    public ArrayList<Card> cards = new ArrayList<Card>();
    public Card[] veggieCards = new Card[2];

    public Pile(ArrayList<Card> cards) {
        this.cards = cards;
        this.veggieCards[0] = cards.remove(0);
        this.veggieCards[1] = cards.remove(0);
        this.veggieCards[0].criteriaSideUp = false;;
        this.veggieCards[1].criteriaSideUp = false;
    }
    public Card getPointCard() {
        if(cards.isEmpty()) {
            //remove from the bottom of the biggest of the other piles
            int biggestPileIndex = 0;
            int biggestSize = 0;
            for(int i = 0; i < piles.size(); i++) {
                if(i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                    biggestSize = piles.get(i).cards.size();
                    biggestPileIndex = i;
                }
            }
            if(biggestSize > 1) {
                cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size()-1));
            } else // we can't remove active point cards from other piles
                return null;
        }
        return cards.get(0);
    }
    public Card buyPointCard() {
        if(cards.isEmpty()) {
            //remove from the bottom of the biggest of the other piles
            int biggestPileIndex = 0;
            int biggestSize = 0;
            for(int i = 0; i < piles.size(); i++) {
                if(i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                    biggestSize = piles.get(i).cards.size();
                    biggestPileIndex = i;
                }
            }
            if(biggestSize > 1) {
                cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size()-1));
            } else { // we can't remove active point cards from other piles
                return null;
            }
        }
        return cards.remove(0);
    }
    public Card getVeggieCard(int index) {
        return veggieCards[index];
    }
    public Card buyVeggieCard(int index) {
        Card aCard = veggieCards[index];
        if(cards.size() <=1) {
            //remove from the bottom of the biggest of the other piles
            int biggestPileIndex = 0;
            int biggestSize = 0;
            for(int i = 0; i < piles.size(); i++) {
                if(i != piles.indexOf(this) && piles.get(i).cards.size() > biggestSize) {
                    biggestSize = piles.get(i).cards.size();
                    biggestPileIndex = i;
                }
            }
            if(biggestSize > 1) {
                cards.add(piles.get(biggestPileIndex).cards.remove(piles.get(biggestPileIndex).cards.size()-1));
                veggieCards[index] = cards.remove(0);
                veggieCards[index].criteriaSideUp = false;
            } else {
                veggieCards[index] = null;
            }
        } else {
            veggieCards[index] = cards.remove(0);
            veggieCards[index].criteriaSideUp = false;
        }

        return aCard;
    }
    public boolean isEmpty() {
        return cards.isEmpty() && veggieCards[0] == null && veggieCards[1] == null;
    }
}