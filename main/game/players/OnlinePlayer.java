package main.game.players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.game.card.ICard;

public class OnlinePlayer implements IHumanPlayer {
    private int playerID;
    private int score;
    private ArrayList<ICard> hand;
    private String message;
    private Socket connection;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    
    public OnlinePlayer(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID;
        this.score = 0;
        this.hand = new ArrayList<ICard>();
        this.connection = connection; 
        this.inFromClient = inFromClient; 
        this.outToClient = outToClient; 
    }
    
    @Override
    public int getPlayerID() {
        return playerID;
    }
    @Override
    public int getScore() {
        return score;
    }
    @Override
    public void sendMessage(Object message) {
        try {
            outToClient.writeObject(message);
        } 
        catch (Exception e) {}
    }
    @Override
    public String readMessage() {
        try{
            message = (String) inFromClient.readObject();
        } 
        catch (Exception e){}
        return message;
    }
    @Override
    public ArrayList<ICard> getHand() {
        return hand;
    }
    public Socket getConnection() {
        return connection;
    }
    @Override
    public void setScore(int score){
        this.score = score;
    }
    
    
    // public void setScore(int score) {
    //     this.score = score;
    // }
    
    // public void addScore(int score) {
    //     this.score += score;
    // }
    
    // public void resetScore() {
    //     this.score = 0;
    // }
    
    // public String toString() {
    //     return name + " (" + score + ")";
    // }
    
}
