package main.game.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import main.game.players.BotPlayer;
import main.game.players.IPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;;

public class Server {
    private ServerSocket aSocket;
    private ArrayList<IPlayer> players;

    public Server(ArrayList<IPlayer> players) {
        this.players = players;
    }

    public void startServer(int numberPlayers, int numberOfBots) throws Exception {
        players.add(new LocalPlayer(0)); // add this instance as a player
        // Open for connections if there are online players
        for (int i = 0; i < numberOfBots; i++) {
            players.add(new BotPlayer(i + 1)); // add a bot
        }
        if (numberPlayers > 1)
            aSocket = new ServerSocket(2048);
        for (int i = numberOfBots + 1; i < numberPlayers + numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(new OnlinePlayer(i, connectionSocket, inFromClient, outToClient)); // add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }
    }
}
