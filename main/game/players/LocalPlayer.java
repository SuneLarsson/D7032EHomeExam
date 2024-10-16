package main.game.players;

import java.util.ArrayList;
import java.util.Scanner;

import main.game.card.Card;
import main.game.players.actions.HumanAction;
import main.game.players.actions.IPlayerActions;
import main.game.setupgame.GameState;

public class LocalPlayer implements IHumanPlayer{
    private int playerID;
    private ArrayList<Card> hand;
    private int score;
    private Scanner scanner;
    private IPlayerActions playerActions;
    
    public LocalPlayer(int playerID) {
        this.playerID = playerID;
        this.score = 0;
        this.hand = new ArrayList<Card>();
        this.scanner = new Scanner(System.in);
        this.playerActions = new HumanAction();
    }
    @Override
    public void sendMessage(Object message) {
        System.out.println(message);
    }
    // @Override
    // public String readMessage() {
    //     String message = "";
    //     try (Scanner scanner = new Scanner(System.in)) {
    //         message =  scanner.nextLine();
    //     }catch(Exception e){}
    //     return message;
    // }

    @Override
    public String readMessage() {
        String message = "";
        try {
            message = scanner.nextLine();
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
        return message;
    }

    // @Override
    // public String readMessage() {
    //     return scanner.nextLine();
    // }
    
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
    
    @Override
    public void takeTurn(GameState gameState) {
        playerActions.turnAction(this, gameState);
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
