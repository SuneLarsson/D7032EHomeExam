package main.game.network;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import main.game.setupgame.GameState;
import java.util.ArrayList;


/**
 * Server class that creates a server socket and accepts connections from clients.
 */
public class Server {
    private ServerSocket aSocket;
    private ArrayList<Socket> connectionSockets = new ArrayList<Socket>();

    /**
     * Creates a new server that accepts connections from clients.
     * Creates connection sockets for each online player.
     * @param gamestate The game state.
     * @throws Exception If there is an error creating the server socket or accepting connections.
     */
    public Server(GameState gamestate) throws Exception {
        try {
            int numberPlayers = gamestate.getNumPlayers() - 1;
            System.out.println("Server socket");
            aSocket = new ServerSocket(2048);
            for (int i = 0; i < numberPlayers; i++) {
                Socket connectionSocket = aSocket.accept();
                connectionSockets.add(connectionSocket);
            }
        } catch (IOException e) {
            System.err.println("Error while creating server socket or accepting connections: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Closes the server socket and all connection sockets.
     */
    public void close() {
        try {
            aSocket.close();
            if (connectionSockets == null) {
                return;
            }
            for (Socket connectionSocket : connectionSockets) {
                connectionSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the connection sockets.
     * @return ArrayList<Socket> The connection sockets.
     */
    public ArrayList<Socket> getConnectionSockets() {
        return connectionSockets;
    }
}
