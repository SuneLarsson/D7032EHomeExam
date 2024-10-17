package main.game.piles.pile;

import java.util.ArrayList;

import main.game.card.Card;

public abstract class Pile implements IPile {

    private ArrayList<Card> cards;

    public Pile () {
        this.cards = new ArrayList<Card>();

    }

    @Override
    public int getPileSize() {
        return cards.size();
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    @Override
    public Card removeCard(int index) {
        return cards.remove(index);
    }

}
