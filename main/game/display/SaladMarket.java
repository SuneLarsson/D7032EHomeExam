package main.game.display;

import java.util.ArrayList;

import main.game.card.Card;
import main.game.piles.Old.PileManager;
import main.game.piles.Old.PilePointSalad;

public class SaladMarket implements IMarket {
    
    @Override
    public String printMarket(PileManager pileManager) {
        ArrayList<ArrayList<Card>> piles = pileManager.getPiles();
        String pileString = "Point Cards:\t";
        for (int p=0; p<piles.size(); p++) {
            
            if(PilePointSalad.getPileCard()==null) {
                pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
            }
            else
                pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPointCard()) + "\t";
            if(pileManager.getPile(p).getPileCard()==null) {
                pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
            }
            else
                pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPointCard()) + "\t";
        }
        pileString += "\nVeggie Cards:\t";
        char veggieCardIndex = 'A';
        for (ArrayList<Card> pile : piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(0)) + "\t";
            veggieCardIndex++;
        }
        pileString += "\n\t\t";
        for (ArrayList<Card> pile : piles) {
            pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(1)) + "\t";
            veggieCardIndex++;
        }
        return pileString;
    }
		String pileString = "Point Cards:\t";
		for (int p=0; p<piles.size(); p++) {
			if(piles.getPile(p).getPointCard()==null) {
				pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
			}
			else
				pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPointCard()) + "\t";
		}
		pileString += "\nVeggie Cards:\t";
		char veggieCardIndex = 'A';
		for (Pile pile : piles) {
			pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(0)) + "\t";
			veggieCardIndex++;
		}
		pileString += "\n\t\t";
		for (Pile pile : piles) {
			pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(1)) + "\t";
			veggieCardIndex++;
		}
		return pileString;
	}
}
