package main.game.players;
import main.game.card.Card;
import main.game.players.actions.BotAction;
import main.game.players.actions.IPlayerActions;
import main.game.setupgame.GameState;

import java.util.ArrayList;

public class BotPlayer implements IPlayer{
    private int playerID;
    private ArrayList<Card> hand;
    private int score;
    private IPlayerActions playerActions;

    public BotPlayer(int playerID){
        this.playerID = playerID;
        this.hand = new ArrayList<Card>();
        this.score = 0;
        this.playerActions = new BotAction();
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
        playerActions.turnAction(this, gameState);
    }

}

// public void performPlayerAction(IPlayer player) {
//     if (player instanceof OnlinePlayer) {
//         // Handle online player actions
//     } else if (player instanceof LocalPlayer) {
//         // Handle local player actions
//     }
// }