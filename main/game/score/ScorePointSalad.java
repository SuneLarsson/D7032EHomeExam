package main.game.score;

import java.util.ArrayList;

import main.game.piles.Old.SetPiles;
import main.game.players.BotPlayer;
import main.game.players.IPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;

public class ScorePointSalad {

    public void setupScore(ArrayList<IPlayer> players){
        ArrayList<String> cardTypes = SetPiles.getCardType();
        for (IPlayer player : players) {
            if (player instanceof BotPlayer) {
                BotPlayer botPlayer = (BotPlayer) player;
                botPlayer.setScore(PointSaladCriteria.calculateScore(botPlayer, players, cardTypes));
            }
            else if (player instanceof OnlinePlayer) {
                // Handle online player actions
                OnlinePlayer onlinePlayer = (OnlinePlayer) player;
                onlinePlayer.setScore(PointSaladCriteria.calculateScore(onlinePlayer, players, cardTypes));
            } else   {
                // Handle local player actions
                LocalPlayer localPlayer = (LocalPlayer) player;
                localPlayer.setScore(PointSaladCriteria.calculateScore(localPlayer, players, cardTypes));
            }
        }
    }
}
