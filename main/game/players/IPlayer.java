package main.game.players;

import java.util.ArrayList;

import main.game.card.ICard;

public interface IPlayer {
    int getPlayerID();
    ArrayList<ICard> getHand();
    int getScore();
    void setScore(int score);
}
