package main.game.display;

import java.util.ArrayList;

import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;


public class SendMessage {
    public void sendToAllPlayers(String message, ArrayList<IPlayer> players) {

		for (IPlayer player : players) {
            if (player instanceof IHumanPlayer) {
                IHumanPlayer humanPlayer = (IHumanPlayer) player;
                humanPlayer.sendMessage(message);
            }
		}
	}
}
