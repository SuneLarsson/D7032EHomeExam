package main.game.piles.piles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import main.game.card.Card;
import main.game.card.ICardFactory;
import main.game.setupgame.GameState;

public class BuildDecks {
    private GameState gameState;
    private JSONArray cardsArray;
    private Map<String, ArrayList<Card>> decks;
    private ICardFactory ICardFactory;
    private ArrayList<String> cardTypes;

    public BuildDecks(GameState gameState, JSONArray cardsArray) {
        this.gameState = gameState;
        this.cardsArray = cardsArray;
        this.decks = new HashMap<>();
        this.cardTypes = gameState.getSettings().getCardTypes();
        this.ICardFactory = gameState.getSetup().getCardFactory();
        initializeDecks();
        populateDecks();       
    }

    private void initializeDecks() {
        for (String cardType: cardTypes) {
            decks.put(cardType, new ArrayList<Card>());
        }
    }

    private void populateDecks() {
        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);
            JSONObject criteriaObj = cardJson.getJSONObject("criteria");

            for (String cardType : cardTypes) {
                ArrayList<Card> deck = decks.get(cardType);
                deck.add(ICardFactory.createCard(cardType, criteriaObj.getString(cardType)));
            }
        }

    }

    public Map<String, ArrayList<Card>> getDecks() {
        return decks;
    }



}
