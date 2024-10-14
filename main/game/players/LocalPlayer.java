package main.game.players;

import java.util.ArrayList;
import java.util.Scanner;

import main.game.card.Card;

public class LocalPlayer implements IHumanPlayer{
    private int playerID;
    private ArrayList<Card> hand;
    private int score;
    
    public LocalPlayer(int playerID) {
        this.playerID = playerID;
        this.score = 0;
        this.hand = new ArrayList<Card>();
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
    public void addCard(Card card){
        hand.add(card);
    }
    @Override
    public ArrayList<Card> getHand() {
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
    
    // public void addCard(Card card) {
    //     hand.add(card);
    // }
    
    // public void removeCard(Card card) {
    //     hand.remove(card);
    // }
    
    // public void flipCard(Card card) {
    //     card.flip();
    // }
    
    // public void playCard(Card card) {
    
}
