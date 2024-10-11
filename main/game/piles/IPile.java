package main.game.piles;

import main.game.card.PointSaladCard;


public interface IPile {
    PointSaladCard getPileCard();
    PointSaladCard buyPileCard();
    PointSaladCard getMarketCard(int index);
    PointSaladCard buyMarketCard(int index);
    boolean isEmpty();
}
