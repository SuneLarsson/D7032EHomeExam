package main.game.setupgame;

import main.game.network.Server;
import main.game.players.BotPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;

import java.net.Socket;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CreatePlayers {
    private GameState gameState;
    private Server server;

    public CreatePlayers(GameState gameState, Server server) throws Exception {
        this.gameState = gameState;
        this.server = server;
        createLocalPlayer();
        createOnlinePlayers();
        createBotPlayers();

    }

    private void createLocalPlayer() {
        if (gameState.getNumPlayers() != 0) {
            gameState.addPlayer(new LocalPlayer(0)); 
        }
    }

    // todo error handling
    private void createOnlinePlayers() throws Exception {
        ArrayList<Socket> connectionSockets = server.getConnectionSockets();
        for (int i = 1; i < gameState.getNumPlayers(); i++) {
            Socket connectionSocket = connectionSockets.get(i - 1);
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            gameState.getPlayers().add(new OnlinePlayer(i, connectionSocket, inFromClient, outToClient));
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }
    }
    
    private void createBotPlayers() {
        for (int i = gameState.getNumPlayers(); i < gameState.getNumberOfBots()+gameState.getNumPlayers(); i++) {
            gameState.addPlayer(new BotPlayer(i));
        }
    }

}
