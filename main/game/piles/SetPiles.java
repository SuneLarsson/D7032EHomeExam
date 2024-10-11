package main.game.piles;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import main.game.card.PointSaladCard;
import main.game.json.ReadJson;





public class SetPiles implements ISetPiles {
    //FACTORY? 
    private PileManager pileManager;
    private ArrayList<PointSaladCard> deckPepper = new ArrayList<>();
    private ArrayList<PointSaladCard> deckLettuce = new ArrayList<>();
    private ArrayList<PointSaladCard> deckCarrot = new ArrayList<>();
    private ArrayList<PointSaladCard> deckCabbage = new ArrayList<>();
    private ArrayList<PointSaladCard> deckOnion = new ArrayList<>();
    private ArrayList<PointSaladCard> deckTomato = new ArrayList<>();
    private JSONArray cardsArray;

    public SetPiles(PileManager pileManager) {
        this.pileManager = pileManager;
        this.cardsArray = null;//loook here
    }

    @Override
    public void createPiles() {
        
        this.cardsArray = ReadJson.jsonData("./src/PointSaladManifest.json");
        if (cardsArray == null) {
            //FIXME: handle error
            System.out.println("Error reading JSON file");
            return;
        }
        populateDecks();
        shufflePiles();
        makeDeck();


    }
    
    

    private void populateDecks() {
        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);

            // Get the criteria object from the card JSON
            JSONObject criteriaObj = cardJson.getJSONObject("criteria");

            // Add each vegetable card to the deck with its corresponding criteria
            deckPepper.add(new PointSaladCard(PointSaladCard.Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
            deckLettuce.add(new PointSaladCard(PointSaladCard.Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
            deckCarrot.add(new PointSaladCard(PointSaladCard.Vegetable.CARROT, criteriaObj.getString("CARROT")));
            deckCabbage.add(new PointSaladCard(PointSaladCard.Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
            deckOnion.add(new PointSaladCard(PointSaladCard.Vegetable.ONION, criteriaObj.getString("ONION")));
            deckTomato.add(new PointSaladCard(PointSaladCard.Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
        }
    }

    
    private void shufflePiles() {
        shuffleDeck(deckPepper);
        shuffleDeck(deckLettuce);
        shuffleDeck(deckCarrot);
        shuffleDeck(deckCabbage);
        shuffleDeck(deckOnion);
        shuffleDeck(deckTomato);
    }

	private void shuffleDeck(ArrayList<PointSaladCard> deck) {
		Collections.shuffle(deck);
	}
    
    private void makeDeck() {
        //getNrPlayers
        int cardsPerVeggie = 4/2 * 6;

        ArrayList<PointSaladCard> deck = new ArrayList<>();
        for(int i = 0; i < cardsPerVeggie; i++) {
            deck.add(deckPepper.remove(0));
            deck.add(deckLettuce.remove(0));
            deck.add(deckCarrot.remove(0));
            deck.add(deckCabbage.remove(0));
            deck.add(deckOnion.remove(0));
            deck.add(deckTomato.remove(0));
        }
        shuffleDeck(deck);

        //divide the deck into 3 piles
        ArrayList<PointSaladCard> pile1 = new ArrayList<>();
        ArrayList<PointSaladCard> pile2 = new ArrayList<>();
        ArrayList<PointSaladCard> pile3 = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            if (i % 3 == 0) {
                pile1.add(deck.get(i));
            } else if (i % 3 == 1) {
                pile2.add(deck.get(i));
            } else {
                pile3.add(deck.get(i));
            }
        }
        pileManager.addPile(pile1);
        pileManager.addPile(pile2);
        pileManager.addPile(pile3);
    }

}

