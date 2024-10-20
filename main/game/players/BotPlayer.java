package main.game.players;
import main.game.card.Card;
import main.game.game.gameState.GameState;

import java.util.ArrayList;

/**
 * Class for bot players
 * Holds methods for bot players
 */
public class BotPlayer implements IPlayer{
    private int playerID;
    private ArrayList<Card> hand;
    private int score;

    /**
     * Constructor for bot player
     * @param playerID The player's ID
     */
    public BotPlayer(int playerID){
        this.playerID = playerID;
        this.hand = new ArrayList<Card>();
        this.score = 0;
    }
    @Override
    public int getPlayerID(){
        return playerID;
    }
    @Override
    public ArrayList<Card> getHand(){
        return hand;
    }

    @Override
    public void addCard(Card card){
        hand.add(card);
    }
    @Override
    public int getScore(){
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

// public void performPlayerAction(IPlayer player) {
//     if (player instanceof OnlinePlayer) {
//         // Handle online player actions
//     } else if (player instanceof LocalPlayer) {
//         // Handle local player actions
//     }
// }