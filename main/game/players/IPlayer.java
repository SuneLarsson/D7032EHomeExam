package main.game.players;

import java.util.ArrayList;

import main.game.card.Card;


public interface IPlayer {
    int getPlayerID();
    ArrayList<Card> getHand();
    int getScore();
    void setScore(int score);
}
