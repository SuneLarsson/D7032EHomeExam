package main.game.score;

import java.util.ArrayList;

import main.game.card.Card;
import main.game.players.IPlayer;

public class PointSaladCriteria implements ICriteria {
    private int totalScore;
    private CountResources countResources;
    

    public PointSaladCriteria() {
        this.countResources = new CountResources();
    }


    @Override
    public int calculateScore(IPlayer player, ArrayList<IPlayer> players, ArrayList<String> vegetableTypes, ArrayList<Card> temphand) {
        this.totalScore = 0;
        int playerID = player.getPlayerID();
        ArrayList<Card> hand;
        // countResources.countAllResource(hand);
        if (temphand == null) {
            hand = player.getHand();
        } else {
            hand = temphand;
        }
        String criteria = "";
        String[] parts = new String[0]; 
        // ArrayList<String> vegetableTypes = vegetableTypes;
        for (Card criteriaCard : hand) {
            if (criteriaCard.isPointSideUp()) {
                criteria = criteriaCard.getPointSide();
                String[] newCriterias = criteria.split(",");
                String[] tempArray = new String[parts.length + newCriterias.length];
                System.arraycopy(parts, 0, tempArray, 0, parts.length);
                System.arraycopy(newCriterias, 0, tempArray, parts.length, newCriterias.length);

                parts = tempArray;
            }
        }
        for(int i = 0; i < parts.length; i++) {
            if (parts[i].indexOf("TOTAL")>=0 || parts[i].indexOf("TYPE")>=0 || parts[i].indexOf("SET")>=0) {
                ID18(hand, parts[i], players, playerID, vegetableTypes);
            // } else if(parts[i].contains("MOST") || parts[i].contains("FEWEST")) {
            //     // parts[i].indexOf("MOST")>=0 ||parts[i].indexOf("FEWEST")>=0) {
                // ID1_2(hand, parts[i], players, playerID);
            } else if(parts[i].indexOf("MOST")>=0) {
                ID1(hand, parts[i], players, playerID);
            } else if(parts[i].indexOf("FEWEST")>=0) {
                System.out.println("ID2: "+parts[i]);
                ID2(hand, parts[i], players, playerID);
            } else if(parts.length > 1 || parts[i].indexOf("+")>=0 || parts[i].indexOf("/")>=0) {
                if (parts[i].indexOf("+")>=0) {
                    ID5(hand, parts[i], players, playerID, vegetableTypes);
                }else if(parts[i].indexOf("=")>=0) {
                    ID3(hand, parts[i], players, playerID);
                    // Skip the next part of the criteria
                    i++;
                } else {
                    ID4(hand, parts[i], players, playerID);
                }
            } else {
               System.out.println("Invalid criteria: "+criteria);
            }
        }
        return totalScore;
    }

    private void ID1(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
        int vegIndex = criteria.indexOf("MOST") + 5;
        String veg = criteria.substring(vegIndex, criteria.indexOf("=")).trim();
        int countVeg = countResources.countResource(hand, veg);
        int nrVeg = countVeg;
    
        for (IPlayer p : players) {
            if (p.getPlayerID() != playerID) {
                int playerVeg = countResources.countResource(p.getHand(), veg);
                if (playerVeg > nrVeg) {
                    nrVeg = playerVeg;
                }
            }
        }
    
        // If the current player has the most vegetables, update the score
        if (nrVeg == countVeg) {
            this.totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=") + 1).trim());
        }
    }

    private void ID2(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
        int vegIndex = criteria.indexOf("FEWEST") + 7;
        System.out.println("ID2 vegIndex: "+vegIndex);
        String veg = criteria.substring(vegIndex, criteria.indexOf("=")).trim();
        int countVeg = countResources.countResource(hand, veg);
        System.out.println("ID2 countVeg: "+countVeg);
        int nrVeg = Integer.MAX_VALUE;

    
        for (IPlayer p : players) {
            int playerVeg = countResources.countResource(p.getHand(), veg);
            if (playerVeg < nrVeg) {
                nrVeg = playerVeg;
            }
        }
        System.out.println("ID2 nrVeg: "+nrVeg);
        System.out.println("ID2 poäng grejen: "+ Integer.parseInt(criteria.substring(criteria.indexOf("=") + 1).trim()));
        // If the current player has the fewest vegetables, update the score
        if (nrVeg == countVeg) {
            System.out.println("ID2 Inne i : ");
            this.totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=") + 1).trim());
        }
    }


    // ID5, ID6, ID7, ID11, ID12, ID13
    private void ID5(ArrayList<Card> hand, String criteria,  ArrayList<IPlayer> players, int playerID, ArrayList<String> vegetableTypes) {
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
            this.totalScore +=  ((int)countResources.countResource(hand, vegs[0].trim()) /countSameKind) * Integer.parseInt(criteria.split("=")[1].trim());
        } else {
            for(int i = 0; i < vegs.length; i++) {
                nrVeg[i] = countResources.countResource(hand, vegs[i].trim());
            }
            //find the lowest number in the nrVeg array
            int min = nrVeg[0];
            for (int x = 1; x < nrVeg.length; x++) {
                if (nrVeg[x] < min) {
                    min = nrVeg[x];
                }
            }
            this.totalScore += min * Integer.parseInt(criteria.split("=")[1].trim());
        }
    }
    
    // ID3
    private void ID3(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
        System.out.println("ID3: "+ criteria);
        String veg = criteria.substring(0, criteria.indexOf(":"));
        int countVeg = countResources.countResource(hand, veg);
        //todo lägg till if sats för att kolla om det är 0
        this.totalScore += (countVeg%2==0)?7:3;
    }

    // ID4, ID8, ID9, ID10, ID14, ID15, ID16, ID17
    private void ID4(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
        String[] veg = criteria.split("/");
        this.totalScore += Integer.parseInt(veg[0].trim()) * countResources.countResource(hand, veg[1].trim());
    }



    // ID18
    private void ID18(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID, ArrayList<String> vegetableTypes) {
        if(criteria.indexOf("TOTAL")>=0) {
            int countVeg = countResources.countAllResource(hand);
            int thisHandCount = countVeg;
            for(IPlayer p : players) {
                if(p.getPlayerID() != playerID) {
                    int playerVeg = countResources.countAllResource(p.getHand());
                    if((criteria.indexOf("MOST")>=0) && (playerVeg > countVeg)) {
                        countVeg = countResources.countAllResource(p.getHand());
                    }
                    if((criteria.indexOf("FEWEST")>=0) && (playerVeg < countVeg)) {
                        countVeg = countResources.countAllResource(p.getHand());
                    }
                }
            }
            if(countVeg == thisHandCount) {
                //int aScore = Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
                //System.out.print("ID18 MOST/FEWEST: "+aScore + " " );
                this.totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
            }
        }
        if(criteria.indexOf("TYPE")>=0) {
            String[] expr = criteria.split("/");
            int addScore = Integer.parseInt(expr[0].trim());
            if(expr[1].indexOf("MISSING")>=0) {
                int missing = 0;
                // ArrayList<String> cardtypes = SetPiles.getCardType();
                for (String vegetable : vegetableTypes) {
                    if(countResources.countResource(hand, vegetable) == 0) {
                        missing++;
                    }
                }
                //int aScore = missing * addScore;
                //System.out.print("ID18 TYPE MISSING: "+aScore + " ");
                this.totalScore += missing * addScore;
            }
            else {
                int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=")+2).trim());
                int totalType = 0;
                for(String vegetable : vegetableTypes) {
                    int countVeg = countResources.countResource(hand, vegetable);
                    if(countVeg >= atLeastPerVegType) {
                        totalType++;
                    }
                }
                //int aScore = totalType * addScore;
                //System.out.print("ID18 TYPE >=: "+aScore + " ");
                this.totalScore += totalType * addScore;
            }
        }
        if(criteria.indexOf("SET")>=0) {
            int addScore = 12;
            for (String vegetable : vegetableTypes) {
                int countVeg = countResources.countResource(hand, vegetable);
                if(countVeg == 0) {
                    addScore = 0;
                    break;
                }
            }
            //System.out.print("ID18 SET: "+addScore + " ");
            this.totalScore += addScore;
        }
    }


}
