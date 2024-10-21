package main.game.display;

import main.game.card.Card;
import main.game.game.gameState.GameState;
import main.game.game.score.CountResources;

import java.util.ArrayList;

/**
 * HandDisplay class is used to display the hand of the player
 */
public class SaladHandDisplay implements IHandDisplay {

    /**
     * displayHand method is used to display the hand of the player
     * @param hand is the hand of the player
     * @param gameState is the current state of the game
     * @return the hand of the player as a string
     */
    @Override
    public String displayHand(ArrayList<Card> hand, GameState gameState) {
        try {
            String handString = gameState.getSettings().getPointName() + ":\t";
            for (int i = 0; i < hand.size(); i++) {
                if(hand.get(i).isPointSideUp() && hand.get(i).getResourceSide() != null) {
                    handString += "["+i+"] "+hand.get(i).getPointSide() + " ("+hand.get(i).getResourceSide().toString()+")"+"\t";
                }
            }
            handString += "\n" + gameState.getSettings().getResourceName() + "\t";
            // Sum up the number of each resourceCards and show the total number of each resource
            
            for (String cardType : gameState.getSettings().getCardTypes()) {
                CountResources countResources = new CountResources();;
                int count = countResources.countResource(hand, cardType);
                if(count > 0) {
                    handString += cardType + ": " + count + "\t";
                }
            }
            return handString;
        } catch (Exception e) {
            return "Error displaying hand";
        }
    }
}