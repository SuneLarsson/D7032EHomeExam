package main.game.piles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import main.game.card.Card;
import main.game.card.ICardFactory;
import main.game.card.SaladCardFactory;
import main.game.setupgame.GameState;

public class PopulateDecks {
    private GameState gameState;
    private JSONArray cardsArray;
    private Map<String, ArrayList<Card>> decks;
    private ICardFactory ICardFactory;

    public PopulateDecks(GameState gameState, JSONArray cardsArray) {
        this.gameState = gameState;
        this.cardsArray = cardsArray;
        this.decks = new HashMap<>();
        initializeDecks();
        populateDecks();
        shufflePiles();
        makeDeck();
        
    }

    private void initializeDecks() {
        for (String cardType: gameState.getCardTypes()) {
            decks.put(cardType, new ArrayList<Card>());
        }
    }

    public void populateDecks() {
        for (int i = 0; i < gameState.getCardTypes().size(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);
            JSONObject criteriaObj = cardJson.getJSONObject("criteria");

            gameState.getCardTypes().get(i);            

        }
       for (int i = 0; i < cardsArray.length(); i++) {
        JSONObject cardJson = cardsArray.getJSONObject(i);
        JSONObject criteriaObj = cardJson.getJSONObject("criteria");

        for (String cardType : gameState.getCardTypes()) {
            ArrayList<Card> deck = decks.get(cardType);
            // deck.add(new SaladCard(cardType, criteriaObj.getString(cardType), true));
            //TODO kanske flytta till annat ställe typ setupgame eller gamestae?
            if (gameState.getGameMode().equals("PointSalad")) {
                ICardFactory = new SaladCardFactory();
            } else {
                System.out.println("Error: Game mode not recognized");
            }
            // Card tempCard = ICardFactory.createCard(cardType, criteriaObj.getString(cardType));
            deck.add(ICardFactory.createCard(cardType, criteriaObj.getString(cardType)));
        
        }
    }

    }

    public Map<String, ArrayList<Card>> getDecks() {
        return decks;
    }

    private void shufflePiles() {
        for (ArrayList<Card> deck : decks.values()) {
            shuffleDeck(deck);
        }
    }

    private void shuffleDeck(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }

    public int getAmountOfCards() {
        
        return cardsArray.length();
    }

    public void makeDeck() {
        ArrayList<Card> combineDeck = new ArrayList<>();
        // todo fixa så att den faktist multiplicerar med antal spelare borde not inte vara här
        for (ArrayList<Card> deck : decks.values()) {
            int j = 0;
            for (int i = 0; i < gameState.getSettings().getAmountOfEachCardType().get(j); i++) {
                combineDeck.add(deck.remove(0));
            }
        }
        shuffleDeck(combineDeck);
        
        //divide the deck into 3 piles
        PileManager pileManager = gameState.getPileManager();
        Pile pile1 = new SaladPile(pileManager, 0);
        Pile pile2 = new SaladPile(pileManager, 1);
        Pile pile3 = new SaladPile(pileManager, 2);
        // ArrayList<Card> pile1 = new ArrayList<>();
        // ArrayList<Card> pile2 = new ArrayList<>();
        // ArrayList<Card> pile3 = new ArrayList<>();
        for (int i = 0; i < combineDeck.size(); i++) {
            if (i % 3 == 0) {
                pile1.addCard(combineDeck.get(i));
            } else if (i % 3 == 1) {
                pile2.addCard(combineDeck.get(i));
            } else {
                pile3.addCard(combineDeck.get(i));
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
