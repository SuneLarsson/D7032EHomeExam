package main.game.display;

import java.util.ArrayList;

import main.game.piles.Pile;
import main.game.piles.PileManager;

public class SaladMarket implements IMarket {
    
    @Override
    public String printMarket(PileManager pileManager) {
        ArrayList<Pile> piles = pileManager.getPiles();
        String pileString = "Point Cards:\t";
        for (int p=0; p<piles.size(); p++) {
            
            if(piles.get(p).getPileCard()==null) {
                pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
            }
            else
                pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPileCard()) + "\t";
            if(pileManager.getPile(p).getPileCard()==null) {
                pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
            }
            else
                pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPileCard()) + "\t";
        }
        pileString += "\nVeggie Cards:\t";
        char veggieCardIndex = 'A';
        for (Pile pile : piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getMarketCard(0)) + "\t";
            veggieCardIndex++;
        }
        pileString += "\n\t\t";
        for (Pile pile : piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getMarketCard(1)) + "\t";
            veggieCardIndex++;
        }
        return pileString;
    }

}
