package main.game.players;
import main.game.card.ICard;
import java.util.ArrayList;

public class BotPlayer implements IPlayer{
    private int playerID;
    private ArrayList<ICard> hand;
    private int score;

    public BotPlayer(int playerID){
        this.playerID = playerID;
        this.hand = new ArrayList<ICard>();
        this.score = 0;
    }
    @Override
    public int getPlayerID(){
        return playerID;
    }
    @Override
    public ArrayList<ICard> getHand(){
        return hand;
    }
    @Override
    public int getScore(){
        return score;
    }
    @Override
    public void setScore(int score){
        this.score = score;
    }


}

// public void performPlayerAction(IPlayer player) {
//     if (player instanceof OnlinePlayer) {
//         // Handle online player actions
//     } else if (player instanceof LocalPlayer) {
//         // Handle local player actions
//     }
// }