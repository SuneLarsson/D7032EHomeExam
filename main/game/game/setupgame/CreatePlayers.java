package main.game.game.setupgame;

import main.game.game.gameState.GameState;
import main.game.network.Server;
import main.game.players.BotPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;
import java.net.Socket;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class that creates the players of the game.
 */
public class CreatePlayers {
    private GameState gameState;
    private Server server;

    /**
     * Constructor that creates the players of the game.
     * @param gameState The current state of the game.
     * @param server The server that the game is connected to.
     * @throws Exception
     */	
    public CreatePlayers(GameState gameState, Server server) throws Exception {
        this.gameState = gameState;
        this.server = server;
        createLocalPlayer();
        createOnlinePlayers();
        createBotPlayers();

    }

    /**
     * Method that creates the local player.
     */
    private void createLocalPlayer() {
        if (gameState.getNumPlayers() != 0) {
            gameState.addPlayer(new LocalPlayer(0)); 
        }
    }

    /**
     * Method that creates the online players.
     * @throws Exception
     */
    private void createOnlinePlayers() throws Exception {
        ArrayList<Socket> connectionSockets = server.getConnectionSockets();
        for (int i = 1; i < gameState.getNumPlayers(); i++) {
            Socket connectionSocket = connectionSockets.get(i - 1);
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            gameState.getPlayers().add(new OnlinePlayer(i, connectionSocket, inFromClient, outToClient));
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }
    }

    /**
     * Method that creates the bot players.
     */
    private void createBotPlayers() {
        for (int i = gameState.getNumPlayers(); i < gameState.getNumberOfBots()+gameState.getNumPlayers(); i++) {
            gameState.addPlayer(new BotPlayer(i));
        }
    }

}
