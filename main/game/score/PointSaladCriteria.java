package main.game.score;

import java.util.ArrayList;

import main.game.card.Card;
import main.game.piles.SetPiles;
import main.game.players.IPlayer;

public class PointSaladCriteria {
    private int totalScore;

    public PointSaladCriteria() {
    }



    public int calculateScore(IPlayer player, ArrayList<IPlayer> players, ArrayList<String> vegetableTypes, ArrayList<Card> temphand) {
        this.totalScore = 0;
        int playerID = player.getPlayerID();
        ArrayList<Card> hand;
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
                ID18(hand, parts[i], players, playerID);
            } else if((parts[i].indexOf("MOST")>=0) || (parts[i].indexOf("FEWEST")>=0)) {
                ID1_2(hand, parts[i], players, playerID);
            } else if(parts.length > 1 || parts[i].indexOf("+")>=0 || parts[i].indexOf("/")>=0) {
                if (parts[i].indexOf("+")>=0) {
                    ID5(hand, parts[i], players, playerID, vegetableTypes);
                }else if(parts[i].indexOf("=")>=0) {
                    ID3(hand, parts[i], players, playerID);
                } else {
                    ID4(hand, parts[i], players, playerID);
                }
            // } else if (parts[i].indexOf("TOTAL")>=0 || parts[i].indexOf("TYPE")>=0 || parts[i].indexOf("SET")>=0) {
            //     ID18(hand, parts[i], players, playerID);
            } else {
               System.out.println("Invalid criteria: "+criteria);
            }
        }
        return totalScore;
    }

    private void ID1_2(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {

        int vegIndex = criteria.indexOf("MOST")>=0 ? criteria.indexOf("MOST")+5 : criteria.indexOf("FEWEST")+7;
        String veg = criteria.substring(vegIndex, criteria.indexOf("=")).trim();
        int countVeg = countVegetables(hand, veg);
        int nrVeg = countVeg;
        for(IPlayer p : players) {
            if(p.getPlayerID() != playerID) {
                int playerVeg = countVegetables(p.getHand(), veg);
                if((criteria.indexOf("MOST")>=0) && (playerVeg > nrVeg)) {
                    nrVeg = countVegetables(p.getHand(), veg);
                }
                if((criteria.indexOf("FEWEST")>=0) && (playerVeg < nrVeg)) {
                    nrVeg = countVegetables(p.getHand(), veg);
                }
            }
        }
        if(nrVeg == countVeg) {
            System.out.print("ID1/ID2: "+Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim()) + " ");
            this.totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
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
            System.out.print("ID5/ID11: "+ (countVegetables(hand, vegs[0].trim()) /countSameKind) * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
            this.totalScore +=  ((int)countVegetables(hand, vegs[0].trim()) /countSameKind) * Integer.parseInt(criteria.split("=")[1].trim());
            // totalScore +=  ((int)countVegetables(hand, SaladCard.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim());
        } else {
            for(int i = 0; i < vegs.length; i++) {
                nrVeg[i] = countVegetables(hand, vegs[i].trim());
            }
            //find the lowest number in the nrVeg array
            int min = nrVeg[0];
            for (int x = 1; x < nrVeg.length; x++) {
                if (nrVeg[x] < min) {
                    min = nrVeg[x];
                }
            }
            System.out.print("ID6/ID7/ID12/ID13: "+min * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
            this.totalScore += min * Integer.parseInt(criteria.split("=")[1].trim());
        }
    }
    
    // ID3
    private void ID3(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
        System.out.println("ID3: "+ criteria);
        String veg = criteria.substring(0, criteria.indexOf(":"));
        int countVeg = countVegetables(hand, veg);
        //System.out.print("ID3: "+((countVeg%2==0)?7:3) + " ");
        this.totalScore += (countVeg%2==0)?7:3;
    }

    // ID4, ID8, ID9, ID10, ID14, ID15, ID16, ID17
    private void ID4(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
        // for(int i = 0; i < parts.length; i++) {
            String[] veg = criteria.split("/");
            // System.out.println("ID4: VEG0 "+veg[0] + " VEG1 "+veg[1]);
            // System.out.println("ID4 count: " + countVegetables(hand, veg[1].trim()) + " ");
            // System.out.println("ID4 parseInt: " + Integer.parseInt(veg[0].trim()) + " ");
            System.out.println("ID4 criteria: " + criteria + " VEG0 "+veg[0] + " VEG1 "+veg[1]);

            System.out.print("ID4/ID8/ID9/ID10/ID14/ID15/ID16/ID17: " + Integer.parseInt(veg[0].trim()) * countVegetables(hand, veg[1].trim()) + " ");
            this.totalScore += Integer.parseInt(veg[0].trim()) * countVegetables(hand, veg[1].trim());
        // }
    }
    // private static void ID4(ArrayList<Card> hand, String criteria, String[] parts, ArrayList<IPlayer> players, int playerID) {
    //     for (int i = 0; i < parts.length; i++) {
    //         String[] veg = parts[i].split("/"); // Splits something like "7/TOMATO"
    //         if (veg.length == 2) {
    //             try {
    //                 int quantity = Integer.parseInt(veg[0].trim()); // Extract and parse the number
    //                 String vegetable = veg[1].trim(); // Extract the vegetable type
    //                 totalScore += quantity * countVegetables(hand, vegetable);
    //             } catch (NumberFormatException e) {
    //                 System.err.println("Invalid number format in criteria: " + veg[0]);
    //             }
    //         } else {
    //             System.err.println("Invalid format for part: " + parts[i]);
    //         }
    //     }
    // }
    




    // ID18
    private void ID18(ArrayList<Card> hand, String criteria, ArrayList<IPlayer> players, int playerID) {
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
                this.totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
            }
        }
        if(criteria.indexOf("TYPE")>=0) {
            String[] expr = criteria.split("/");
            int addScore = Integer.parseInt(expr[0].trim());
            if(expr[1].indexOf("MISSING")>=0) {
                int missing = 0;
                // ArrayList<String> cardtypes = SetPiles.getCardType();
                for (String vegetable : SetPiles.getCardType()) {
                    if(countVegetables(hand, vegetable) == 0) {
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
                for(String vegetable : SetPiles.getCardType()) {
                    int countVeg = countVegetables(hand, vegetable);
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
            for (String vegetable : SetPiles.getCardType()) {
                int countVeg = countVegetables(hand, vegetable);
                if(countVeg == 0) {
                    addScore = 0;
                    break;
                }
            }
            //System.out.print("ID18 SET: "+addScore + " ");
            this.totalScore += addScore;
        }
    }

    public int countTotalVegetables(ArrayList<Card> hand) {
        int count = 0;
        for (Card card : hand) {
            if (!card.isPointSideUp()) {
                count++;
            }
        }
        return count;
    }

    public int countVegetables(ArrayList<Card> hand, String vegetable) {
        int count = 0;
        for (Card card : hand) {
            // System.out.println("getreasourceSide: " + card.getResourceSide() + " vegetable: " + vegetable + " isPointSideUp: " + card.isPointSideUp()+ " vändlogik: "+ !card.isPointSideUp()+ " logic: " + ((!card.isPointSideUp()) && card.getResourceSide().equals(vegetable)));
            
            if (!card.isPointSideUp() && card.getResourceSide().equals(vegetable)) {
                count++;
            }
        }
        return count;
    }
    


}
