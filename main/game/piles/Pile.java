package main.game.piles;

import java.util.ArrayList;

import main.game.card.Card;

public abstract class Pile implements IPile {
    // private ArrayList<Card> cards = new ArrayList<Card>();
    // private Card[] playCards = new Card[2];
    private ArrayList<Card> cards;
    private Card[] playCards;
    // private PileManager2 pileManager;
    // private int pileIndex;

    public Pile () {
        this.cards = new ArrayList<Card>();
        this.playCards = new Card[2];
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
    public Card getPlayCard(int index) {
        return playCards[index];
    }

    @Override
    public Card removeCard(int index) {
        return cards.remove(index);
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty() && playCards[0] == null && playCards[1] == null;
    }

    @Override
    public void setPlayCard(int index, Card card) {
        playCards[index] = card;
    }
}
