package main.game;

import java.util.ArrayList;

import main.game.card.PointSaladCard;
import main.game.players.IPlayer;

public class PointSaladCriteria {



    private int ID1_2(ArrayList<PointSaladCard> hand, String criteria, int totalScore, ArrayList<IPlayer> players, int playerID) {

        int vegIndex = criteria.indexOf("MOST")>=0 ? criteria.indexOf("MOST")+5 : criteria.indexOf("FEWEST")+7;
        String veg = criteria.substring(vegIndex, criteria.indexOf("=")).trim();
        int countVeg = countVegetables(hand, PointSaladCard.Vegetable.valueOf(veg));
        int nrVeg = countVeg;
        for(IPlayer p : players) {
            if(p.getPlayerID() != playerID) {
                int playerVeg = countVegetables(p.getHand(), PointSaladCard.Vegetable.valueOf(veg));
                if((criteria.indexOf("MOST")>=0) && (playerVeg > nrVeg)) {
                    nrVeg = countVegetables(p.getHand(), PointSaladCard.Vegetable.valueOf(veg));
                }
                if((criteria.indexOf("FEWEST")>=0) && (playerVeg < nrVeg)) {
                    nrVeg = countVegetables(p.getHand(), PointSaladCard.Vegetable.valueOf(veg));
                }
            }
        }
        if(nrVeg == countVeg) {
            //System.out.print("ID1/ID2: "+Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim()) + " ");
            totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
        }
    }

}
