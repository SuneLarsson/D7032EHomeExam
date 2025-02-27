package main.players;

import java.util.ArrayList;

import main.card.Card;
import main.game.gameState.GameState;
import main.game.gamelogic.turnlogic.TurnFactory;

/**
 * Class for local players
 * Holds methods for local players
 */

public class LocalPlayer implements IHumanPlayer{
    private int playerID;
    private ArrayList<Card> hand;
    private int score;

    /**
     * Constructor for local player
     * @param playerID The player's ID
     */
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
    public String readMessage(GameState gameState) {
        String message = "";
        try {
            message = gameState.getScanner().nextLine();
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
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
    
    @Override
    public void takeTurn(GameState gameState) {
        TurnFactory.createTurnLogic(gameState).takeTurn(gameState, this);
    }

    
}
