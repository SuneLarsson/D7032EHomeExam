package main.game;

import java.util.ArrayList;

import main.game.card.ICard;
import main.game.card.PointSaladCard;
import main.game.players.IPlayer;

public class PointSaladCriteria {
    private static int totalScore = 0;


    public static int calculateScore(IPlayer player, ArrayList<IPlayer> players) {
        int playerID = player.getPlayerID();
        ArrayList<ICard> hand = player.getHand();
        String criteria;
        String[] parts; 
        for (PointSaladCard criteriaCard : hand) {
            if (criteriaCard.isCriteriaSideUp()) {
                criteria = criteriaCard.getCritera();
                parts = criteria.split(",");
            }
        }
        for(int i = 0; i < parts.length; i++) {
            if((criteria.indexOf("MOST")>=0) || (criteria.indexOf("FEWEST")>=0)) {
                ID1_2(hand, criteria, players, playerID);
            } else if(parts.length > 1 || criteria.indexOf("+")>=0 || parts[0].indexOf("/")>=0) {
                if (criteria.indexOf("+")>=0) {
                    ID5(hand, criteria, players, playerID);
                }else if(parts[0].indexOf("/")>=0) {
                    ID3(hand, criteria, parts, players, playerID);
                } else {
                    ID4(hand, criteria, parts, players, playerID);
                }
                
            } else if (parts[i].indexOf("TOTAL")>=0 || parts[i].indexOf("TYPE")>=0 || parts[i].indexOf("SET")>=0) {
                ID18(hand, parts[i], players, playerID);
            } else {
               System.out.println("Invalid criteria: "+criteria);
            }
        }
        return totalScore;
    }

    private void ID1_2(ArrayList<PointSaladCard> hand, String criteria, ArrayList<IPlayer> players, int playerID) {

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

    // ID5, ID6, ID7, ID11, ID12, ID13
    private void ID5(ArrayList<PointSaladCard> hand, String criteria,  ArrayList<IPlayer> players, int playerID) {
        String expr = criteria.split("=")[0].trim();
        String[] vegs = expr.split("\\+");
        int[] nrVeg = new int[vegs.length];
        int countSameKind = 1;
        for(int j = 1; j < vegs.length; j++) {
            if(vegs[0].trim().equals(vegs[j].trim())) {
                countSameKind++;
            }
        }
        if(countSameKind > 1) {
            //System.out.print("ID5/ID11: "+ ((int)countVegetables(hand, Card.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
            totalScore +=  ((int)countVegetables(hand, PointSaladCard.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim());
        } else {
            for(int i = 0; i < vegs.length; i++) {
                nrVeg[i] = countVegetables(hand, PointSaladCard.Vegetable.valueOf(vegs[i].trim()));
            }
            //find the lowest number in the nrVeg array
            int min = nrVeg[0];
            for (int x = 1; x < nrVeg.length; x++) {
                if (nrVeg[x] < min) {
                    min = nrVeg[x];
                }
            }
            //System.out.print("ID6/ID7/ID12/ID13: "+min * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
            totalScore += min * Integer.parseInt(criteria.split("=")[1].trim());
        }
    }
    
    // ID3
    private void ID3(ArrayList<PointSaladCard> hand, String criteria, String[] parts, ArrayList<IPlayer> players, int playerID) {
        String veg = parts[0].substring(0, parts[0].indexOf(":"));
        int countVeg = countVegetables(hand, PointSaladCard.Vegetable.valueOf(veg));
        //System.out.print("ID3: "+((countVeg%2==0)?7:3) + " ");
        totalScore += (countVeg%2==0)?7:3;
    }

    // ID4, ID8, ID9, ID10, ID14, ID15, ID16, ID17
    private void ID4(ArrayList<PointSaladCard> hand, String criteria, String[] parts, ArrayList<IPlayer> players, int playerID) {
        for(int i = 0; i < parts.length; i++) {
            String[] veg = parts[i].split("/");
            //System.out.print("ID4/ID8/ID9/ID10/ID14/ID15/ID16/ID17: " + Integer.parseInt(veg[0].trim()) * countVegetables(hand, Card.Vegetable.valueOf(veg[1].trim())) + " ");
            totalScore += Integer.parseInt(veg[0].trim()) * countVegetables(hand, PointSaladCard.Vegetable.valueOf(veg[1].trim()));
        }
    }

    // ID18
    private void ID18(ArrayList<PointSaladCard> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
        if(criteria.indexOf("TOTAL")>=0) {
            int countVeg = countTotalVegetables(hand);
            int thisHandCount = countVeg;
            for(IPlayer p : players) {
                if(p.getPlayerID() != playerID) {
                    int playerVeg = countTotalVegetables(p.getHand());
                    if((criteria.indexOf("MOST")>=0) && (playerVeg > countVeg)) {
                        countVeg = countTotalVegetables(p.getHand());
                    }
                    if((criteria.indexOf("FEWEST")>=0) && (playerVeg < countVeg)) {
                        countVeg = countTotalVegetables(p.getHand());
                    }
                }
            }
            if(countVeg == thisHandCount) {
                //int aScore = Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
                //System.out.print("ID18 MOST/FEWEST: "+aScore + " " );
                totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
            }
        }
        if(criteria.indexOf("TYPE")>=0) {
            String[] expr = criteria.split("/");
            int addScore = Integer.parseInt(expr[0].trim());
            if(expr[1].indexOf("MISSING")>=0) {
                int missing = 0;
                for (PointSaladCard.Vegetable vegetable : PointSaladCard.Vegetable.values()) {
                    if(countVegetables(hand, vegetable) == 0) {
                        missing++;
                    }
                }
                //int aScore = missing * addScore;
                //System.out.print("ID18 TYPE MISSING: "+aScore + " ");
                totalScore += missing * addScore;
            }
            else {
                int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=")+2).trim());
                int totalType = 0;
                for(PointSaladCard.Vegetable vegetable : PointSaladCard.Vegetable.values()) {
                    int countVeg = countVegetables(hand, vegetable);
                    if(countVeg >= atLeastPerVegType) {
                        totalType++;
                    }
                }
                //int aScore = totalType * addScore;
                //System.out.print("ID18 TYPE >=: "+aScore + " ");
                totalScore += totalType * addScore;
            }
        }
        if(criteria.indexOf("SET")>=0) {
            int addScore = 12;
            for (PointSaladCard.Vegetable vegetable : PointSaladCard.Vegetable.values()) {
                int countVeg = countVegetables(hand, vegetable);
                if(countVeg == 0) {
                    addScore = 0;
                    break;
                }
            }
            //System.out.print("ID18 SET: "+addScore + " ");
            totalScore += addScore;
        }
    }

    private int countTotalVegetables(ArrayList<PointSaladCard> hand) {
        int count = 0;
        for (PointSaladCard card : hand) {
            if (card.isCriteriaSideUp()) {
                count++;
            }
        }
        return count;
    }

    private int countVegetables(ArrayList<PointSaladCard> hand, PointSaladCard.Vegetable vegetable) {
        int count = 0;
        for (PointSaladCard card : hand) {
            if (card.isCriteriaSideUp() && card.getVegetable() == vegetable) {
                count++;
            }
        }
        return count;
    }
    


}
