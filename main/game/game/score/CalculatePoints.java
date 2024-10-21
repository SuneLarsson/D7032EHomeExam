package main.game.game.score;

import java.util.ArrayList;

import main.game.game.gameState.GameState;
import main.game.players.IPlayer;

/**
 * Class that calculates the points of the players based on the game mode and the cards they have.
 */
public class CalculatePoints {

    /**
     * Method that calculates the points of the players based on the game mode and the cards they have
     * and sets the score of each player.
     * @param gameState The current state of the game.
     */
    public void scorePoints(GameState gameState) {
        ArrayList<String> cardTypes = gameState.getSettings().getCardTypes();
        ArrayList<IPlayer> players = gameState.getPlayers();
        ICriteria ScoreCriteria;
        for (IPlayer player : players) {
            ScoreCriteria = CriteriaFactory.createCriteria(gameState);
            player.setScore(ScoreCriteria.calculateScore(player, players, cardTypes, null));
        }
    }
}
