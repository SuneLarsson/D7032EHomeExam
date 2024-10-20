package main.game.players;

import java.util.ArrayList;

import main.game.card.Card;
import main.game.gamelogic.turnlogic.ITurnLogic;
import main.game.setupgame.GameState;

public class LocalPlayer implements IHumanPlayer{
    private int playerID;
    private ArrayList<Card> hand;
    private int score;
    private ITurnLogic turnLogic;
    // private Scanner scanner;
    // private IPlayerActions playerActions;
    
    public LocalPlayer(int playerID) {
        this.playerID = playerID;
        this.score = 0;
        this.hand = new ArrayList<Card>();
        // this.scanner = new Scanner(System.in);
        // this.playerActions = gameState.getSetup().getHumanActions();
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
        gameState.getSetup().getTurnFactory().takeTurn(gameState, this);
    }

    
}
