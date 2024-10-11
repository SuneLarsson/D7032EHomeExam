package main.game.players;

import java.util.ArrayList;
import java.util.Scanner;

import main.game.card.ICard;

public class LocalPlayer implements IHumanPlayer{
    private int playerID;
    private ArrayList<ICard> hand;
    private int score;
    
    public LocalPlayer(int playerID) {
        this.playerID = playerID;
        this.score = 0;
        this.hand = new ArrayList<ICard>();
    }
    @Override
    public void sendMessage(Object message) {
        System.out.println(message);
    }
    @Override
    public String readMessage() {
        String message = "";
        try (Scanner scanner = new Scanner(System.in)) {
            message =  scanner.nextLine();
        }catch(Exception e){}
        return message;
    }
    @Override
    public int getPlayerID() {
        return playerID;
    }
    @Override
    public ArrayList<ICard> getHand() {
        return hand;
    }
    @Override
    public int getScore() {
        return score;
    }
    @Override
    public void setScore(int score){
        this.score = score;
    }
    
    // public void addCard(ICard card) {
    //     hand.add(card);
    // }
    
    // public void removeCard(ICard card) {
    //     hand.remove(card);
    // }
    
    // public void flipCard(ICard card) {
    //     card.flip();
    // }
    
    // public void playCard(ICard card) {
    
}
