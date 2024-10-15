package main.game.score;

import java.util.ArrayList;

import main.game.card.Card;
import main.game.players.IPlayer;

public interface ICriteria {
    int calculateScore(IPlayer player, ArrayList<IPlayer> players, ArrayList<String> cardTypes, ArrayList<Card> tempHand);
}
