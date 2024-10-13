package main.game.piles;


import java.util.ArrayList;

import main.game.card.Card;

public interface IPile {
    int getPileSize();
    void addCard(Card card);
    ArrayList<Card> getCards();
    Card getPlayCard(int index);
    Card removeCard(int index);
    
    Card getPileCard();
    Card buyPileCard();
    Card getMarketCard(int index);
    Card buyMarketCard(int index);
    boolean isEmpty();
    void setPlayCard(int index, Card card);
}
