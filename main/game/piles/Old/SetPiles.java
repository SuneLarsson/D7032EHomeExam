package main.game.piles.Old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import main.game.card.Card;
import main.game.card.SaladCard;
import main.game.json.ReadJson;





public class SetPiles implements ISetPiles {
    //FACTORY? 
    private PileManager pileManager;
    private ArrayList<Card> deckPepper = new ArrayList<>();
    private ArrayList<Card> deckLettuce = new ArrayList<>();
    private ArrayList<Card> deckCarrot = new ArrayList<>();
    private ArrayList<Card> deckCabbage = new ArrayList<>();
    private ArrayList<Card> deckOnion = new ArrayList<>();
    private ArrayList<Card> deckTomato = new ArrayList<>();
    private JSONArray cardsArray;
    private static ArrayList<String> vegetableTypes = (ArrayList<String>) Arrays.asList("PEPPER", "LETTUCE", "CARROT", "CABBAGE", "ONION", "TOMATO");

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
    
    public static ArrayList<String> getCardType() {
        return vegetableTypes;
    }
    

    private void populateDecks() {
        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);

            // Get the criteria object from the card JSON
            JSONObject criteriaObj = cardJson.getJSONObject("criteria");

            // Add each vegetable card to the deck with its corresponding criteria
            deckPepper.add(new SaladCard("PEPPER", criteriaObj.getString("PEPPER") , false));
            deckLettuce.add(new SaladCard("LETTUCE", criteriaObj.getString("LETTUCE") , false));
            deckCarrot.add(new SaladCard("CARROT", criteriaObj.getString("CARROT") , false));
            deckCabbage.add(new SaladCard("CABBAGE", criteriaObj.getString("CABBAGE") , false));
            deckOnion.add(new SaladCard("ONION", criteriaObj.getString("ONION") , false));
            deckTomato.add(new SaladCard("TOMATO", criteriaObj.getString("TOMATO") , false));

            // deckLettuce.add(new PointSaladCard(PointSaladCard.Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
            // deckCarrot.add(new PointSaladCard(PointSaladCard.Vegetable.CARROT, criteriaObj.getString("CARROT")));
            // deckCabbage.add(new PointSaladCard(PointSaladCard.Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
            // deckOnion.add(new PointSaladCard(PointSaladCard.Vegetable.ONION, criteriaObj.getString("ONION")));
            // deckTomato.add(new PointSaladCard(PointSaladCard.Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
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

	private void shuffleDeck(ArrayList<Card> deck) {
		Collections.shuffle(deck);
	}
    
    private void makeDeck() {
        //getNrPlayers
        int cardsPerVeggie = 4/2 * 6;

        ArrayList<Card> deck = new ArrayList<>();
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
        ArrayList<Card> pile1 = new ArrayList<>();
        ArrayList<Card> pile2 = new ArrayList<>();
        ArrayList<Card> pile3 = new ArrayList<>();
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

