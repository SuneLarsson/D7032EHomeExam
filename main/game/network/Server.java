package main.game.network;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import main.game.setupgame.GameState;
import java.util.ArrayList;

public class Server {
    private ServerSocket aSocket;
    private ArrayList<Socket> connectionSockets = new ArrayList<Socket>();


    public Server(GameState gamestate) throws Exception {
        // int numberPlayers = gamestate.getNumPlayers() - 1;
        // aSocket = new ServerSocket(2048);
        // // todo error handling
        // for (int i = 0; i < numberPlayers; i++) {
        //     Socket connectionSocket = aSocket.accept();
        //     connectionSockets.add(connectionSocket);
        // }
        try {
            int numberPlayers = gamestate.getNumPlayers() - 1;
            System.out.println("Server socket");
            aSocket = new ServerSocket(2048);
            // todo error handling
            for (int i = 0; i < numberPlayers; i++) {
                System.out.println("Accepting connections");
                Socket connectionSocket = aSocket.accept();
                System.out.println("Connection accepted");
                connectionSockets.add(connectionSocket);
            }
        } catch (IOException e) {
            System.err.println("Error while creating server socket or accepting connections: " + e.getMessage());
            throw e;
        }
    }


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

    public ArrayList<Socket> getConnectionSockets() {
        return connectionSockets;
    }


}
