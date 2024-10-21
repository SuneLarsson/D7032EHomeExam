package main.game.score;

import java.util.ArrayList;

import main.card.Card;
import main.players.IPlayer;

/**
 * Interface that represents the criteria that will be used to calculate the score of the players.
 */
public interface ICriteria {
    /**
     * Method that calculates the score of the player and the cards they have.
     * Can be used to calculate a tempHand score that Bot players can use to make decisions.
     * @param player The player to calculate the score.
     * @param players The list of players.
     * @param cardTypes The list of card types.
     * @param tempHand The temporary hand of the player.
     * @return int The score of the player.
     */
    int calculateScore(IPlayer player, ArrayList<IPlayer> players, ArrayList<String> cardTypes, ArrayList<Card> tempHand);
}
