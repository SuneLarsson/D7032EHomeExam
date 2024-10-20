package main.game.score;

import java.util.ArrayList;

import main.game.card.Card;

/**
 * Class that counts the resources of the players based on the cards they have.
 */
public class CountResources {

    public CountResources() {
    }
    
    /**
     * Method that counts all the resources of the player.
     * @param hand The hand of the player.
     * @return int The number of resources the player has.
     */
    public int countAllResource(ArrayList<Card> hand){
        int count = 0;
        for (Card card : hand) {
            if (!card.isPointSideUp()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Method that counts the resources of the player based on the resource.
     * @param hand The hand of the player.
     * @param resource The resource to count.
     * @return int The number of resources the player has.
     */
    public int countResource(ArrayList<Card> hand, String resource){
        int count = 0;
        for (Card card : hand) {
            if(!card.isPointSideUp() && card.getResourceSide().equals(resource)){
                count++;
            }
        }
        return count;
    }
}
