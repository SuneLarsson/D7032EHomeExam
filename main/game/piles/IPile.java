package main.game.piles;

import main.game.card.Card;

public interface IPile {
    Card getPileCard();
    Card buyPileCard();
    Card getMarketCard(int index);
    Card buyMarketCard(int index);
    boolean isEmpty();
}
