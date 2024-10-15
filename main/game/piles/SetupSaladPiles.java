package main.game.piles;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import main.game.card.Card;
import main.game.card.SaladCard;
import main.game.json.ReadJson;
import main.game.setupgame.GameState;





public class SetupSaladPiles implements ISetupPiles {
    //FACTORY? 
    private PileManager pileManager;
    private GameState gameState;
    private ArrayList<Card> deckPepper = new ArrayList<>();
    private ArrayList<Card> deckLettuce = new ArrayList<>();
    private ArrayList<Card> deckCarrot = new ArrayList<>();
    private ArrayList<Card> deckCabbage = new ArrayList<>();
    private ArrayList<Card> deckOnion = new ArrayList<>();
    private ArrayList<Card> deckTomato = new ArrayList<>();
    private JSONArray cardsArray;


    public SetupSaladPiles(GameState gameState) {
        this.gameState = gameState;
        this.pileManager = gameState.getPileManager();
        this.cardsArray = null;
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
            deckPepper.add(new SaladCard("PEPPER", criteriaObj.getString("PEPPER") , true));
            deckLettuce.add(new SaladCard("LETTUCE", criteriaObj.getString("LETTUCE") , true));
            deckCarrot.add(new SaladCard("CARROT", criteriaObj.getString("CARROT") , true));
            deckCabbage.add(new SaladCard("CABBAGE", criteriaObj.getString("CABBAGE") , true));
            deckOnion.add(new SaladCard("ONION", criteriaObj.getString("ONION") , true));
            deckTomato.add(new SaladCard("TOMATO", criteriaObj.getString("TOMATO") , true));
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
        int numberOfPlayers = gameState.getNumPlayers() + gameState.getNumberOfBots();
        int cardsPerVeggie = numberOfPlayers/2 * 6;
        // cardsPerVeggie = 3; //test value

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
        Pile pile1 = new SaladPile(pileManager, 0);
        Pile pile2 = new SaladPile(pileManager, 1);
        Pile pile3 = new SaladPile(pileManager, 2);
        // ArrayList<Card> pile1 = new ArrayList<>();
        // ArrayList<Card> pile2 = new ArrayList<>();
        // ArrayList<Card> pile3 = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            if (i % 3 == 0) {
                pile1.addCard(deck.get(i));
            } else if (i % 3 == 1) {
                pile2.addCard(deck.get(i));
            } else {
                pile3.addCard(deck.get(i));
            }
        }
        pileManager.addPile(pile1);
        pileManager.addPile(pile2);
        pileManager.addPile(pile3);
        for(Pile pile : pileManager.getPiles()) {
            pile.setupMarket();
        }
    }

}

