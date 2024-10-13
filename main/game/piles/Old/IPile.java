package main.game.piles.Old;

import main.game.card.Card;

public interface IPile {
    int getPileSize();
    Card getPileCard();
    Card buyPileCard();
    Card getMarketCard(int index);
    Card buyMarketCard(int index);
    boolean isEmpty();
}
