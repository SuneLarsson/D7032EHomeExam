package main.game.display;

import main.game.card.Card;
import main.game.score.PointSaladCriteria;


import java.util.ArrayList;

public class HandDisplay {

    public String displayHand(ArrayList<Card> hand, ArrayList<String> cardTypes) {
        String handString = "Criteria:\t";
        for (int i = 0; i < hand.size(); i++) {
            if(hand.get(i).isPointSideUp() && hand.get(i).getResourceSide() != null) {
                handString += "["+i+"] "+hand.get(i).getPointSide() + " ("+hand.get(i).getResourceSide().toString()+")"+"\t";
            }
        }
        handString += "\nVegetables:\t";
        // Sum up the number of each vegetable and show the total number of each vegetable
        
        for (String cardType : cardTypes) {
            PointSaladCriteria pointSaladCriteria = new PointSaladCriteria();
            int count = pointSaladCriteria.countVegetables(hand, cardType);
            if(count > 0) {
                handString += cardType + ": " + count + "\t";
            }
        }
        return handString;
    }
}