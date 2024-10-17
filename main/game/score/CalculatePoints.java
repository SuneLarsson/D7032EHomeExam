package main.game.score;

import java.util.ArrayList;

import main.game.players.IPlayer;
import main.game.setupgame.GameState;

public class CalculatePoints {

    public void scorePoints(GameState gameState) {
        ArrayList<String> cardTypes = gameState.getSettings().getCardTypes();
        ArrayList<IPlayer> players = gameState.getPlayers();
        ICriteria ScoreCriteria;
        for (IPlayer player : players) {
            if (gameState.getGameMode().equals("PointSalad")) {
                ScoreCriteria = new PointSaladCriteria();
                player.setScore(ScoreCriteria.calculateScore(player, players, cardTypes, null));
            }
        }
    }
}
