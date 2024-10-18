package main.game.players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.game.card.Card;

import main.game.setupgame.GameState;

public class OnlinePlayer implements IHumanPlayer {
    private int playerID;
    private int score;
    private ArrayList<Card> hand;
    private String message;
    private Socket connection;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    
    public OnlinePlayer(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID;
        this.score = 0;
        this.hand = new ArrayList<Card>();
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
    public void addCard(Card card){
        hand.add(card);
    }
    @Override
    public void sendMessage(Object message) {
        try {
            outToClient.writeObject(message);
        } 
        catch (Exception e) {}
    }
    @Override
    public String readMessage(GameState gameState) {
        try{
            message = (String) inFromClient.readObject();
        } 
        catch (Exception e){}
        return message;
    }
    @Override
    public ArrayList<Card> getHand() {
        return hand;
    }
    public Socket getConnection() {
        return connection;
    }
    @Override
    public void setScore(int score){
        this.score = score;
    }

    @Override
    public void takeTurn(GameState gameState) {
        gameState.getSetup().getTurnLogic().takeTurn(gameState, this);
    }

    
}
