package main.game.score;

import java.util.ArrayList;

import main.game.piles.SetPiles;
import main.game.players.BotPlayer;
import main.game.players.IPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;

public class ScorePointSalad {

    public void setupScore(ArrayList<IPlayer> players){
        ArrayList<String> cardTypes = SetPiles.getCardType();
        PointSaladCriteria pointSaladCriteria;
        for (IPlayer player : players) {
            if (player instanceof BotPlayer) {
                BotPlayer botPlayer = (BotPlayer) player;
                pointSaladCriteria = new PointSaladCriteria();
                botPlayer.setScore(pointSaladCriteria.calculateScore(botPlayer, players, cardTypes, null));
            }
            else if (player instanceof OnlinePlayer) {
                // Handle online player actions
                OnlinePlayer onlinePlayer = (OnlinePlayer) player;
                pointSaladCriteria = new PointSaladCriteria();
                onlinePlayer.setScore(pointSaladCriteria.calculateScore(onlinePlayer, players, cardTypes, null));
            } else   {
                // Handle local player actions
                LocalPlayer localPlayer = (LocalPlayer) player;
                pointSaladCriteria = new PointSaladCriteria();
                localPlayer.setScore(pointSaladCriteria.calculateScore(localPlayer, players, cardTypes, null));
            }
        }
    }
}
