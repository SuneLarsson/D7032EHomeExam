package main.game.network;


import java.net.ServerSocket;
import java.net.Socket;


import main.game.setupgame.GameState;
import java.util.ArrayList;

public class Server {
    private ServerSocket aSocket;
    private ArrayList<Socket> connectionSockets;


    public Server(GameState gamestate) throws Exception {
        int numberPlayers = gamestate.getNumPlayers() - 1;
        aSocket = new ServerSocket(2048);
        // todo error handling
        for (int i = 0; i < numberPlayers; i++) {
            Socket connectionSocket = aSocket.accept();
            connectionSockets.add(connectionSocket);
        }
    }

    // todo implement close
    // public void close() {
    //     try {
    //         aSocket.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();

    public ArrayList<Socket> getConnectionSockets() {
        return connectionSockets;
    }


}
