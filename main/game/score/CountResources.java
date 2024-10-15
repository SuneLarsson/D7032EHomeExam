package main.game.score;

import java.util.ArrayList;

import main.game.card.Card;

public class CountResources {

    public CountResources() {
    }
    
    public int countAllResource(ArrayList<Card> hand){
        int count = 0;
        for (Card card : hand) {
            if (!card.isPointSideUp()) {
                count++;
            }
        }
        return count;
    }

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
