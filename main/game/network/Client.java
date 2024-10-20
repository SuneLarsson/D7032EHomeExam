package main.game.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


/**
 * Calls to contorll the client that connects to a server and plays the game.
 */
public class Client {

    /**
     * Creates a new client that connects to a server.
     * @param ipAddress The IP address of the server.
     * @throws Exception If there is an error connecting to the server.
    */
    public Client(String ipAddress) throws Exception {
    try {
        Socket aSocket = new Socket(ipAddress, 2048);
        ObjectOutputStream outToServer = new ObjectOutputStream(aSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(aSocket.getInputStream());

        String nextMessage = "";
        Scanner in = new Scanner(System.in);
        while (!nextMessage.contains("winner")) {
            nextMessage = (String) inFromServer.readObject();
            System.out.println(nextMessage);
            // Todo change the contains an input from specific settings. 
            if (nextMessage.contains("Would you like to draw") || nextMessage.contains("into")) {
                outToServer.writeObject(in.nextLine());
            }
        }
        System.out.println("Closing");
        in.close();
        aSocket.close();
        outToServer.close();
        inFromServer.close();
    } catch (IOException e) {
        System.err.println("Connection error: " + e.getMessage());
        e.printStackTrace();
    }
}

}
