package main.game.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {


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
