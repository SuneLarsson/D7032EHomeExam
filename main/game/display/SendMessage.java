package main.game.display;

import java.util.ArrayList;

import main.game.players.BotPlayer;
import main.game.players.IPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;

public class SendMessage {
    public void sendToAllPlayers(String message, ArrayList<IPlayer> players) {

		for (IPlayer player : players) {
            if (player instanceof BotPlayer) {
            }
            else if (player instanceof OnlinePlayer) {
                // Handle online player actions
                OnlinePlayer onlinePlayer = (OnlinePlayer) player;
                onlinePlayer.sendMessage(message);
            } else   {
                // Handle local player actions
                LocalPlayer localPlayer = (LocalPlayer) player;
                localPlayer.sendMessage(message);
            }
		}
	}
}
