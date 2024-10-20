package main.game.display;

import java.util.ArrayList;

import main.game.piles.PileManager;
import main.game.piles.pile.Pile;


/**
 * SaladGameField class is used to display the game field for the PointSalad game
 * 3 piles of cards are displayed, each with 2 market cards
 */

public class SaladGameField implements IGameField {
    
    /**
     * printGameField method is used to display the game field
     * @param pileManager is the pile manager for the game
     * @return the game field as a string
     */
    @Override
    public String printGameField(PileManager pileManager) {
        try {
            ArrayList<Pile> piles = pileManager.getPiles();
            String pileString = "Point Cards:\t";
            for (int p = 0; p < piles.size(); p++) {
            if (piles.get(p).getPileCard() == null) {
                pileString += "[" + p + "]" + String.format("%-43s", "Empty") + "\t";
            } else {
                pileString += "[" + p + "]" + String.format("%-43s", piles.get(p).getPileCard()) + "\t";
            }
            }
            pileString += "\nVeggie Cards:\t";
            char veggieCardIndex = 'A';
            for (Pile pile : piles) {
            pileString += "[" + veggieCardIndex + "]" + String.format("%-43s", pile.getMarketCard(0)) + "\t";
            veggieCardIndex++;
            }
            pileString += "\n\t\t";
            for (Pile pile : piles) {
            pileString += "[" + veggieCardIndex + "]" + String.format("%-43s", pile.getMarketCard(1)) + "\t";
            veggieCardIndex++;
            }
            return pileString;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while printing the game field.";
        }
    }

}
