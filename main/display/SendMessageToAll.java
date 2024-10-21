package main.display;

import java.util.ArrayList;

import main.players.IHumanPlayer;
import main.players.IPlayer;

/**
 * Class that sends a message to all players.
 */

public class SendMessageToAll {

    /**
     * Sends a message to all players.
     * @param message The message to send.
     * @param players The players to send the message to.
     */
    public void sendToAllPlayers(String message, ArrayList<IPlayer> players) {
        try {
            for (IPlayer player : players) {
                if (player instanceof IHumanPlayer) {
                    IHumanPlayer humanPlayer = (IHumanPlayer) player;
                    humanPlayer.sendMessage(message);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while sending a message to all players." + e.getMessage());
        }
	}
}
