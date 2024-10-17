package main.game.display;

import main.game.card.Card;

import main.game.score.CountResources;
import main.game.setupgame.GameState;

import java.util.ArrayList;

public class HandDisplay {

    public String displayHand(ArrayList<Card> hand, GameState gameState) {
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
    }
}