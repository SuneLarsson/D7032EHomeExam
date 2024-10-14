package main.game.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import main.game.players.BotPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;
import main.game.setupgame.GameState;;

public class Server {
    private static ServerSocket aSocket;
    // private ArrayList<IPlayer> players;

    // public Server(ArrayList<IPlayer> players) {
    //     this.players = players;
    // }

    public static void startServer(GameState gameState) throws Exception {
        int numberPlayers = gameState.getNumPlayers();
        int numberOfBots = gameState.getNumberOfBots();
        int playerID = 0;
        gameState.addPlayer(new LocalPlayer(playerID));
        playerID++;
        // players.add(new LocalPlayer(0)); // add this instance as a player
        // Open for connections if there are online players
        for (int i = 0; i < numberOfBots; i++) {
            gameState.addPlayer(new BotPlayer(playerID));
            playerID++;
        }
        if (numberPlayers > 1)
            aSocket = new ServerSocket(2048);
        for (int i = numberOfBots + 1; i < numberPlayers + numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            gameState.addPlayer(new OnlinePlayer(playerID, connectionSocket, inFromClient, outToClient)); // add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
            playerID++;
        }
    }
}
